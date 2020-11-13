package ru.geekbrains;

import org.springframework.stereotype.Component;

@Component
public class Camera {

    private final CameraRoll cameraRoll;

    public Camera(CameraRoll cameraRoll) {
        this.cameraRoll = cameraRoll;
    }

    public void doPhoto() {
        System.out.println("Щелк!");
        cameraRoll.processing();
    }
}
