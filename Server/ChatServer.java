package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ChatServer {
    private final AutentificationService autentificationService;
    private final Set<CLientHandler> loggedClients;

    public ChatServer() {
        autentificationService = new AutentificationService();
        loggedClients = new HashSet<>();

        try {
            ServerSocket socket = new ServerSocket(8080);
            System.out.println("Сервер запущен!");
            while (true) {
                System.out.println("Ожидание клиентов...");
                Socket clientSocket = socket.accept();
                new CLientHandler(clientSocket, this);
            }
        } catch (IOException e) {
            throw new ChatSetverExceptions("Что-то пошло не так...");
        }

    }

    public synchronized boolean isLoggedIn(String name) {
        return loggedClients.stream().filter(client -> client.getName().equals(name)).findFirst().isPresent();
    }

    public AutentificationService getAutentificationService() {
        return autentificationService;
    }

    public synchronized void subscribe(CLientHandler cLientHandler) {
        loggedClients.add(cLientHandler);
    }

    public synchronized void unsubscribe(CLientHandler cLientHandler) {
        loggedClients.remove(cLientHandler);
    }

    public void broadcast(String message) {
        for (CLientHandler cLientHandler : loggedClients)
            cLientHandler.sendMessage(message);

    }

    public void sendPrivateMessage(String message, String name, String sender) {
        for (CLientHandler client : loggedClients)
            if (client.getName().equals(name)) {
                client.sendMessage(String.format("%s say: %s", sender, message));
                return;//исправил косяк с прошлого занятия
            }
    }
}
