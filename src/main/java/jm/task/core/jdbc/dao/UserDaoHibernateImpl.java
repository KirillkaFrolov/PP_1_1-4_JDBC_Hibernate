package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                String create = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age TINYINT)";
                session.createNativeQuery(create).executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
                transaction.commit();

            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(new User(name, lastName, age));
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        String delete = "DELETE FROM user WHERE id = ?";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.createNativeQuery(delete).executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List userList = null;
        String select = "From " + User.class.getSimpleName();
        try (Session session = sessionFactory.openSession()) {

            try {
                userList = session.createQuery(select).list();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }
}
