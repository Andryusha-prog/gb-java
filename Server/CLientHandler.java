package Server;

import db.Users;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class CLientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatServer chatServer;
    private String name;

    public CLientHandler(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new ChatSetverExceptions("Что-то пошло не так... ", e);
        }
        new Thread(() -> {
            doAutentification();
            listen();
        }).start();
    }

    private void doAutentification() {
        sendMessage("Добро пожаловать! Пожалуйста, пройдите аутентификацию! \n Внимание! Вы будете отключены от сервера, если не пройдете аутентификацию в течении 120 сек!!!");
        long connectTime = System.currentTimeMillis();
        while (true) {
            try {
                String message = in.readUTF();
                if (System.currentTimeMillis() - connectTime < 120000){//Пока что не хватает времени разобраться с Socket setSoTimeout() и Timer/TimerTask  :(
                    if (message.startsWith("-auth")) {
                        String[] credentialsStruct = message.split("\\s");
                        String login = credentialsStruct[1];
                        String password = credentialsStruct[2];

                        Optional<Users> mayBeCredentials = chatServer.getAutentificationService().findEntryByCredentials(login, password);
                        if (mayBeCredentials.isPresent()) {
                            Users credentials = mayBeCredentials.get();
                            if (!chatServer.isLoggedIn(credentials.getUserName())) {
                                name = credentials.getUserName();
                                chatServer.broadcast(String.format("Пользователь %s вошел в чат", name));
                                sendMessage(String.format("Добро пожаловать в чат, %s!!!", name));
                                chatServer.subscribe(this);
                                return;
                            } else {
                                sendMessage("Пользователь с таким именем уже находится в чате");
                            }
                        } else {
                            sendMessage("Некорректно введен логин или пароль");
                        }

                    } else {
                        sendMessage("Некорректный способ прохождения аутентификации!! Пожалуйста Введите команду: -auth логин пароль");
                    }
            }
                else {
                    sendMessage("Соединение разорвано! Пожалуйста, перезапустите програму и попробуйте снова!");
                    socket.close();
                }
            } catch (IOException e) {
                throw new ChatSetverExceptions("Что-то пошло не так при аутентификации....");
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new ChatSetverExceptions("Что-то пошло не так при попытке отправить сообщение", e);
        }
    }

    public void receiveMessage() {
        while (true) {
            try {
                String message = in.readUTF();
                if (message.startsWith("/w")) {
                    String[] messArr = message.split("\\s");
                    chatServer.sendPrivateMessage(messArr[2], messArr[1], this.getName());
                } else chatServer.broadcast(String.format("%s: %s", name, message));
            } catch (IOException e) {
                throw new ChatSetverExceptions("Что-то пошло не так при попытке переслать сообщение", e);
            }
        }
    }

    public String getName() {
        return name;
    }

    private void listen() {
        receiveMessage();
    }
}
