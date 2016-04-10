package com.arduinosocketserver.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//File Name GreetingServer.java
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.arduinosocketserver.util.FArduino;


public class SocketService extends Thread {
	private ServerSocket serverSocket;
	private FArduino arduino;

	public SocketService(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
		this.arduino = new FArduino();
		if(!this.arduino.initialize())
			System.exit(0);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				// Accept Socket-nya
				Socket server = serverSocket.accept();

				// Status koneksi socket
				// System.out.println("Just connected to "+
				// server.getRemoteSocketAddress());

				// Input stream
				DataInputStream in = new DataInputStream(server.getInputStream());
				// Receive Data
				String readUTF = in.readUTF();
				// System.out.println(readUTF);
				JSONParser parser = new JSONParser();
				// Command
				String command = "";
				// Receive JSON Object
				JSONObject jsonObject = null;
				try {
					jsonObject = (JSONObject) parser.parse(readUTF);
					command = (String) jsonObject.get("command");
					// Mengirim data ke arduino
					arduino.sendData(command);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Berikan informasi keluar
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				// Send Data
				// out.writeUTF("FROM SERVER Thank you for connecting to " +
				// server.getLocalSocketAddress() + "\n");
				out.writeUTF(jsonObject.toString());
				// SocketEngine se = new SocketEngine(ip_tujuan,port_tujuan);
				// if(se.connect()) {
				// se.sendRequest("Data Dari Server : "+readUTF);
				// se.disconnect();
				// }

				// Tutup server
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				// break;
			} catch (IOException e) {
				e.printStackTrace();
				// break;
			}
		}
	}

	public static void main(String[] args) {
		// Jika menggunakan CMD
		// int port = Integer.parseInt(args[0]);
		
		// Tester
		// Hapus jika menggunakan CMD
		int port = 12000;
		try {
			Thread t = new SocketService(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}