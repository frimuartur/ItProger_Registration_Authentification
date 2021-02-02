package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Button btn_exit;

    @FXML
    private VBox paneVBox;

    private DB db = new DB();

    @FXML
    void initialize() throws IOException, SQLException, ClassNotFoundException {
        ResultSet res = null;

        res = db.getArticles();

        while (res.next()){
            Node node = null;
            node = FXMLLoader.load(getClass().getResource("/sample/Scenes/article.fxml"));

            Label title = (Label) node.lookup("#title");
            title.setText(res.getString("title"));

            Label intro = (Label) node.lookup("#intro");
            intro.setText(res.getString("intro"));

            final Node nodeSet = node;

            node.setOnMouseEntered(event-> {
                nodeSet.setStyle("-fx-background-color: #707173");
            });

            node.setOnMouseExited(event-> {
                nodeSet.setStyle("-fx-background-color: #343434");
            });

            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
        }

        btn_exit.setOnAction(event -> {

            try {
                FileOutputStream fos = new FileOutputStream("user.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new User(""));

                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/Scenes/sample.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Registration");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
