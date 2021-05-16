package launcher;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.util.prefs.Preferences;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        primaryStage.setTitle("Digital Telecommunication - Information Theory Calculator");
        Scene scene = new Scene(root);
        scene.getRoot().getStyleClass().add(JMetroStyleClass.BACKGROUND);

        Preferences prefs = Preferences.userNodeForPackage(launcher.Main.class);
        String defaultValue = "0";
        String propertyValue = prefs.get("DARK", defaultValue);
        JMetro jMetro;
        if(propertyValue.equals("1"))
            jMetro = new JMetro(Style.DARK);
        else
            jMetro = new JMetro(Style.LIGHT);

        jMetro.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(485);
        primaryStage.setMinWidth(625);
        primaryStage.setHeight(670);
        primaryStage.setWidth(630);
        primaryStage.getIcons().addAll(
                new Image(getClass().getResource("/images/icon_16x16.png").openStream()),
                new Image(getClass().getResource("/images/icon_24x24.png").openStream()),
                new Image(getClass().getResource("/images/icon_28x28.png").openStream()),
                new Image(getClass().getResource("/images/icon_32x32.png").openStream()),
                new Image(getClass().getResource("/images/icon_64x64.png").openStream())
                );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
