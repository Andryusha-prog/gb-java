package Client;

import Client.gui.ChatFrame;
import Client.gui.api.Receiver;
import Client.gui.api.Sender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private final ChatFrame frame;
    private final ChatCommunication communication;

    public Chat(String host, int port) {
        communication = new ChatCommunication(host, port);
        frame = new ChatFrame(data -> communication.transmit(data));

        new Thread(() -> {
            Receiver receiver = frame.getReceiver();
            while (true) {
                String message = communication.receive();
                if (!message.isBlank()) {
                    receiver.receive(message);
                }
            }
        }).start();

    }

}


