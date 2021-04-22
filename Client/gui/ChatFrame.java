package Client.gui;

import Client.gui.api.Receiver;
import Client.gui.api.Sender;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {
    private final JTextArea chatArea;

    public ChatFrame(Sender sender) {
        setTitle("Chat v1.0");
        setBounds(0, 0, 600, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        top.add(chatArea, BorderLayout.CENTER);

        top.add(chatArea);
        add(top, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        bottom.add(inputField, BorderLayout.CENTER);
        JButton submBtn = new JButton("Submit");
        submBtn.addActionListener(new SubmitButtonListener(inputField, sender));
        bottom.add(submBtn, BorderLayout.EAST);

        add(top, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);

    }

    public Receiver getReceiver() {
        return (message) -> {
            if (!message.isBlank()) {
                chatArea.append(message);
                chatArea.append("\n");
            }
        };
    }
}
