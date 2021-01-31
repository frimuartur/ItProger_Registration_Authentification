package sample.Controller;

import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.DB;

public class RegController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_reg;

    @FXML
    private TextField email_reg;

    @FXML
    private PasswordField pass_reg;

    @FXML
    private CheckBox confidentials;

    @FXML
    private Button btn_reg;

    @FXML
    private TextField login_auth;

    @FXML
    private PasswordField pass_auth;

    @FXML
    private Button btn_auth;

    private DB db = new DB();

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {
            login_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            pass_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Registration");

            if (login_reg.getCharacters().length() <= 3) {
                login_reg.setStyle("-fx-border-color: red");
                return;
            } else if (email_reg.getCharacters().length() <= 3) {
                email_reg.setStyle("-fx-border-color: red");
                return;
            } else if (pass_reg.getCharacters().length() <= 3) {
                pass_reg.setStyle("-fx-border-color: red");
                return;
            } else if (!confidentials.isSelected()) {
                btn_reg.setText("Insert simbol");
                return;
            }
            String password = md5Strin(pass_reg.getCharacters().toString());

            try {
                boolean isReg = db.regUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), password);
                if (isReg) {
                    login_reg.setText("");
                    email_reg.setText("");
                    pass_reg.setText("");
                    btn_reg.setText("Finish");
                } else
                    btn_reg.setText("Insert an other login");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_auth.setOnAction(event -> {
            login_auth.setStyle("-fx-border-color: #fafafa");
            pass_auth.setStyle("-fx-border-color: #fafafa");


            if (login_auth.getCharacters().length() <= 3) {
                login_auth.setStyle("-fx-border-color: red");
                return;
            }  else if (pass_auth.getCharacters().length() <= 3) {
                pass_auth.setStyle("-fx-border-color: red");
                return;
            }
            String password = md5Strin(pass_auth.getCharacters().toString());

            try {
                boolean isAuth = db.authUser(login_auth.getCharacters().toString(),  password);
                if (isAuth) {
                    login_auth.setText("");
                    pass_auth.setText("");
                    btn_auth.setText("Log In");
                } else
                    btn_auth.setText("Don't exist");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    public static String md5Strin(String pass){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32)
            md5Hex = "0" + md5Hex;

        return md5Hex;

    }
}
