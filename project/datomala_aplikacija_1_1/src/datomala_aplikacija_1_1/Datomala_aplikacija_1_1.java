/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datomala_aplikacija_1_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 *
 * @author dex
 */
public class Datomala_aplikacija_1_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
            InputStream is = null;
            OutputStream os = null;
            Socket server = null;
            int character;
            
            String serverIP = ConfigurationAbstract.loadConfig("NWTiS.app.config.xml").getSettings("socketIpAddress");
            int port = Integer.parseInt(ConfigurationAbstract.loadConfig("NWTiS.app.config.xml").getSettings("socketPort"));
            while (true){
                try{
                    server = new Socket(serverIP, port);
                    os = server.getOutputStream();
                    is = server.getInputStream();
                    Scanner s = new Scanner(System.in);
                    String command = s.nextLine();
                    os.write(command.getBytes());
                    os.flush();
                    server.shutdownOutput();
                    
                    StringBuilder response = new StringBuilder();
                    while ((character = is.read()) != -1){
                        response.append((char) character);
                    }
                   
                    System.out.println("RESPONSE: " + response);
                    
                }
                catch (IOException ex) {
                    System.out.println("ERROR: server is not responding.");
                    ex.printStackTrace();
                }
                finally{
                    try{
                        if (os != null)
                            os.close();
                        if (is != null)
                            is.close();
                        if (server != null)
                            server.close();
                    }
                    catch (IOException ex){
                    }
                }
               
                
            }
        } catch (NoConfiguration ex) {
            Logger.getLogger(Datomala_aplikacija_1_1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
