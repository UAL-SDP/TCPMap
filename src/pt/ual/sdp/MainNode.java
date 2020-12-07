package pt.ual.sdp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainNode {

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Wrong parameters.");
            System.exit(1);
        }
        int port = Integer.parseInt(args[1]);

        // Thread to deal with node registry.
        new MainNodeRegister(port).start();
    }
}
