package fr.unicaen.iutcaen.networkProtocol;

import java.io.Serializable;

public class Message implements Serializable {
    private ProtocolData data;

    public Message( ProtocolData data) {
        this.data = data;
    }

    public ProtocolData getData() { return this.data; } 
    public void setData(ProtocolData data) { this.data = data; }
}