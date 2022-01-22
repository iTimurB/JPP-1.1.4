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
//        start.createUsersTable();
        start.saveUser("PPP", "SSS", (byte) 99);
//        start.getAllUsers();
//        start.loadUser(29L);

//        start.removeUserById(34L);
//        start.cleanUsersTable();
//        start.dropUsersTable();



    }
}
