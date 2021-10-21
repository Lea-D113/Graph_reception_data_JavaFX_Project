package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ServerThreadDatagrams  extends Thread {
	public void run() {
		try {
			DatagramSocket ds = new DatagramSocket(65355);
			System.out.print("Server port: ");
			System.out.println(ds.getLocalPort());
			byte[] buffer = new byte[16];
			byte[] buffer2 = new byte[16];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			while (true) {
				try {
					ds.receive(packet);
					String stringifiedPacketContent = new String(buffer, 0, packet.getLength());
					InetAddress receivedAddress = packet.getAddress();
					if (currentThreadAddress == null || currentThreadAddress == receivedAddress) {
						currentThreadAddress = receivedAddress;
					} else {
						initializeNewThread(receivedAddress);
					}
					System.out.print("Received Message from "+receivedAddress.toString()+": ");
					System.out.println(stringifiedPacketContent);
					String delims = "[;]";
					String[] splitStringifiedPacketContent = stringifiedPacketContent.split(delims);
					double receivedValueX = Double.valueOf(splitStringifiedPacketContent[0]);
					receivedValuesX.add(receivedValueX);
					double receivedValueY = Double.valueOf(splitStringifiedPacketContent[1]);
					receivedValuesY.add(receivedValueY);
					int receivedPort = packet.getPort();
					
					String message = "Message received";
					buffer2 = message.getBytes();
					DatagramPacket request = new DatagramPacket(buffer2, buffer2.length, receivedAddress, receivedPort);
					ds.send(request);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ServerThreadDatagrams newServerThread = null;
	
	public void initializeNewThread(InetAddress address) {
		newServerThread = new ServerThreadDatagrams();
		newServerThread.currentThreadAddress = address;
	}
	
	public List<String> returnSuccessiveIPs() {
		List<String> newIPList = new ArrayList<String>();
		if (currentThreadAddress != null) {
			newIPList.add(currentThreadAddress.toString());
			if (newServerThread != null) {
				List<String> addressListIterator = newServerThread.returnSuccessiveIPs();
				for (String s : addressListIterator) {
					newIPList.add(s);
				}
			}
		} else {
			newIPList.add("No addresses found yet");
		}
		return newIPList;
	}
	
	private List<Double> receivedValuesX = new ArrayList<>();
	
	public InetAddress currentThreadAddress = null;
	
	public List<Double> getValuesX(String ipAddressToFetch) {
		if (currentThreadAddress == null) {
			return null;
		} else {
			if (currentThreadAddress.toString() == ipAddressToFetch) {
				return receivedValuesX;
			} else {
				if (newServerThread != null) {
					return newServerThread.getValuesX(ipAddressToFetch);
				} else {
					return null;
				}
			}
		}
	}

	private List<Double> receivedValuesY = new ArrayList<>();
	
	public List<Double> getValuesY(String ipAddressToFetch) {
		if (currentThreadAddress == null) {
			return null;
		} else {
			if (currentThreadAddress.toString() == ipAddressToFetch) {
				return receivedValuesX;
			} else {
				if (newServerThread != null) {
					return newServerThread.getValuesY(ipAddressToFetch);
				} else {
					return null;
				}
			}
		}
	}

}
