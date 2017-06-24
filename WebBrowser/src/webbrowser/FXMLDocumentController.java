/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Ryan
 */
public class FXMLDocumentController implements Initializable {
    @FXML Button backButton;
    Image imageDecline = new Image(getClass().getResourceAsStream("back.png"));
    
    @FXML
    Button forwardButton;
    Image imageDeclinez = new Image(getClass().getResourceAsStream("forward.png"));
    
    @FXML
    Button refreshButton;
    Image imageDeclinezz = new Image(getClass().getResourceAsStream("refresh2.png"));
    
    @FXML AnchorPane anchorPane;
    
    @FXML 
    WebView webView;
    
    @FXML
    Button searchButton;
    Image imageDeclinew = new Image(getClass().getResourceAsStream("search.png"));
    
    @FXML
    TabPane tabPane;
    Image imageDeclineq = new Image(getClass().getResourceAsStream("tab.png"));
    
    
    @FXML Button newTabButton;
   

    
    @FXML TextField textField;
    
    WebEngine webEngine;
    
    
    @FXML Button directButton;
    Image imageDecline4 = new Image(getClass().getResourceAsStream("direct.png"));
    
    
    @FXML
    public void handleEvent(ActionEvent event)
    {
        String searchString = textField.getText();
        webEngine.load("https://www.google.com/search?q=" + searchString);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backButton.setGraphic(new ImageView(imageDecline));
        forwardButton.setGraphic(new ImageView(imageDeclinez));
        refreshButton.setGraphic(new ImageView(imageDeclinezz));
        newTabButton.setGraphic(new ImageView(imageDeclineq));
        searchButton.setGraphic(new ImageView(imageDeclinew));    
        directButton.setGraphic(new ImageView(imageDecline4));
        webEngine = webView.getEngine();
        Tab tab = new Tab("New Tab ");
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        
        
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() { 
        @Override 
        public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                //to switch the webview pane to stored webpages   
                System.out.println(tabPane.getTabs().size() + "hi");
                if(tabPane.getTabs().size() == 0)           
                {                      
                    System.exit(0);           
                }
        }
        });     
        

           
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {           
            if (Worker.State.SUCCEEDED.equals(newValue)) {           
                tabPane.getSelectionModel().getSelectedItem().setText(webEngine.getTitle());                      
                textField.setText(webEngine.getLocation());           
            }       
        });               
            
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {                        
            @Override                       
            public void changed(ObservableValue ov, Boolean t, Boolean t1) {                            
                    Platform.runLater(new Runnable() {                                       
                        @Override                                        
                        public void run() {                                            
                            if (textField.isFocused() && !textField.getText().isEmpty()) {                                                 
                                textField.selectAll();                                                                         
                            }                                     
                        }                             
                    });                       
                }           
            });
       
        anchorPane.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.E) && e.isControlDown())
            {
                boringAddTab();
            }                           
            if (e.getCode().equals(KeyCode.ENTER))
            {                        
                String searchString = textField.getText();        
                webEngine.load("https://www.google.com/search?q=" + searchString);
            }              
            if(e.getCode().equals(KeyCode.W) && e.isControlDown() && tabPane.getTabs().size() != 0)                
            {                                     
                tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());

            }                                        
        });    
                                     
    }
    
    
    @FXML
    private void addTab(ActionEvent actionEvent)
    {
        boringAddTab();
    }
    
    private void boringAddTab()
    {
        Tab tab = new Tab("New Tab");
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        webEngine.load("https://www.google.com/search?q=zebra"); 
    }
    
}
