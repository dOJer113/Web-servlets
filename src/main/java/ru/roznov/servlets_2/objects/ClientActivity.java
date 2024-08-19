package ru.roznov.servlets_2.objects;

public class ClientActivity {
    private int id;
    private int activity;

    public ClientActivity(int id, int activity) {
        this.id = id;
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
}
