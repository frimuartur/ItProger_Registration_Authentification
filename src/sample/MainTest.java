package sample;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DB isConnectedTest = new DB();
        isConnectedTest.isConnected();
        //isConnectedTest.createTabel("users");
        //isConnectedTest.regUser("arturfrimu","vasile.cataraga@gmail.com","Va-04031998");
    }
}
