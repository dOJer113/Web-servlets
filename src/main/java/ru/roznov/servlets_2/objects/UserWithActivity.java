package ru.roznov.servlets_2.objects;

public class UserWithActivity {
    private int id;
    private String login;
    private int password;
    private RoleEnum role;
    private int activity;

    public UserWithActivity(int id, String login, int password, RoleEnum role, int activity) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.activity = activity;
    }


    public int getId() {
        return this.id;
    }


    public String getLogin() {
        return this.login;
    }


    public int getPassword() {
        return this.password;
    }


    public RoleEnum getRole() {
        return this.role;
    }

    public int getActivity() {
        return this.activity;
    }
}
