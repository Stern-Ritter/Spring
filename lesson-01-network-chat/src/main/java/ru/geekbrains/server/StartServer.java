package ru.geekbrains.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
    public static void main(String[] args){
        //XML-реализация
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        ChatServer chatServer = context.getBean("chatServer", ChatServer.class);
        chatServer.start(7777);
        //Java-config реализация
        //ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        //ChatServer chatServer = context.getBean("chatServer", ChatServer.class);
        //chatServer.start(7777);
    }
}
