/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(0);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            Socket server = serverSocket.accept();
            System.out.println("Just connected to "
                  + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            String login[]=in.readUTF().split(",");
            System.out.println(login[0]+login[1]);
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            out.writeUTF("o");
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      try
      {
         Thread t = new GreetingServer(7738);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
