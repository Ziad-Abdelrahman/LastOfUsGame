package engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.characters.Hero;
import model.world.Cell;

public class Client {
	    private DatagramSocket socket;
	    private String name, address;
	    private int port;
	    private InetAddress ip;
	    private Thread send;
	    private int in = 0;
	    public Client(String name, String address, int port) {
	        this.name = name;
	        this.address = address;
	        this.port = port;
	        boolean connect = openConnection(address);
	        if (!connect) {
	            System.err.println("Connection failed!");
	        }
	        send(name.getBytes());
	    }

	    public void send(final byte[] data) {
	        send = new Thread("SendString") {
	            public void run() {
	                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
	                try {
	                    socket.send(packet);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	        send.start();
	    }
	    public Object receive() {
			byte[] data = new byte[10240];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Object ret = process(packet.getData());
			return ret;
		}
	    public boolean openConnection(String address) {
	        try {
	            socket = new DatagramSocket();
	            ip = InetAddress.getByName(address);
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	            return false;
	        } catch (SocketException e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }

	    public void send(Cell[][] cells) {
	    	Serializer ser = new Serializer();
	        byte[] data = ser.serialize(cells);
	        send = new Thread("SendCell") {
	            public void run() {
	                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
	                try {
	                    socket.send(packet);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	        send.start();
	    }

	    public void send(ArrayList<Hero> heroes) {
	        byte[] data = heroesToBytes(heroes);
	        send = new Thread("SendHero") {
	            public void run() {
	                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
	                try {
	                    socket.send(packet);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	        send.start();
	    }

	    public static byte[] cellsToBytes(Object cells) {
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        try {
	            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
	            objectStream.writeObject(cells);
	            objectStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] byteArray = byteStream.toByteArray();
	        System.out.println("byte Array Size" + byteArray.length);
	        return byteArray;
	    }

	    public static Cell bytesToCells(byte[] bytes) {
	        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
	        ObjectInputStream objectStream;
	        Cell deserializedArray = null;
	        try {
	            objectStream = new ObjectInputStream(byteStream);
	            Object deserializedObject = objectStream.readObject();
	            objectStream.close();
	            deserializedArray = (Cell) deserializedObject;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return deserializedArray;
	    }
	    
	    public static byte[] heroesToBytes(ArrayList<Hero> hero) {
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        try {
	            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
	            objectStream.writeObject(hero);
	            objectStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] byteArray = byteStream.toByteArray();
	        return byteArray;
	    }
	    
	    public static byte[] heroToBytes(Hero hero) {
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        try {
	            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
	            objectStream.writeObject(hero);
	            objectStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] byteArray = byteStream.toByteArray();
	        return byteArray;
	    }
	    
	    
	    public static ArrayList<Hero> bytesToHeroes(byte[] bytes) {
	        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
	        ObjectInputStream objectStream;
	        ArrayList<Hero> deserializedArray = null;
	        try {
	            objectStream = new ObjectInputStream(byteStream);
	            Object deserializedObject = objectStream.readObject();
	            objectStream.close();
	            deserializedArray = (ArrayList<Hero>) deserializedObject;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return deserializedArray;
	    }
	    private Object bytesToObject(byte[] bytes) {
	        try {
	        	ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
	            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
	            System.out.println("object: "+objectStream);
	            return objectStream.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    public Object process(byte[] data) {
	    	if(in == 0) {
	    		in++;
	    		System.out.println("Client unprocessed: "+data);
	    		String s = new String(data).replaceAll(name, address).replaceAll("[^\\x20-\\x7E]", "");
	    		System.out.println("Client process: "+s);
	    		return s;	    	
	    	}
	    	else {
	    		Serializer ser = new Serializer();
	    	return  ser.deserialize(data);
	 		
	    	
	    }
	}
}
