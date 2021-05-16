package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("AAA");
        user.setLastName("BBB");
        user.setAge((byte) 69);
        UserDaoHibernateImpl start = new UserDaoHibernateImpl();
        start.createUsersTable();
        //start.saveUser(user.getName(), user.getLastName(), user.getAge());
        //start.getAllUsers();
        //start.removeUserById(1);
        //start.cleanUsersTable();
        //start.dropUsersTable();



    }
}
