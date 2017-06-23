import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//Cassandra Pizarro
//UDP PingClient
public class PingClient
{

public static void main(String[] args) throws Exception
{
// Get command line argument.
if (args.length != 2) {
System.out.println("Missing arguments: localhost port(2014)");
return;
}

String ServerName = args[0];	
int port = Integer.parseInt(args[1]);

// Create a datagram socket for receiving and sending UDP packets
DatagramSocket socket = new DatagramSocket();
InetAddress IPAddress =InetAddress.getByName(ServerName);

long rtt2 = 0;
float counter = 0;
float rttAvg = 0;
//Send out 10 ping requests to server, start at 1
for(int i=1;i<11;i++){
long startTime = System.currentTimeMillis()*1000; //this is current time
String pingAttemptnumber = "Ping Attempt Number: " + i + ", Current Time: " + startTime + " microseconds";
System.out.println(pingAttemptnumber);// send ping number and current time

String Message = "\nPing: "+ i + " \nstarting time:" + startTime + " microseconds"; //ping number
DatagramPacket request =
new DatagramPacket(Message.getBytes(), Message.length(),IPAddress,port);
socket.send(request); //sending message as a packet to server
DatagramPacket reply =
new DatagramPacket(new byte[1024], 1024); //create new packet for reply

socket.setSoTimeout(1000); //this will give a timeout exception reply

try
{
long elapsedStart = System.currentTimeMillis()*1000;
socket.receive(reply); //server only sends a reply when it sends a message to client
byte[] buf = reply.getData();
System.out.println("Received from server:" + new String(buf,0,buf.length)); //print out message from server
long elapsedEnd = System.currentTimeMillis()*1000;
long elapsedTime = elapsedEnd - elapsedStart;
long endTime = System.currentTimeMillis()*1000;
long rtt = endTime - startTime; 
System.out.println("Elapsed Time: " + elapsedTime + " microseconds"); //elapsed time for server to get reply
//and print out
System.out.println("RTT of Packet: " + rtt + " microseconds\n"); //difference between times
//rtt, server back to client time
counter +=1;
rtt2 = (rtt2) + (rtt);
}catch(IOException E)
{
	System.out.println("Request Timed Out\n"); //we don't get a reply
}

Thread.sleep(1000);//slow down thread to see it easier in terminal

}

rttAvg = (float) ((rtt2)/counter);
System.out.println("RTT Avg: " + rttAvg + " microseconds");
socket.close();
}

}

