package org.usfirst.frc.team2846.robot;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver2 implements Runnable {
	
	final SocketAddress address;
	float angle;
	float distance;
	
	public Receiver2(int port) {
		address = new InetSocketAddress(port);
	}

	public void run() {
		double msgDouble;
		long msgLong;
		int msgInt;
		float info1;
		float info2;
		String msg;
		try (DatagramChannel channel = DatagramChannel.open().bind(address)) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				buffer.clear();
				channel.receive(buffer);
				buffer.flip();
//				msgDouble = buffer.getDouble();
//				msgLong = buffer.getLong();
//				msgInt = buffer.getInt();
				info1 = buffer.getFloat();
				info2 = buffer.getFloat();
				angle = info1;
				distance = info2;
//				msg = new String(buffer.array(), buffer.position(), buffer.remaining());
				System.out.printf("Received a message: %10.6f %12.6f \n", info1, info2);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Receiver2 x = new Receiver2(5810);
		Thread t = new Thread(x);
		t.start();
		System.out.println("Receiving...");
		t.join();
	}

}
