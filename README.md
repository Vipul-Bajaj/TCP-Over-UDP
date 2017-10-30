------------------------------------
Project Title - TCP over UDP
------------------------------------


------------------------------------
Group Details:
------------------------------------
 | Name                 | Roll No.  |  
 |----------------------|-----------|
 | Shubham Gupta        | 14115085  |
 | Aishwarya Srivastava | 14115901  | 
 | Ekta Agrawal         | 14115902  |
 | Vipul Bajaj          | 14115904  |


------------------------------------
Usage :
------------------------------------
There are 2 versions of the Sender and Receiver,each with increasing levels of data reliability and throughput.

*Sender and Receiver 1 uses Stop and Wait ARQ Protocol.*

*Sender and Receiver 2 uses Go-Back-N ARQ Protocol.*


## Run the receiver side:

```
$javac <Name of the Receiver File>.java

$java <Name of the Receiver File>
```
where 'Name of the Receiver File' is the name of the receiver file of the protocol being used.

When the program is ran it will ask the user to enter the port number which is to be used and also the filename for storing the file which the receiver will receive. 
 
## Run the sender side:

```
$javac <Name of the Sender File>.java

$java <Name of the Sender File>
```
where 'Name of the Sender File' is the name of the sender file of the protocol being used.

When the program is ran it will ask the user to enter the IpAddress  at which to send to , the port number which is to be used and also the filename of the file to send.
