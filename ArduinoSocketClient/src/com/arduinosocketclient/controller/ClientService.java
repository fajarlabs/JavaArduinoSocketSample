package com.arduinosocketclient.controller;

import java.net.*;
import java.io.*;
/**
 * Ini hanya socket client saja
 * Prinsip kerja-nya sama seperti pada contoh ArduinoBoardDesktop
 * Ini hanya contoh apabila dibuat untuk versi CMD
 * 
 * @author masfajar
 *
 */
public class ClientService
{
   public static void main(String [] args)
   {
      //String serverName = args[0];
      //int port = Integer.parseInt(args[1]);
      
      // Test connection
      String serverName = "localhost";
	  int port = 12000;
      
      try {
         System.out.println("Connecting to " + serverName +" on port " + port);
         
         // Init
         Socket client = new Socket(serverName, port);
         // Info
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         // Send data to server
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         out.writeUTF("Hello from "+ client.getLocalSocketAddress());
         
         // Get data from server
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println("Server says " + in.readUTF());
         // Close client
         client.close();
         
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
}
