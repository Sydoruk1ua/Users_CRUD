package ua.in.sydoruk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.in.sydoruk.dao.UserDAO;
import ua.in.sydoruk.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List<User> getUsers(int page, String name) {
        return userDAO.getUsers(page, name);
    }

    @Override
    public Integer getThePageNumber(String name) {
        return userDAO.getThePageNumber(name);
    }
}
