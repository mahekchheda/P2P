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
public class server implements Runnable {
   

   private ServerSocket serverSocket;
   linkedlist1 head=null;
   rfc1 head1=null;
   public server(int port) throws IOException
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
            System.out.println("Waiting for client on port " +serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
           String peer="" +server.getRemoteSocketAddress();
           String peer1[]=peer.split(":");
           linkedlist1 one=new linkedlist1(peer1[0],Integer.parseInt(peer1[1]));
           linkedadd(one);
            DataInputStream in =new DataInputStream(server.getInputStream());
            String rf[]=in.readUTF().split(" ");
            rfc1 r=new rfc1(Integer.parseInt(rf[0]),rf[1],rf[2]);
            rfcadd(r);
            linkdisplay();
            rfcdisplay();
            DataOutputStream out =new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to "+ server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         }
         catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }
         catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
  void linkedadd(linkedlist1 x)
   {
       if(head==null)
       {
           head=x;
       }
       else
       {
           linkedlist1 temp=head;
           while(temp.next!=null)
           {
               temp=temp.next;
           }
           temp.next=x;
       }
   }
  void linkdisplay()
  {
      linkedlist1 temp=head;
      while(temp!=null)
      {
          System.out.println("Hostname"+temp.hostname+"\t Port No"+temp.portno);
          temp=temp.next;
      }
  }
  void rfcadd(rfc1 x)
  {
      if(head1==null)
      {
          head1=x;
      }
      else
      {
          rfc1 temp=head1;
          while(temp.next!=null)
          {
              temp=temp.next;
          }
          temp.next=x;
      }
  }
  void rfcdisplay()
  {
      rfc1 temp=head1;
      while(temp!=null)
      {
          System.out.println("RFC number"+temp.num+"\ttitle"+temp.title+"\tHostname"+temp.hostname);
          temp=temp.next;
      }
  }
public static void main(String [] args)
   {
      
       int port = 7738;
      try
      {
         Thread t = new Thread(new server(port),"server");
         t.start();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
class linkedlist1 {
    String hostname;
    int portno;
    linkedlist1 next=null;
    linkedlist1(String a, int b)
    {
        hostname=a;
        portno=b;
    }
}
class rfc1{
    int num;
    String title;
    String hostname;
    rfc1 next=null;
    rfc1(int a,String b,String c)
    {
        num=a;
        title=b;
        hostname=c;
    }
}
