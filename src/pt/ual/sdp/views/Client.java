package pt.ual.sdp.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        String mainNodeAddress = args[0];
        int mainNodePort = Integer.parseInt(args[1]);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] commands = line.split(" ");
            String key, value;
            Socket socket = null;
            PrintWriter mainNodePrintWriter;
            Scanner mainNodeScanner;
            switch(commands[0].toUpperCase()) {
                case "R":
                    key = commands[1];
                    value = commands[2];

                    socket = getSocket(mainNodeAddress, mainNodePort);
                    mainNodePrintWriter = getPrintWritter(socket);

                    mainNodePrintWriter.println(commands[0].toUpperCase());
                    mainNodePrintWriter.println(key);
                    mainNodePrintWriter.flush();
                    mainNodePrintWriter.println(value);
                    mainNodePrintWriter.flush();

                    mainNodeScanner = getScanner(socket);
                    String response = mainNodeScanner.nextLine();
                    System.out.println(response);
                    break;
                case "C":
                    key = commands[1];
                    socket = getSocket(mainNodeAddress, mainNodePort);
                    mainNodePrintWriter = getPrintWritter(socket);
                    mainNodePrintWriter.println(commands[0].toUpperCase());
                    mainNodePrintWriter.flush();
                    mainNodePrintWriter.println(key);
                    mainNodePrintWriter.flush();

                    mainNodeScanner = getScanner(socket);
                    value = mainNodeScanner.nextLine();
                    System.out.println(value);
                    break;
                case "D":
                    key = commands[1];
                    break;
                case "Q":
                    System.exit(0);
                    break;
                case "L":
                    break;
                default:
                    System.out.println("Invalid command.");
            }
            if(socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static Scanner getScanner(Socket socket) {
        Scanner mainNodeScanner = null;
        try {
            mainNodeScanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainNodeScanner;
    }

    private static PrintWriter getPrintWritter(Socket socket) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    private static Socket getSocket(String mainNodeAddress, int mainNodePort) {
        Socket socket = null;
        try {
            socket = new Socket(mainNodeAddress, mainNodePort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
