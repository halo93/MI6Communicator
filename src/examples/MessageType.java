/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examples;

import jade.lang.acl.ACLMessage;

/**
 *
 * @author halo93
 */
public enum MessageType {
    REQUEST(ACLMessage.REQUEST, "Request"),
    INFORM(ACLMessage.INFORM, "Inform"),
    FAILURE(ACLMessage.FAILURE, "Failure");

    private final int code;
    private final String type;

    private MessageType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
    
}
