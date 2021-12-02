package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE if not exists users(" +
                    "id integer primary key auto_increment, " +
                    "name varchar(45) not null, " +
                    "lastname varchar(45) not null, " +
                    "age tinyint not null );");
            connection.commit();
            statement.close();
            System.out.println("Create user table");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            connection.commit();
            statement.close();
            System.out.println("Drop user table");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastname, age)  values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
            System.out.println("User " + name + " add data base");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE  id =" + id + ";");
            connection.commit();
            statement.close();
            System.out.println("User delete in data base");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
            statement.close();
            resultSet.close();
            for (User userL : userList){
                System.out.println(userL);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            util.getClose();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = util.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE users");
            connection.commit();
            statement.close();
            System.out.println("Clean users table");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            util.getClose();
        }
    }
}
