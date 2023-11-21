package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        Util.getConnection();

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Aleksey", "Alekseev", (byte) 20);
        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Roman", "Romanov", (byte) 30);
        userService.saveUser("Andrey", "Andreev", (byte) 35);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
