package application.tabClasses.homeTab.webViewClasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;

import java.io.IOException;


public class WebBrowserComponentController {
    @FXML private WebView webView;
    private WebEngine webEngine;
    @FXML private Button backButton;
    @FXML private Button forwardButton;
    @FXML private Button refreshButton;
    @FXML private TextField navigationBar;
    @FXML private Button goButton;

    //History combo box
    @FXML private ComboBox historyComboBox;

    public ComboBox getHistoryComboBox() {
        return historyComboBox;
    }

    public TextField getNavigationBar() {
        return navigationBar;
    }

    public Button getGoButton() {
        return goButton;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getForwardButton() {
        return forwardButton;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }

    public void createBrowserInTabPane(TabPane homeMainTabPane) throws IOException {
        FXMLLoader webBrowserLoader = new FXMLLoader( );
        webBrowserLoader.setLocation(getClass( ).getResource("WebBrowser.fxml"));
        Parent root = webBrowserLoader.load( );
        WebBrowserComponentController controller = webBrowserLoader.getController( );
        //=============>Change this part to add the browser to another component<============
        Tab browserTab = new Tab("Workspace Browser");
        browserTab.setContent(root);
        browserTab.setOnClosed(e -> controller.getWebEngine( ).load(null));
        homeMainTabPane.getTabs( ).add(browserTab);
        //============>Change the parameter of this function with the type of the node you want to place the browser in <=============
        controller.setWebEngine(controller.getWebView( ).getEngine( ));
        controller.getWebEngine().setJavaScriptEnabled(true);
        controller.getWebEngine( ).load("https://www.google.com");
        controller.getNavigationBar().setText(controller.getWebEngine().getLocation());



        //===================================>BROWSER EVENTS<========================================================
        //***********************************************************************************************************
        //History setup and events
        WebHistory webHistory = controller.getWebEngine( ).getHistory( );
        controller.getHistoryComboBox( ).setItems(webHistory.getEntries( ));

        controller.getHistoryComboBox( ).setOnAction(e -> {
            int index = controller.getHistoryComboBox( ).getSelectionModel( ).getSelectedIndex( ) - webHistory.getCurrentIndex( );
            webHistory.go(index);
            controller.getNavigationBar().setText(controller.getHistoryComboBox().getEditor().getText());
        });

        webHistory.currentIndexProperty( ).addListener(new ChangeListener<Number>( ) {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                controller.getHistoryComboBox( ).getSelectionModel( ).select(newValue.intValue( ));
            }
        });

        controller.getHistoryComboBox( ).setConverter(new StringConverter<WebHistory.Entry>( ) {
            @Override
            public String toString(WebHistory.Entry object) {
                return object == null ? null : object.getUrl( );
            }


            @Override
            public WebHistory.Entry fromString(String string) {
                throw new UnsupportedOperationException( );
            }
        });


        //Back button event
        controller.getBackButton( ).setOnAction(e -> {
            webHistory.go(-1);
            controller.getNavigationBar().setText(controller.getWebEngine().getLocation());
        });


        //Forward button event
        controller.getForwardButton( ).setOnAction(e ->{
            webHistory.go(1);
            controller.getNavigationBar().setText(controller.getWebEngine().getLocation());
        });

        //Refresh button event
        controller.getRefreshButton( ).setOnAction(e -> {
            controller.getWebEngine().reload();
        });

        //Navigation bar (TextField) event through go button
        controller.getGoButton( ).setOnAction(e -> {
            String fullAddress = " ";
            String url = controller.getNavigationBar( ).getText( );
            if (!url.contains("https://")) {
                fullAddress = "https://" + url;
            } else {
                fullAddress = url;
            }
            controller.getWebEngine( ).load(fullAddress);
            controller.getNavigationBar().setText(controller.getWebEngine().getLocation());
            controller.getWebEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue == Worker.State.FAILED){
                    String urlFail = WebBrowserComponentController.class.getResource("index.html").toExternalForm();
                    controller.getWebEngine().load(urlFail);
                }
            });

        });

        //navigation bar event (TextField) on Enter press
        controller.getNavigationBar( ).setOnAction(e -> {
            String fullAddress = " ";
            String url = controller.getNavigationBar( ).getText( );
            if (!url.contains("https://")) {
                fullAddress = "https://" + url;
            } else {
                fullAddress = url;
            }
            controller.getWebEngine( ).load(fullAddress);
            controller.getNavigationBar().setText(controller.getWebEngine().getLocation());
            controller.getWebEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue == Worker.State.FAILED){
                    String urlFail = WebBrowserComponentController.class.getResource("index.html").toExternalForm();
                    controller.getWebEngine().load(urlFail);
                }
            });
        });

    }//end of method
}//end of class


