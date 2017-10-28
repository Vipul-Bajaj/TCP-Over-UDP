import java.io.*;
import java.net.*;

public class Go_Back_N_Reciever {

	public static void main(String args[]) throws Exception {
		// Get the port number and name of file which is being received over UDP
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the port number : ");
		final int portNumber = Integer.parseInt(bufferedReader.readLine());
		System.out.print("Enter the name for recieving file : ");
		final String recievingFileName = bufferedReader.readLine();
		//System.out.println("Filename =" + recievingFileName);
		System.out.println("Ready to receive the file from the Sender.Here Go Back N ARQ Protocol is being used for error and flow control.");
		recieveFile(portNumber, recievingFileName);
	}

	public static void recieveFile(int portNumber, String fileName) throws IOException {
		// Create the socket, set the address and create the file to be sent
		DatagramSocket udpSocket = new DatagramSocket(portNumber);
		InetAddress sendersAddress;
		File file = new File(fileName);
		FileOutputStream outToFile = new FileOutputStream(file);

		// Create a flag to indicate the last message
		boolean isLastMessageFlag = false;

		// Store sequence number
		int sequenceNumber = 0;
		int lastSequenceNumber = 0;

		// For each message we will receive
		while (!isLastMessageFlag) {
			// Create byte array for full message and another for file data without header
			byte[] message = new byte[1024];
			byte[] fileByteArray = new byte[1021];

			// Receive packet and retreive message
			DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
			udpSocket.setSoTimeout(0);
			udpSocket.receive(receivedPacket);
			message = receivedPacket.getData();

			// Get port and address for sending ack
			sendersAddress = receivedPacket.getAddress();
			portNumber = receivedPacket.getPort();

			// Retrieve sequence number
			sequenceNumber = ((message[0] & 0xff) << 8) + (message[1] & 0xff);

			// Retrieve the last message flag
			if ((message[2] & 0xff) == 1) {
				isLastMessageFlag = true;
			} else {
				isLastMessageFlag = false;
			}

			if (sequenceNumber == (lastSequenceNumber + 1)) {

				// Update latest sequence number
				lastSequenceNumber = sequenceNumber;

				// Retrieve data from message
				for (int i=3; i < 1024 ; i++) {
					fileByteArray[i-3] = message[i];
				}

				// Write the message to the file
				outToFile.write(fileByteArray);
				System.out.println("Received Sequence number = " + sequenceNumber +", Flag = " + isLastMessageFlag);

				// Send acknowledgement
				sendAckToSender(lastSequenceNumber, udpSocket, sendersAddress, portNumber);

				// Check for last message
				if (isLastMessageFlag) {
					outToFile.close();
				} 
			} else {
				// If packet has been received, send ack for that packet again
				if (sequenceNumber < (lastSequenceNumber + 1)) {
					// Send acknowledgement for received packet
					sendAckToSender(sequenceNumber, udpSocket, sendersAddress, portNumber);
				} else {
					// Resend acknowledgement for last packet received
					sendAckToSender(lastSequenceNumber, udpSocket, sendersAddress, portNumber);
				}
			}
		}

		udpSocket.close();
		System.out.println("File " + fileName + " has been received and is stored at " + System.getProperty("user.dir") + ".");
	}

	public static void sendAckToSender(int lastSequenceNumber, DatagramSocket socket, InetAddress address, int port) throws IOException {
		// Resend acknowledgement
		byte[] ackPacket = new byte[2];
		ackPacket[0] = (byte)(lastSequenceNumber >> 8);
		ackPacket[1] = (byte)(lastSequenceNumber);
		DatagramPacket acknowledgement = new  DatagramPacket(ackPacket, ackPacket.length, address, port);
		socket.send(acknowledgement);
		System.out.println("Sent ack for Sequence Number = " + lastSequenceNumber);
	}
}