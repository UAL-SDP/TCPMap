package pt.ual.sdp.nodes.main;

import java.util.HashMap;
import java.util.Map;

public class MainNode {
    private Map<String, ParticipantNodeRecord> nodes;

    public MainNode() {
        this.nodes = new HashMap<>();
    }

    synchronized public ParticipantNodeRecord getNode(String key) {
        int id = hash(key, nodes.size()) +1;
        return nodes.get(String.valueOf(id));
    }

    public int hash(String key, int size) {
        int result = 0;
        for (char c : key.toCharArray()) {
            result += c;
        }
        return (result % size);
    }

    synchronized public Map<String, ParticipantNodeRecord> getNodes() {
        return nodes;
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Wrong number of arguments.");
            System.exit(1);
        }
        int registryPort = Integer.parseInt(args[0]);
        int clientPort = Integer.parseInt(args[1]);

        // Create main node
        MainNode mainNode = new MainNode();

        // Thread to deal with node registry.
        new MainNodeRegister(mainNode, registryPort).start();

        // Thread to deal with client requests
        new MainNodeClient(mainNode, clientPort).start();
    }
}
