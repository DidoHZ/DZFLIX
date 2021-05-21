package Login;

import sample.JSONreader;

import java.sql.*;

public class GetData {

    public static Connection GetConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(
                "jdbc:sqlserver://app-univdz.database.windows.net:1433;database=App;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", "dido", "Imad2020");
    }

    public static boolean Login(String user, String pass) {
        String query = "select * from users where username='"+user+"' and pass='"+pass+"'";
        try (Statement stmt = GetConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if (rs.getString(2).equals(user) && rs.getString(3).equals(pass)) {
                Controller.setID(rs.getString(1));
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void signup(String user, String pass) {
        String query = "insert into users (username,pass) values ('" + user + "','" + pass + "')";
        try (Statement stmt = GetConnection().createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean CheckUser(String user){
        String query = "select username from users where username='"+user+"'";
        try (Statement stmt = GetConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if (rs.getString(1).equals(user))
                return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    }