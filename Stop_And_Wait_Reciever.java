import java.io.*;
import java.net.*;

public class Stop_And_Wait_Reciever {

	public static void main(String args[]) throws Exception {
		// Get the port number and name of file which is being received over UDP
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the port number : ");
		final int portNumber = Integer.parseInt(bufferedReader.readLine());
		System.out.print("Enter the name for recieving file : ");
		final String recievingFileName = bufferedReader.readLine();
		//System.out.println("Filename =" + recievingFileName);
		System.out.println("Ready to receive the file from the Sender");
		recieveFile(portNumber, recievingFileName);
	}

	public static void recieveFile(int portNumber, String fileName) throws IOException {

		// Create the socket and create the file being recieved.
		DatagramSocket udpSocket = new DatagramSocket(portNumber);
		InetAddress sendersAddress;
		File file = new File(fileName);
		FileOutputStream ouputFileStream = new FileOutputStream(file);

		// Create a flag to indicate the last message
		boolean isLastMessageFlag = false;
		boolean isLastMessage = false;

		// Store sequence number
		int sequenceNumber = 0;
		int lastSequenceNumber = 0;

		//Store sender's port number.
		int sendersPortNumber = 0;

		// For each message we will receive
		while (!isLastMessage) {

			// Create byte array for full message and another for file data without header
			byte[] message = new byte[1024];
			byte[] fileByteArray = new byte[1021];

			// Receive packet and retreive message
			DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
			udpSocket.setSoTimeout(0);
			udpSocket.receive(receivedPacket);
			message = receivedPacket.getData();

			// Get sender's port number and address for sending acknowledgement.
			sendersAddress = receivedPacket.getAddress();
			sendersPortNumber = receivedPacket.getPort();

			// Retrieve sequence number
			sequenceNumber = ((message[0] & 0xff) << 8) + (message[1] & 0xff);

			// Retrieve the last message flag
			if ((message[2] & 0xff) == 1) {
				isLastMessageFlag = true;
			} else {
				isLastMessageFlag = false;
			}

			if (sequenceNumber == (lastSequenceNumber + 1)) {
				lastSequenceNumber = sequenceNumber;

				// Retrieve data from message
				for (int i=3; i < 1024 ; i++) {
					fileByteArray[i-3] = message[i];
				}

				// Write the message to the file and print received message
				ouputFileStream.write(fileByteArray);
				System.out.println("Received Sequence number = " + lastSequenceNumber +", Flag = " + isLastMessageFlag);

				// Send acknowledgement
				sendAckToSender(lastSequenceNumber, udpSocket, sendersAddress, sendersPortNumber);

			} else {
				System.out.println("Expected sequence number: " + (lastSequenceNumber + 1) + " but received " + sequenceNumber + ". DISCARDING");

				//Resend the acknowledgement
				sendAckToSender(lastSequenceNumber, udpSocket, sendersAddress, sendersPortNumber);
			}

			// Check for last message
			if (isLastMessageFlag) {
				ouputFileStream.close();
				isLastMessage = false;
				break;
			}
		}

		udpSocket.close();
		System.out.println("File " + fileName + " has been received and is stored at " + System.getProperty("user.dir") + ".");
	}

	public static void sendAckToSender(int lastSequenceNumber, DatagramSocket socket, InetAddress sendersAddress, int sendersPortNumber) throws IOException {
		// Resend acknowledgement
		byte[] ackPacket = new byte[2];
		ackPacket[0] = (byte)(lastSequenceNumber >> 8);
		ackPacket[1] = (byte)(lastSequenceNumber);
		DatagramPacket acknowledgement = new  DatagramPacket(ackPacket, ackPacket.length, sendersAddress, sendersPortNumber);
		socket.send(acknowledgement);
		System.out.println("Acknowledgement Sent for Sequence Number = " + lastSequenceNumber);
	}
}