package pt.ual.sdp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MainNodeRegister extends Thread {
    private int port;
    private int nodeCount;
    private Map<String, ParticipantNodeRecord> nodes;

    public MainNodeRegister(int port) {
        super();
        this.port = port;
        this.nodeCount = 1;
        this.nodes = new HashMap<>();
    }

    @Override
    public synchronized void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Cannot create ServerSocket on port "+port);
            e.printStackTrace();
        }
        while(true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Could not create socket on accept.");
                e.printStackTrace();
            }
            String nodeAddress = socket.getInetAddress().toString();
            Scanner scanner = null;
            try {
                scanner = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Could not read from node socket.");
                e.printStackTrace();
            }
            int nodePort = Integer.parseInt(scanner.nextLine());
            int nodeId = nodeCount++;
            ParticipantNodeRecord nodeRecord = new ParticipantNodeRecord(nodeAddress, nodePort);
            nodes.put(String.valueOf(nodeId), nodeRecord);
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Could not write to node socket.");
                e.printStackTrace();
            }
            printWriter.println(nodeId);
            printWriter.flush();
        }
    }

    private class ParticipantNodeRecord {
        private String address;
        private int port;

        public ParticipantNodeRecord(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
    }
}