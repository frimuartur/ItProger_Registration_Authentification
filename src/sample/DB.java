package sample;

import java.sql.*;

public class DB {
    private final String host = "localhost";
    private final String port = "3306";
    private final String dbName = "users";
    private final String user = "root";
    private final String password = "02012000Artur";

    Connection dbConn = null;


    public Connection getdbconnection() throws ClassNotFoundException, SQLException {
        String conn_str = "jdbc:mysql://" + host + ":" + port + "/" + dbName +
                "?characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(conn_str, user, password);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getdbconnection();
        System.out.print(dbConn.isValid(1000));
        System.out.println(" mysql is connected ");
    }

    public void createTabel(String tabelName) throws SQLException, ClassNotFoundException {
        String str = "CREATE TABLE IF NOT EXISTS " + tabelName
                + "(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY, login VARCHAR (50), email VARCHAR (50), password TEXT (50))"
                + " ENGINE=MYISAM";
        Statement statement = getdbconnection().createStatement();
        statement.executeUpdate(str);
        System.out.println("The tabel " + tabelName + " was created in mysql");
    }

    public boolean regUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users.users (login, email, password) " + "VALUES (?, ?, ?)";
        Statement statement = getdbconnection().createStatement();
        String select = "SELECT * FROM users.users WHERE login = ? Limit 1";
        PreparedStatement prStSelect = getdbconnection().prepareStatement(select);
        prStSelect.setString(1, login);
        System.out.println(prStSelect.toString());
        ResultSet res = prStSelect.executeQuery();
        if (res.next())
            return false;

        PreparedStatement prSt = getdbconnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);


        //statement.executeUpdate("INSERT INTO Customers " + "VALUES (1002, 'McBeal', 'Ms.', 'Boston', 2004)");

        prSt.executeUpdate();
        System.out.println("The value was insert in the DB " + dbName + " ; Server mysql");
        return true;
    }

    public boolean authUser(String login, String password) throws SQLException, ClassNotFoundException {
        Statement statement = getdbconnection().createStatement();
        String select = "SELECT * FROM users.users WHERE login = '" + login + "' AND password = '" + password + "' Limit 1";
        ResultSet res = statement.executeQuery(select);
        return res.next();

    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM users.articles";
            Statement statement = getdbconnection().createStatement();
            ResultSet res = statement.executeQuery(sql);
            return res;
    }
}


