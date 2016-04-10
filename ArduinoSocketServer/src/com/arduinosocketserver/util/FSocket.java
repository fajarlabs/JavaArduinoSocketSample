package com.arduinosocketserver.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FSocket {
	private String servername;
	private String port;
	private Socket socket;
	
	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public void sendRequest(String data) {
        OutputStream outToServer = null;
		try {
			outToServer = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException : Request OutputStream Gagal!");
			//e.printStackTrace();
		}
        DataOutputStream out = new DataOutputStream(outToServer);
        
        try {
			// System.out.println(">> socket : "+ socket.getLocalSocketAddress());
			out.writeUTF(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException : Request DataOutputStream Gagal!");
			//e.printStackTrace();
		}
	}
	
	public String getResponse() {
        InputStream inFromServer = null;
		try {
			inFromServer = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException : GetInputStream Gagal!");
		}
        DataInputStream in = new DataInputStream(inFromServer);
        
        String result = "";
        try {
			result = (String)in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException : DataInputStream Gagal!");
		}
        
		return result;
	}
	
	public Boolean connect() {
	      try
	      {
	          socket = new Socket(this.getServername(), Integer.parseInt(this.getPort()) );
	    	  return true;
	      } catch(IOException e) {
	    	  System.out.println("IO Exception : Tidak bisa terhubung!");
	         // e.printStackTrace();
	      }		
	      
	      return false;
	}
	
	public void disconnect() {
  		  boolean connected = socket.isConnected() && !socket.isClosed();
  		  if(connected) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("Sambungan socket sudah terputus!");
			}
  		  }
	}

	public FSocket(String servername, String port) {
		setServername(servername);
		setPort(port);
	}
	
	public FSocket() {
		
	}
	
	
}
