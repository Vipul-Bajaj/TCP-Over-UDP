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
There are 3 versions of the Sender and Receiver,each with increasing levels of data reliability and throughput.

*Sender and Receiver 1 has completely unreliable data transfer.
Sender and Receiver 2 uses Stop and Wait.
Sender and Receiver 3 uses Go-Back-N.*

##Run the receiver side:

```
$java Receiver3 <Port> <Filename>
```
where <port>is the port number used for receiving from the sender and <Filename>is the name or filepath used to store the file on local disk.
 
##Run the sender side:

```
$ java Sender3 <IPaddress> <Port> <Filename>
```
where <IPaddress> is the address at which to send to, <Port> is the port number used by the corresponding receiver and <Filename> is the file or filepath of the file to transfer from local disk.
