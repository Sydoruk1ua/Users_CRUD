package ua.in.sydoruk.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.in.sydoruk.model.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final int NUMBER_OF_USERS_PER_PAGE = 10;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void updateUser(User user) {
        User userToUpdate = getUser(user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setAge(user.getAge());
        userToUpdate.setisAdmin(user.getisAdmin());
        getCurrentSession().update(userToUpdate);
    }

    @Override
    public User getUser(int id) {
        User user = (User) getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = getUser(id);
        if (user != null)
            getCurrentSession().delete(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(int page, String name) {
        Criteria criteria = getCurrentSession().createCriteria(User.class)
                .setMaxResults(NUMBER_OF_USERS_PER_PAGE).setFirstResult(page * NUMBER_OF_USERS_PER_PAGE);
        if (name != "")
            criteria.add(Restrictions.like("name", (name + "%")));

        return criteria.list();
    }

    @Override
    public Integer getThePageNumber(String name) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        if (name != "")
            criteria.add(Restrictions.like("name", (name + "%")));

        int i = criteria.list().size();

        if (i % NUMBER_OF_USERS_PER_PAGE == 0) return i / NUMBER_OF_USERS_PER_PAGE;
        return i / NUMBER_OF_USERS_PER_PAGE + 1;

    }
}
