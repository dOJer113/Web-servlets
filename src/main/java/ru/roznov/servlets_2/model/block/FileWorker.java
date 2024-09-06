package ru.roznov.servlets_2.model.block;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.roznov.servlets_2.objects.clients.Client;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileWorker {
    public static List<Client> readBlockedUsersFromFile() {
        Gson gson = new Gson();
        List<Client> clients = new ArrayList<>();
        try {
            File file = new File("block.json");
            if (file.exists()) {
                Reader reader = Files.newBufferedReader(file.toPath());
                clients = new ArrayList<>(Arrays.asList(gson.fromJson(reader, Client[].class)));
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading " + e.getMessage());
        }
        return clients;
    }

    public static void writeBlockedUsersToFile(List<Client> clients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File file = new File("block.json");
            if (file.exists()) {
                Writer writer = Files.newBufferedWriter(file.toPath());
                gson.toJson(clients, writer);
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error writing " + e.getMessage());
        }

    }

    public static List<String> getBlockedClientsLogins() {
        Iterator<Client> blockedClientsIterator = FileWorker.readBlockedUsersFromFile().listIterator();
        List<String> blockedClientsLogins = new ArrayList<>();
        while (blockedClientsIterator.hasNext()) {
            blockedClientsLogins.add(blockedClientsIterator.next().getLogin());
        }
        return blockedClientsLogins;
    }
}
