package transfer.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project: genomizer-Server
 * Package: transfer.Test
 * User: c08esn
 * Date: 4/28/14
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */

public class Receive implements Runnable {


    private InputStream is;
    private ServerSocket welcome;
    private Socket listen;
    public Receive() {

        try {
            welcome = new ServerSocket(8091);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        Receive r = new Receive();
        r.run();
    }

    @Override
    public void run() {
        while(true) {
            try {
                byte[] info = new byte[1000];
                listen = welcome.accept();
                DataInputStream stream = new DataInputStream(listen.getInputStream());
                stream.read(info);
                System.out.println(new String(info));
                String answer = "<html><body>Hej anrop!</body></html>";
                DataOutputStream out = new DataOutputStream(listen.getOutputStream());
                out.write(answer.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

}
