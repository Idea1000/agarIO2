package fr.unicaen.iutcaen.agario2.network;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private Object data;

    public Message(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    // Getters et setters
    public String getType() { return type; }
    public Object getData() { return data; }
    public void setType(String type) { this.type = type; }
    public void setData(Object data) { this.data = data; }
}