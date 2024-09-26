package ru.roznov.servlets_2.controler.command;

public class Page {
    private RedirectEnum redirectEnum;
    private String url;

    public Page(RedirectEnum redirect, String url) {
        this.redirectEnum = redirect;
        this.url = url;
    }

    public RedirectEnum getRedirectEnum() {
        return this.redirectEnum;
    }

    public void setRedirectEnum(RedirectEnum redirectEnum) {
        this.redirectEnum = redirectEnum;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

