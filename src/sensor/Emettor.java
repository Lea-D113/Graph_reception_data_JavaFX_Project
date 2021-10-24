package sensor;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * public class to send data with 3 methods:
 * @author: Lea D 
 * - the constructor Emettor, to initialize variables and informations to send the datas (adress, port)
 * @param:
 * 		String hostname: adresse or website to which we send the data
 * 		int port: the UDP port we send our data (make sure this port matches that of the receiver)
 * - the send method to send data (the message) to an adress and port specified in the constructor
 * @param:
 * 		String message: message that will be send
 * - the close method to close the socket
 */
public class Emettor {
	
	// Declaration of our variables
	InetAddress adress;
	DatagramSocket socket;
	int port;
	//String hostname = "djxmmx.net";
	//int port = 17;

	/**
	 * Constructor of the transmitter: initialization of the address and port to 
	 * which we will send the data
	 * @param hostname: address of the receiver
	 * @param port: UDP port of the receiver to which we will send the data
	 * @throws IOException
	 */
	public Emettor(String hostname, int port) throws IOException{
		
		// Initialization of our variables
		adress = InetAddress.getByName(hostname);
		socket = new DatagramSocket();
		this.port = port;
		
	}
	
	/**
	 * Method to send the data in our parameter to to computer with the address
	 * and to the port initialized in the constructor
	 * @param message: type: String, data send to our receiver (figures like float stringified for example)
	 * @throws IOException
	 */
	public void send(String message) throws IOException{
		
		//byte[] buffer = new byte[32]; //we fill it with our data
		byte[] buffer = new byte[message.length()];
		//To prepare the message to send it
		buffer = message.getBytes();
		//To prepare the request with the informations we need (to send it)		
		DatagramPacket request = new DatagramPacket(buffer, buffer.length, adress, port);
		socket.send(request);

	}
	
	/**
	 * Method to close the DatagramSocket used to send our data
	 */
	public void close() {
		socket.close(); // We close the socket
	}
	
	/**
	 * Main to send data locally or to another computer (use to test the class Emettor)
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		Emettor e = new Emettor("127.0.0.1", 54734); // initialization of the "transmitter"
		int i=20;
		while ((i--)>0) {
			//System.out.println(i);
			//e.envoi("hello");
			// This way, we're sending a random number between 0 and 20
			float random = ThreadLocalRandom.current().nextFloat()*20;
			System.out.println(i+": "+random); // We show it in our terminal
			e.send(String.valueOf(random)); // We send the number
			Thread.sleep(1000);// We wait a bit (one second) before sending the next number
		}
		e.close();
	}
		
}
