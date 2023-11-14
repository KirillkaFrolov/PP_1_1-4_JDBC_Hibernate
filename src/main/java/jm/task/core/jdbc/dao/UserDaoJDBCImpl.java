package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl {
    private static  final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {

        String create = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age TINYINT);";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(create);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public void dropUsersTable() throws SQLException {
        String dropTable = "DROP TABLE user";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dropTable);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, lastName, age) values (?,?,?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void removeUserById(long id) throws  SQLException {
        String delete = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAll = "SELECT * FROM user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAll);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));

                users.add(user);

                connection.commit();
            }
        }catch (SQLException s) {
            s.printStackTrace();
        }
        return users;
    }



    public void cleanUsersTable() throws SQLException {
        String truncate = "TRUNCATE TABLE user";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(truncate);
            preparedStatement.executeUpdate(truncate);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }
}

