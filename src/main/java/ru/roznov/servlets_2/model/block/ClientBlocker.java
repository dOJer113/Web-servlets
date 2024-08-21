package ru.roznov.servlets_2.model.block;

import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.Client;

import java.util.List;

public class ClientBlocker {
    public static boolean isClientBlocked(String login) {
        List<String> blockedClientsLogins = FileWorker.getBlockedClientsLogins();
        return blockedClientsLogins.contains(login);
    }

    public static void blockClient(CommandParameters commandParameters) {
        Client client = commandParameters.getParameter("client", Client.class);
        List<Client> clients = FileWorker.readBlockedUsersFromFile();
        if (!clients.contains(client)) {
            clients.add(client);
        }
        FileWorker.writeBlockedUsersToFile(clients);
    }

    public static void unblockClient(CommandParameters commandParameters) {
        Client client = commandParameters.getParameter("client", Client.class);
        List<Client> clients = FileWorker.readBlockedUsersFromFile();
        clients.remove(client);
        FileWorker.writeBlockedUsersToFile(clients);
    }
}
