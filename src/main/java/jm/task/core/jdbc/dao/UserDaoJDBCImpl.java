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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        long id = 1;
        String selectId = "SELECT * FROM users ORDER BY id DESC LIMIT 1;";
        try (Connection connection = util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectId);
            while (result.next()) {
                id = result.getLong("id") + 1;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(id, name, lastname, age)  values (?,?,?,?);");
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
            connection.commit();
            statement.close();
            result.close();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.getClose();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User user;
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
            statement.close();
            resultSet.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            util.getClose();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = util.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE users");
            connection.commit();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            util.getClose();
        }
    }
}
