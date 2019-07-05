package yupiter;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static yupiter.FXMLDocumentController.engine;

public class Yupiter extends Application
{
    public Stage stage;
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.DECORATED);
        stage.setMinWidth(330);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("yupiter4.png")));
        stage.setTitle("The Yupiter - 1.0");
        
        engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                try
                {
                    stage.setTitle(engine.getTitle() + " - Yupiter Browser 1.0");
                }
                catch(Exception e)
                {
                    
                }
            }
        });
            
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
