package yupiter;

import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.web.*;

public class FXMLDocumentController implements Initializable
{
    @FXML
    private WebView browser;
    public static WebEngine engine;
    @FXML
    private TextField path;
    @FXML
    private Label lbl;
    @FXML
    private ProgressBar processor;
    
    
    public void RefreshPage(ActionEvent e)
    {
        engine.executeScript("location.reload(true);");
    }
    
    public void LoadWeb(ActionEvent ev)
    {
        String url = toURL(path.getText());
        engine.load(url);
        
        engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>()
        {
            @Override
            public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue, Throwable newValue)
            {
                System.out.println("recieved exception: " + newValue.getMessage());
                
                if(newValue.getMessage() == "File not found")
                {
                    engine.load(getClass().getResource("notFound.html").toExternalForm());
                    path.setText("File doesn't exists!");
                }
                else if(newValue.getMessage() == "Unknown host")
                {
                    engine.load(getClass().getResource("noInternet.html").toExternalForm());
                    path.setText("Connection ERROR!");
                }
            }
        });
    }
    
    public void GoBack(ActionEvent e)
    {
        engine.executeScript("window.history.back();");
    }
    
    public void GoForward(ActionEvent e)
    {
        engine.executeScript("window.history.forward();");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        engine = browser.getEngine();
        engine.load(getClass().getResource("home.html").toExternalForm());
        engine.setJavaScriptEnabled(true);
        engine.locationProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                path.setText(newValue);
            }
        });
        
        engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                processor.setProgress(newValue.doubleValue());
                try
                {
                    lbl.setText(String.valueOf(newValue.intValue()) + "%");
                }
                catch(Exception e)
                {
                    
                }
            }
        });
        
        path.setText("http://");
    }
    
    public String toURL(final String str)
    {
        try
        {   
            return new URL(str).toExternalForm();
        }
        catch(MalformedURLException e)
        {
            return null;
        }
    }
    
    public String toURI(final String str)
    {
        try
        {   
            return new URI(str).toASCIIString();
        }
        catch(URISyntaxException e)
        {
            return null;
        }
    }
}
