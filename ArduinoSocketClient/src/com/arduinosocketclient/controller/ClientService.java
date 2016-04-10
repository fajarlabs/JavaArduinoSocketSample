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
      String serverName = args[0];
//      System.out.println(serverName);
      int port = Integer.parseInt(args[1]);
      try
      {
         System.out.println("Connecting to " + serverName +" on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from "+ client.getLocalSocketAddress());
         
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
}
