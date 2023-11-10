package engine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.Hero;
import model.world.Cell;

public class Server implements Runnable{
	public static ServerClient[] players = new ServerClient[2];
	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run, send, receive;
	private int index = 0;
	private int ready = 0;
	private int c = 0;
	public ArrayList<Hero> list = new ArrayList<Hero>();
	public Server(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}

	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		receive();
	}

	private void sendToAll(byte[] data) {
		for (int i = 0; i < players.length; i++) {
			ServerClient client = players[i];
			send(data, client.address, client.port);
		}
	}
	private void sendToAll(byte[] data,InetAddress address, int port) {
		for (int i = 0; i < 2; i++) {
			ServerClient client = players[i];
			if(client.address.equals(address) && client.port==port) { 
				System.out.println("address sent: "+ address);
				continue;
			
			}
			else
			send(data, client.address, client.port);
		}
	}
	private void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while (running) {
					
					byte[] data = new byte[10240];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					processData(packet.getData(), packet.getAddress(), packet.getPort());
				}
			}
		};
		receive.start();
	}
	 private void processData(byte[] data, InetAddress address, int port) {
		 if(index == 4) {
			 Serializer ser = new Serializer();
			 Object obj = ser.deserialize(data);
			 if(obj instanceof Cell[][]) {
				 Game.map=(Cell[][]) obj;
				 sendToAll(data);
			 }
			 else {
				 ready++;
				 System.out.println("Ending Turn");
				 if(ready>=2) { 
					 try {
							Game.endTurnOnline();
						} catch (NotEnoughActionsException | InvalidTargetException e) {
							e.printStackTrace();
						}
					 ArrayList<Cell[][]> arr = new ArrayList<>();
					 arr.add(Game.map);
					 sendToAll(ser.serialize(arr));
					 ready=0;
				}
			 }
		 }
		 if(index ==3) {
			Object obj = bytesToObject(data);
			if(obj instanceof ArrayList) {
				System.out.println(c++);
				list.add((Hero) ( ((ArrayList) obj).get(0)));
				sendToAll(data,address,port);
			}
			else {	
				ready++;
			}
			if(ready==2) {
				Game.startGameMP(list.get(0),list.get(1));
				sendToAll(Client.cellsToBytes(Game.map));
				index++;
				ready=0;
			}

		 	}
		 //player names receiving
		 if(index<2) {
		 		String name = new String(data);
				ServerClient player = new ServerClient(name, address, port);
				players[index++]=player;
				String printableString = name.replaceAll("[^\\x20-\\x7E]", "");
				System.out.println("Name: "+printableString+" "+player.address.toString() + ":" + player.port);
		 	}
		 if(index == 2) {
			 System.out.println("Server: "+Integer.toString(index));
			 sendToAll(Integer.toString(index).getBytes());
			 index++;
		 }
		 	
	    }
	 
	 
	 private void send(final byte[] data, final InetAddress address, final int port) {
			send = new Thread("Send") {
				public void run() {
					DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
					try {
						socket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			send.start();
		}

	 
	 private Object bytesToObject(byte[] bytes) {
	        try{ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
	             ObjectInputStream objectStream = new ObjectInputStream(byteStream);
	             System.out.println("object: "+objectStream);
	            return objectStream.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
