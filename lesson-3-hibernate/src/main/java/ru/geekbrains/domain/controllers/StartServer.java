package ru.geekbrains.domain.controllers;

public class StartServer {

    public static void main(String[] args) {
        InternetShop internetShop = new InternetShop();
        //internetShop.generateValues();
        internetShop.startService();
    }
}
