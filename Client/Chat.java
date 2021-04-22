package Client;

import Client.gui.ChatFrame;
import Client.gui.api.Receiver;
import Client.gui.api.Sender;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private final ChatFrame frame;
    private final ChatCommunication communication;
    File file = new File("localHistoryChat.txt");

    public Chat(String host, int port) {
        communication = new ChatCommunication(host, port);
        frame = new ChatFrame(data -> communication.transmit(data));
        clearFile();//При каждом новом подключении очищается файл с прошлой историей


        new Thread(() -> {
            Receiver receiver = frame.getReceiver();
            while (true) {
                String message = communication.receive();
                if (!message.isBlank()) {
                    receiver.receive(message);
                   printLocalHistory(message);//Запись в файл
                }
            }
        }).start();

    }

    void printLocalHistory(String message) {

            try (FileWriter fw = new FileWriter(file, true)) {
                fw.append(message);
                fw.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void clearFile() {
            if (file.length() != 0) {
                try (FileWriter fw = new FileWriter(file)) {
                    fw.append("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}


