package pt.ual.sdp;

class ParticipantNodeRecord {
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