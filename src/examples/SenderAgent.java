package examples;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.Objects;
import javax.swing.DefaultListModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author halo93
 */
public class SenderAgent extends GuiAgent {
    
    private SplashBanner gui;
    private SendMessageBehaviour sendMessageBehaviour;

    @Override
    protected void setup() {
        super.setup(); 
        gui = new SplashBanner(this);
        gui.showSplash();
        System.out.println("Hello World! My name is " + getLocalName());
//         /** Registration with the DF */
//        DFAgentDescription dfd = new DFAgentDescription();
//        ServiceDescription sd = new ServiceDescription();
//        sd.setType("SenderAgent");
//        sd.setName(getName());
//        sd.setOwnership("ExampleReceiversOfJADE");
//        sd.addOntologies("MainAgent");
//        dfd.setName(getAID());
//        dfd.addServices(sd);
//        try {
//            DFService.register(this, dfd);
//        } catch (FIPAException e) {
//            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
//            doDelete();
//        }
//
        sendMessageBehaviour = new SendMessageBehaviour();
    }
    
    
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        if (ge.getType() == 0) {
            addBehaviour(sendMessageBehaviour);
        }
    }
    
    public AMSAgentDescription[] getAgentList() {
        System.out.println("Getting Agent List");
        // Getting agents list
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();// object to search

            // the container exist on the System
            //define infinity result to C
            c.setMaxResults(Long.valueOf(-1));

            //putt all agent found on the system to the agents list
            agents = AMSService.search(this, new AMSAgentDescription(), c);


        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }


        return agents;
    }
    
    public class SendMessageBehaviour extends OneShotBehaviour {
    
    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        String receiverName = gui.getMainBoard().getReceiverGUID();
        //gui.getMainBoard().getReceiverName().substring(0, gui.getMainBoard().getReceiverName().indexOf("@"))

        String messageContent = gui.getMainBoard().getMessageContent();

        if (gui.getMainBoard().getMessageType().equals(MessageType.REQUEST)) {
            msg = new ACLMessage(ACLMessage.REQUEST);
        }

        else if (gui.getMainBoard().getMessageType().equals(MessageType.INFORM)) {
            msg = new ACLMessage(ACLMessage.INFORM);
        }


        msg.addReceiver(new AID(receiverName, AID.ISGUID));
        msg.setLanguage("English");
        msg.setContent(messageContent);
        send(msg);
        
        gui.getMainBoard().setReceivedMessage("You: " + messageContent + " [" + gui.getMainBoard().getMessageType() + "]" + "\n");

        System.out.println("****I Sent Message to::> " + receiverName + "*****" + "\n" +
                "The Content of My Message is::>" + msg.getContent());
    }
    
}
    
    public class ReceiveMessage extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String messagePerformative;
        private String messageContent;
        private String senderName;


        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {

                messagePerformative = ACLMessage.getPerformative(msg.getPerformative());
                messageContent = msg.getContent();
                senderName = msg.getSender().getLocalName();

                if (MessageType.FAILURE.getType().equalsIgnoreCase(messagePerformative)) {
                    System.out.println("Communication failed!...");
                    gui.getMainBoard().showErrorMessage("Agent Not Found or Busy! Please try again");
                } else {
                    if (gui.getMainBoard() != null) {
                        gui.getMainBoard().setReceivedMessage(senderName + " : " + messageContent + " [" + messagePerformative + "]" + "\n");

                    System.out.println(" ****I Received a Message***" + "\n" +
                            "The Sender Name is::>" + senderName + "\n" +
                            "The Content of the Message is::> " + messageContent + "\n" +
                            "::: And Performative is::> " + messagePerformative + "\n");
                    System.out.println("ooooooooooooooooooooooooooooooooooooooo");
                    }
                }
            }
        }

    }
    
    public class GetAgentListBehaviour extends TickerBehaviour {

        public GetAgentListBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            MainBoard mainBoard = gui.getMainBoard();
            if (mainBoard != null) {
                mainBoard.updateAgentList(getAgentList());
            }
        }

       
    }
    
}
