package pt.ual.sdp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketUtil {
    public static Scanner getScanner(Socket socket) {
        Scanner mainNodeScanner = null;
        try {
            mainNodeScanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainNodeScanner;
    }

    public static PrintWriter getPrintWritter(Socket socket) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    public static Socket getSocket(String address, int port) {
        Socket socket = null;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void closeSocket(Socket socket) {
        if(socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
