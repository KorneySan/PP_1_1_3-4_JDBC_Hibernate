package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private static final UserService userService;

    static {
        userService = new UserServiceImpl();
    }
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        userService.createUsersTable();
        User[] users = new User[4];
        users[0] = new User("Alan", "First", (byte) 10);
        users[1] = new User("Bertran", "Second", (byte) 20);
        users[2] = new User("Collin", "Third", (byte) 30);
        users[3] = new User("Douglas", "Fourth", (byte) 40);
        for (User user: users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
        }
        System.out.println("--- // ---");
        List<User> newUsers = userService.getAllUsers();
        for (User user: newUsers) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
