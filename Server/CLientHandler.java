package Server;

import db.Users;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CLientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatServer chatServer;
    private String name;
    File file = new File("ChatHistory.txt");

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
                                readChatHistory();//Выводятся последние 100 строк переписки
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
                } else {
                    chatServer.broadcast(String.format("%s: %s", name, message));
                    writeChatHistory(String.format("%s: %s%n", name, message));//Храним всю историю чата, без перезаписывания
                }
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

    void writeChatHistory(String message){//Запись Чата в текстовый файл
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, true))){
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            bos.write(bytes);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    void readChatHistory(){// чтение последних 100 строк из текстового файла истории чата
        int readLines = 0;
        StringBuilder sb = new StringBuilder();

        try{
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            long fileLength = file.length() - 1;
            raf.seek(fileLength);

            for (long ptr = fileLength - 1; ptr >= 0; ptr--) {
                raf.seek(ptr);
                char ch = (char) raf.read();
                if(ch == '\n') {
                    readLines++;
                    if(readLines == 100)
                        break;
                }
                sb.append(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //sb.reverse();
        sendMessage(sb.reverse().toString());
    }
}
