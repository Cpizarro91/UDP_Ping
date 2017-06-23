import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;



class PingServer {
 public static void main(String args[]) throws Exception
 {
 DatagramSocket serverSocket = new DatagramSocket(2014); //server will run on port #2014
 byte[] receiveData = new byte[1024];
 //Processing loop
 while(true)
 {
 //create a random number generator for use in packet loss simulation
 Random random = new Random();
 //create a datagram packet to hold incoming UDP packet
 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
 // get the client message
 serverSocket.receive(receivePacket);
 //printData(request);
 String sentence = new String(receivePacket.getData(),0, receivePacket.getLength());
 InetAddress IPAddress = receivePacket.getAddress(); //get client's IP
 int port = receivePacket.getPort(); //get client's port #
 //print out the clients's IP address, port number and the message
 System.out.println("client's port # = " + port);
 System.out.println("client'sIP = " +IPAddress);
 System.out.println("client's message = " +sentence); 
 //capitalize the message from the client
 String capitalizedSentence = sentence.toUpperCase();
 //simulate the packet loss
 int rand =random.nextInt(10);//a random number in the range of 0 to10
 // if rand is less than 4 we consider the packet lost and do not reply
 if (rand < 4) {
 System.out.println("Reply not sent\n");
 continue;
 }
 //otherwise, the server responds
 byte[] sendData = capitalizedSentence.getBytes();
 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
port);
 serverSocket.send(sendPacket);
 System.out.println("Reply to the client sent\n");
 }//while
 }//main
} //doesn't close when pings are done