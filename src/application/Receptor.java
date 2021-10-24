package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

/**
 * public class to receive sended data locally (for now) with 3 methods:
 * @author: Lea D  
 * - the constructor, to initialize variables 
 * @param: int taille to define the length of the data (and so the buffer need)
 * - the receivedDatas method to receive data in DatagramPacket form send on port on a port specified in the constructor
 * - the close method to close the socket
 */
public class Receptor extends Thread {

	// Initialization of our variables
	int taille = 32;
	byte[] bufferX;
	byte[] bufferY;
	DatagramSocket tunnel;
	DatagramPacket response;
	int port = 54734;

	// public void reception(DatagramSocket socket, int taille, Button stop) { // previous method
	
	/**
	 * Constructor of our receiver to initialize it (its port and the size of data received
	 * in particular)
	 * @param taille: size of the data we need to receive
	 * @throws IOException
	 */
	public Receptor(int taille) throws IOException {

		tunnel = new DatagramSocket(this.port); // We open our socket on the port chosed
		System.out.print("Server port: ");
		System.out.println(tunnel.getLocalPort()); // to print which port we use (easier to share ou port with other)
		this.taille = taille;
		bufferX = new byte[this.taille]; // Preparation of the buffers to receive data
		bufferY = new byte[this.taille];
		// byte[] bufferR = new byte[32];
		response = new DatagramPacket(bufferX, bufferX.length); // The data sended will be in the buffer bufferX
		// System.out.println("Datagram packet");

	}

	/**
	 * Method to open a socket and receive data as initialized in the constructor
	 * @throws IOException
	 */
	public void receivedDatas() throws IOException {
		// while(stop.getOnMouseClicked()==null) {
		while (true) {

			// System.out.println("Boucle while");
			tunnel.receive(response); // Reception of the data
			System.out.println("Reception d'un message"); // Message of reception of the data
			String stringifiedPacketContent = new String(bufferX, 0, response.getLength()); //Recuperation of the data
			InetAddress receivedAddress = response.getAddress(); // Recuperation of the address from which the data were send
			// System.out.print("Receive Message: ");
			System.out.println("Receive Message: " + stringifiedPacketContent); // We show in the terminal the data receive
			// character to separate the numbers (here space)
			/*
			 * String delims ="[ ]"; String[] splitStringifiedPacketContent =
			 * stringifiedPacketContent.split(delims); double receivedValueX =
			 * Double.valueOf(splitStringifiedPacketContent[0]);
			 * receivedValuesX.add(receivedValueX); double receivedValueY =
			 * Double.valueOf(splitStringifiedPacketContent[1]);
			 * receivedValuesY.add(receivedValueY);
			 */
			int receivedPort = response.getPort(); //Recuperation of the port from which the data received were sended

			//To send a message of good reception of the data to the sender
			/*String recepMsg = "Message received";
			bufferY = recepMsg.getBytes(); // Conversion of the message to binary digits
			DatagramPacket request = new DatagramPacket(bufferY, bufferY.length, receivedAddress, receivedPort);
			tunnel.send(request); // sending of the reception message*/

			// tunnel.close();

		}
		// String quote = new String(bufferY, 0, response.getLength());
		// System.out.println(quote);

	}
	
	/**
	 * Method to open a socket and receive data as initialized in the constructor
	 * and return the data received
	 * @return String stringifiedPacketContent which contains the data received
	 * @throws IOException
	 */
	public String receivedDatas2() throws IOException {
		// while(stop.getOnMouseClicked()==null) {
		//while (true) {

			// System.out.println("Boucle while");
			tunnel.receive(response); // Reception of the data
			System.out.println("Reception d'un message"); // Message of reception of the data
			String stringifiedPacketContent = new String(bufferX, 0, response.getLength()); //Recuperation of the data
			InetAddress receivedAddress = response.getAddress(); // Recuperation of the address from which the data were send
			// System.out.print("Receive Message: ");
			System.out.println("Receive Message: " + stringifiedPacketContent); // We show in the terminal the data receive

			int receivedPort = response.getPort(); //Retrieval of the port from which the data received were sended

			//To send a message of good reception of the data to the sender
			//Doesn't seem to work locally, not tested with other computer
			String recepMsg = "Message received";
			bufferY = recepMsg.getBytes(); // Conversion of the message to binary digits
			DatagramPacket request = new DatagramPacket(bufferY, bufferY.length, receivedAddress, receivedPort);
			tunnel.send(request); // sending of the reception message
			return stringifiedPacketContent;

		//}
	}

	/**
	 * Method to close the DatagramSocket
	 */
	public void close() {
		tunnel.close(); // To close the socket
	}
	/*
	 * private List<Double> receivedValuesX = new ArrayList<>(); public List<Double>
	 * getValuesX(){ return receivedValuesX; } private List<Double> receivedValuesY
	 * = new ArrayList<>();
	 * 
	 * public List<Double> getValuesY(){ return receivedValuesY; }
	 */

	/**
	 * Main to test only the Receptor which received data
	 * Only work locally (cause by Windows protection?)
	 * @param args
	 */
	public static void main(String[] args) {

		Receptor r;
		try {
			r = new Receptor(32); // We open our receiver
			r.receivedDatas(); // processing of the received date
			r.close(); // we close the socket
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
