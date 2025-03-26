package fr.unicaen.iutcaen.network;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private Object data;

    /**
     * Creates a Message object
     * @param type
     * @param data
     */
    public Message(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Gets the type of the message
     * @return the type of the message
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the data of the message
     * @return the data of the message
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the type of the message
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the data of the message
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }
}