/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 *
 * @author Mahek Chheda
 */
public class IS_ngui  extends Thread{
   linkedlist head=null;
   rfc head1=null;
   public static void main(String args[])
   {
      Socket s=null;
    ServerSocket ss2=null;
    System.out.println("Server Listening......");
    try{
        ss2 = new ServerSocket(7734); // can also use static final PORT_NUM , when defined

    }
    catch(IOException e){
    e.printStackTrace();
    System.out.println("Server error");

    }

    while(true){
        try{
            s= ss2.accept();
            //System.out.println("connection Established");
            server1 st=new server1(s);
            st.start();
        }

    catch(Exception e){
        e.printStackTrace();
        //System.out.println("Connection Error");

    }
    }
}
   void linkedadd(linkedlist x)
   {
       if(head==null)
       {
           head=x;
       }
       else
       {
           linkedlist temp=head;
           while(temp.next!=null)
           {
               temp=temp.next;
           }
           temp.next=x;
       }
   }
  void linkeddisplay()
  {
      linkedlist temp=head;
      String s="Hostname\tUpload Port no\n";
      while(temp!=null)
      {
          s=temp.hostname+"\t"+temp.portno+"\n";
          temp=temp.next;
      }
     // System.out.println(s);
  }
 
  void rfcadd(rfc x)
  {
      if(head1==null)
      {
          head1=x;
      }
      else
      {
          rfc temp=head1;
          while(temp.next!=null)
          {
              temp=temp.next;
          }
          temp.next=x;
      }
  }
  void rfcdisplay()
  {
      rfc temp=head1;
      String s="RFC no. \ttitle \tHostname\n";
      while(temp!=null)
      {
          s=s+temp.num+"\t "+temp.title+"\t "+temp.hostname+"\n";
          temp=temp.next;
      }
      //System.out.println(s);
  }

  String searchrfc(String num)
  {
      String res="",b;
      rfc temp=head1;
      boolean flag=false;
      if(Integer.parseInt(num)==0)
          flag=true;
      while(temp!=null)
      {
          if(temp.num==Integer.parseInt(num)||flag)
          {
              b=getAddress(temp.hostname);
              res+=temp.num+"\t"+b+":"+portno(temp.hostname+"")+"\t\t"+temp.title+"\n";
          }
          temp=temp.next;
      }
      return res;
  }
  String getAddress(String a)
  {
      String res[];
      res=a.split(":");
      return res[0];
  }
  String portno(String hostname)
  {
      linkedlist temp=head;
      while(temp!=null)
      {
          if(temp.hostname.equals(hostname))
              return temp.portno+"";
          temp=temp.next;
      }
      return "";
  }
  void delete(String rs)
  {
      int i=0;
      if(head==null)
          return;
      if(head1==null)
          return;
      while(head!=null&&head.hostname.equals(rs))
      {
              head=head.next;
              if(head==null)
                    break;
               //System.out.println(rs+(i++));
              i++;
       }
        linkedlist temp=head,prev=head;
        while(temp!=null)
         {
                if(temp.hostname.equals(rs))
                {
                    prev.next=temp.next;
                    temp=temp.next;
                    i++;
                    //System.out.println(rs+(i++));
                }
                 else
                {
                    prev=temp;
                    temp=temp.next;
                }
            }
       while(head1!=null&&head1.hostname.equals(rs))
      {
          head1=head1.next;
         if(head1==null)
             return;
          //System.out.println(rs+(i++));
          i++;
        }
          rfc tempr=head1,prevr=head1;
          while(tempr!=null)
           {
              if(tempr.hostname.equals(rs))
               {
                        prevr.next=tempr.next;
                        tempr=tempr.next;
                        //System.out.println(rs+(i++));
                        i++;
                }
                    else
                    {
                        prevr=tempr;
                        tempr=tempr.next;
                    }
              }
        
      }
}
class server1 extends Thread {
   
    static File fi=new File("Server.log");
    static FileOutputStream stream;
   Socket server;
  static IS_ngui i=new IS_ngui(); 
   public server1(Socket s) throws IOException
   {
     server=s;
   }

   public void run()
   {
         try
         {
            //System.out.println(server.getRemoteSocketAddress());
            DataInputStream verify=new DataInputStream(server.getInputStream());
            if(userverify(verify.readUTF()))
            {
            DataOutputStream ack=new DataOutputStream(server.getOutputStream());
            ack.writeUTF("ok");
           String peer=verify.readUTF();
           String peer1[]=peer.split(":");
           linkedlist one=new linkedlist(server.getRemoteSocketAddress()+"",Integer.parseInt(peer1[1]));
           i.linkedadd(one);
           String client="New Client "+server.getRemoteSocketAddress()+" is now connected";
            server1.logfile(client);
           boolean f=true;
           while(true)
           {
             DataInputStream in =new DataInputStream(server.getInputStream());
              switch(in.readUTF())
            {
               case "1":
               {
                    String rfc[]=in.readUTF().split(",");
                    rfc r=new rfc(Integer.parseInt(rfc[0]),rfc[1],server.getRemoteSocketAddress()+"");
                   i. rfcadd(r);
                   i.rfcdisplay();
                   server1.logfile(in.readUTF());
                    break;
               }
               case "2":
               {
                    DataOutputStream out =new DataOutputStream(server.getOutputStream());
                    String search=in.readUTF();
                    server1.logfile(in.readUTF());
                    String reply=i.searchrfc(search);
                     out.writeUTF(reply);
                     break;
               }
               case "3":
               {
                   i.delete(server.getRemoteSocketAddress()+"");
                   server1.logfile(server.getRemoteSocketAddress()+"Disconncted or Logged out");
                   server.close();
                   i.linkeddisplay();
                   return;
               }
           }
           }
          }
            else{
                DataOutputStream ack=new DataOutputStream(server.getOutputStream());
            ack.writeUTF("Incorrect");
                server.close();
            }
         }
         catch(SocketException s)
         {
             try
             {
                 i.delete(server.getRemoteSocketAddress()+"");
                 System.out.println("Socket timed out!");
                server.close();
             }
             catch(IOException io)
             {
                 System.out.println(io+"");
             }
         }
         catch(Exception e)
         {
            e.printStackTrace();
         }
   }
   
   boolean userverify(String x)
   {
       String user[][]={{"mschheda","pass"},{"ashwin","pass"}};
       if(x.equals(","))
           return false;
       String temp[]=x.split(",");
       for(int i=0;i<user.length;i++)
       {
           if(user[i][0].equals(temp[0])&&user[i][1].equals(temp[1]))
               return true;
       }
       return false;
   }
  static void logfile(String x)
  {
      x="\n"+x;
       try
                    {
                        stream=new FileOutputStream(fi,true);
                        stream.write(getTime().getBytes("UTF-8"));
                        stream.write(x.getBytes("UTF-8"));
                        stream.write(System.getProperty("line separator").getBytes());
                    }
      
       catch(Exception fnfe)
       {
           System.out.println(fnfe);
           try
           { 
           stream.close();
           }
            catch(IOException e)
       {
           System.out.println(e);
       }
          
       }
                
  }
  static String getTime()
  {
      DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        Calendar cal = Calendar.getInstance();
        return "\n["+dateFormat.format(cal.getTime())+"]";
  }
  }

class linkedlist {
    String hostname;
    int portno;
    linkedlist next=null;
    linkedlist(String a, int b)
    {
        hostname=a;
        portno=b;
    }
}
class rfc{
    int num;
    String title;
    String hostname;
    rfc next=null;
    rfc(int a,String b,String c)
    {
        num=a;
        title=b;
        hostname=c;
    }
}