package ru.geekbrains.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import java.sql.SQLException;

@Configuration
public class JavaConfig {
    @Bean(name="userRepository")
    public UserRepository userRepository(@Value("jdbc:mysql://localhost:3306/network_chat?serverTimezone=Europe/Moscow") String url,
                                         @Value("root") String username, @Value("Erv148") String password) throws SQLException {
        return new UserRepository(url, username, password);
    }
    @Bean(name="authService")
    public AuthServiceJdbcImpl authService(UserRepository userRepository) throws SQLException {
        return new AuthServiceJdbcImpl(userRepository);
    }
    @Bean(name="chatServer")
    public ChatServer chatServer(AuthService authService){
        return new ChatServer(authService);
    }
}