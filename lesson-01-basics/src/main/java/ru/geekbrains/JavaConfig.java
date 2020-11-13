package ru.geekbrains;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@Configuration
//@PropertySource("classpath:application.properties")
public class JavaConfig {

    @Bean
    public CameraRoll cameraRoll() {
        return new BlackAndWhiteRoll();
    }

    @Bean
    public Camera camera() {
        return new Camera(cameraRoll());
    }
}
