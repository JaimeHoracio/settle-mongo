package uy.com.hachebackend.settle.infrastructure.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uy.com.hachebackend.settle.application.services.UserService;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.SettleRepositoryImpl;

@Component
public class HandlerUser {

    private static String EMAIL;

    private static String NAME;

    private static String PASSWORD;

    @Autowired
    private SettleRepositoryImpl mongoRepository;
    @Autowired
    private UserService userService;

    public HandlerUser(@Value("${app.server.user.admin.email}") final String email,
                       @Value("${app.server.user.admin.name}") final String name,
                       @Value("${app.server.user.admin.password}") final String password) {
        EMAIL = email;
        NAME = name;
        PASSWORD = password;
    }

    public void initUserAdmin() {
        userService.createUser(EMAIL, NAME, PASSWORD, false, mongoRepository).subscribe(u ->
                System.out.println(">>>> Usuario administrador creado...."));
    }
}
