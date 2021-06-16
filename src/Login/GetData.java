package Login;

import java.sql.*;
import java.util.ArrayList;

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
                Controller.ID = rs.getString(1);
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void signup(String user, String pass) throws SQLException, ClassNotFoundException {
        String query = "insert into users (username,pass) values ('" + user + "','" + pass + "')";
        Statement stmt = GetConnection().createStatement();
        stmt.executeUpdate(query);
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
    public static ArrayList<Integer> getUserList(String ID){
        ArrayList<Integer> item = new ArrayList<>();
        String query = "select link from mylist where id='"+ID+"'";
        try (Statement stmt = GetConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                item.add(rs.getInt(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }
    public static void AddToUserList(String UID, int ItemID, String Type){
        String query = "insert into mylist values ("+UID+",'"+ItemID+"','"+Type+"')";
        try (Statement stmt = GetConnection().createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void DeleteFromUserList(String UID, int ItemID){
        String query = "delete from mylist where id='"+UID+"' and link='"+ItemID+"'";
        try (Statement stmt = GetConnection().createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    }