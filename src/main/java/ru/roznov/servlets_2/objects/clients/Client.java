package ru.roznov.servlets_2.objects.clients;

import java.util.Objects;

public class Client {
    private int id;
    private String login;
    private int password;
    private RoleEnum role;

    public Client(int id, String login, int password, RoleEnum role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Client() {
        this.id = 0;
        this.login = "";
        this.role = RoleEnum.UNKNOWN;
        this.password = 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return this.role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        boolean result = Objects.equals(client.getLogin(), this.getLogin()) && client.getId() == this.getId();
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getLogin());
    }
}
