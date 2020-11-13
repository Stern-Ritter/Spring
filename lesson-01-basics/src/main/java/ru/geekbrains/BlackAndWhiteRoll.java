package ru.geekbrains;

import org.springframework.stereotype.Component;

public class BlackAndWhiteRoll implements CameraRoll {

    @Override
    public void processing() {
        System.out.println("-1 один черно-белый кадр");
    }
}
