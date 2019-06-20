------------------------------------
Project Title - TCP over UDP
------------------------------------
In this project, we have tried to implement the functionality of Transmission Control Protocol(TCP) using User Datagram Protocol(UDP).
We have simulated features of TCP like flow control, message reliability, congestion control etc. using UDP.

------------------------------------
Contributors:
------------------------------------
 * Shubham Gupta
 * Aishwarya Srivastava
 * Ekta Agrawal
 * Vipul Bajaj
 
------------------------------------
Usage :
------------------------------------
There are 2 versions of the Sender and Receiver,each with increasing levels of data reliability and throughput.

*Sender and Receiver 1 uses Stop and Wait ARQ Protocol.*

*Sender and Receiver 2 uses Go-Back-N ARQ Protocol.*


## Run the receiver side:

For running the receiver on Windows and Linux issue the following command on the command line or the linux terminal.

```
$javac <Name of the Receiver File>.java

$java <Name of the Receiver File>
```
where 'Name of the Receiver File' is the name of the receiver file of the protocol being used.

When the program is ran it will ask the user to enter the port number which is to be used and also the filename for storing the file which the receiver will receive. 
 
## Run the sender side:

For running the sender on Windows and Linux issue the following command on the command line or the linux terminal.

```
$javac <Name of the Sender File>.java

$java <Name of the Sender File>
```
where 'Name of the Sender File' is the name of the sender file of the protocol being used.

When the program is ran it will ask the user to enter the IpAddress  at which to send to , the port number which is to be used and also the filename of the file to send.
