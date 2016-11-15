package ua.in.sydoruk.service;

import ua.in.sydoruk.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    User getUser(int id);

    void deleteUser(int id);

    List<User> getUsers(int page, String name);

    Integer getThePageNumber(String name);
}
