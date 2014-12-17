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
public class Server1 {
     public static void main (String [] args ) throws Exception {
          while(true)
          {
            ServerSocket serverSocket = new ServerSocket(15123);
             Socket socket = serverSocket.accept();
              System.out.println("Accepted connection : " + socket);
              File transferFile = new File ("F:\\notes\\Algos\\greedy-practice-problems.pdf");
              byte [] bytearray  = new byte [(int)transferFile.length()];
              FileInputStream fin = new FileInputStream(transferFile);
              BufferedInputStream bin = new BufferedInputStream(fin);
              bin.read(bytearray,0,bytearray.length);
              OutputStream os = socket.getOutputStream();
              System.out.println("Sending Files...");
              os.write(bytearray,0,bytearray.length);
              os.flush();
              socket.close();
              System.out.println("File transfer complete");
     }
     }
}
