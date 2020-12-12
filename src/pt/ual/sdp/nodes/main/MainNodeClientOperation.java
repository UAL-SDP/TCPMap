package pt.ual.sdp.nodes.main;

import pt.ual.sdp.util.SocketUtil;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainNodeClientOperation extends Thread {
    private MainNode mainNode;
    private Socket clientSocket;

    public MainNodeClientOperation(MainNode mainNode, Socket socket) {
        this.mainNode = mainNode;
        this.clientSocket = socket;
    }

    @Override
    public synchronized void start() {
        Scanner scanner = SocketUtil.getScanner(clientSocket);
        Socket participantNodeSocket = null;
        String operation = scanner.nextLine();
        String key, value, response;
        Scanner participantNodeScanner;
        PrintWriter participantNodePrintWriter;
        switch (operation) {
            case "R":
                key = scanner.nextLine();
                value = scanner.nextLine();
                participantNodeSocket = getParticipantNodeSocket(key);

                participantNodePrintWriter = SocketUtil.getPrintWriter(participantNodeSocket);
                participantNodePrintWriter.println(operation);
                participantNodePrintWriter.flush();
                participantNodePrintWriter.println(key);
                participantNodePrintWriter.flush();
                participantNodePrintWriter.println(value);
                participantNodePrintWriter.flush();

                participantNodeScanner = SocketUtil.getScanner(participantNodeSocket);
                response = participantNodeScanner.nextLine();

                PrintWriter clientPrintWriter = SocketUtil.getPrintWriter(clientSocket);
                clientPrintWriter.println(response);
                clientPrintWriter.flush();
                break;
            case "C":
                key = scanner.nextLine();
                participantNodeSocket = getParticipantNodeSocket(key);
                participantNodePrintWriter = SocketUtil.getPrintWriter(participantNodeSocket);
                participantNodePrintWriter.println(operation);
                participantNodePrintWriter.flush();
                participantNodePrintWriter.println(key);
                participantNodePrintWriter.flush();

                participantNodeScanner = SocketUtil.getScanner(participantNodeSocket);
                response = participantNodeScanner.nextLine();

                clientPrintWriter = SocketUtil.getPrintWriter(clientSocket);
                clientPrintWriter.println(response);
                clientPrintWriter.flush();
                break;
            case "D":
                break;
            case "L":
                break;
        }

        SocketUtil.closeSocket(clientSocket);
        SocketUtil.closeSocket(participantNodeSocket);
    }

    private Socket getParticipantNodeSocket(String key) {
        Socket participantNodeSocket;
        ParticipantNodeRecord node = mainNode.getNode(key);
        participantNodeSocket = SocketUtil.getSocket(node.getAddress(), node.getPort());
        return participantNodeSocket;
    }


}
