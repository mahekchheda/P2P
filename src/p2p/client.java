/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;
 import java.net.*;
import java.io.*;
/**
 *
 * @author Mahek Chheda
 */
public class client {
    
    public static void main(String args[])
    {
      String serverName = "192.168.1.117";
      int port = 7738;
      try
      {
         System.out.println("Connecting to " + serverName+ " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =new DataOutputStream(outToServer);
         out.writeUTF("123456"+" file.pdf "+client.getLocalAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =new DataInputStream(inFromServer);
         System.out.println("Server says " + in.readUTF());
         client.close();
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}    