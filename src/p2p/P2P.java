/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;
import java.net.*;

/**
 *
 * @author Mahek Chheda
 */
public class P2P {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Socket MyClient;
    try {
           MyClient = new Socket("Machine name", 7738);
    }
    catch (Exception e) {
        System.out.println(e);
    }  
    }
}
