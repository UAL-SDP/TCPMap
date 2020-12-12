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
            case "D":
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
            case "L":
                for (ParticipantNodeRecord participantNodeRecord : mainNode.getNodes().values()) {
                    participantNodeSocket = getParticipantNodeSocket(participantNodeRecord);
                    participantNodePrintWriter = SocketUtil.getPrintWriter(participantNodeSocket);
                    participantNodePrintWriter.println(operation);
                    participantNodePrintWriter.flush();
                }
                break;
        }

        SocketUtil.closeSocket(clientSocket);
        SocketUtil.closeSocket(participantNodeSocket);
    }

    private Socket getParticipantNodeSocket(ParticipantNodeRecord node) {
        return SocketUtil.getSocket(node.getAddress(), node.getPort());
    }

    private Socket getParticipantNodeSocket(String key) {
        return getParticipantNodeSocket(mainNode.getNode(key));
    }
}
