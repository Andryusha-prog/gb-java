package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatCommunication {
    private final DataInputStream in;
    private final DataOutputStream out;
    public ChatCommunication(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при установке соединения!", e);
        }
    }
    public void transmit(String data){
        try {
            out.writeUTF(data);
        } catch (IOException e) {
            throw new RuntimeException("произошла ошибка при передаче сообщения!", e);
        }
    }

    public String receive(){
        try {
            return in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException("произошла ошибка при приеме сообщения!", e);
        }
    }
}

