package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                    "id integer primary key auto_increment not null, " +
                    "name varchar(45) not null ," +
                    "lastname varchar(45) not null ," +
                    "age tinyint not null);");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users CASCADE");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users WHERE  id =" + id + ";");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT * FROM users;");
            userList = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE TABLE users;");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}