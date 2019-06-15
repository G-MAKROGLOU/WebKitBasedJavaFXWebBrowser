# WebKitBasedJavaFXWebBrowser
A fully functional web browser made with WebView. FXML file is provided as well as instructions on how to instantiate and embed the browser in your projects.

===>INSTRUCTIONS<===
1) Put all files in the same folder/package of your project otherwise you'll have to edit the paths in the source code.

2) Since the browser is created with JavaFX, you need a container to embed the browser in. The default parent container is a TabPane but you can do it with any other container that accepts an AnchorPane inside it. In that case you'll have to fix the source code and pass your parent container of choice as a parameter to the core function of the browser class. Depending the parent container you chose you may have to configure the anchors for each side of the browser anchorpane.

3) To instantiate the browser you write a try-catch statement since the core method of the browser class throws an IOException. To do that write the following:

    WebBrowserComponentController webBrowser = new WebBrowserComponentController();
        try {
            webBrowser.createBrowserInTabPane(the parent container of your choice here);
        } catch (IOException e) {
            e.printStackTrace( );
        }
        
 Once you do that, locate the parts where the parent container is mentioned inside the method, and replace it with the parent container you inserted in the parameter. 
        
4) The browser is using a few font-awesome icons for JavaFX. For that reason you'll have to download fontawesomefx-8.9 and add the jar to your project's external libraries. You can download fontawesomefx-8.9 from: https://bitbucket.org/Jerady/fontawesomefx/downloads/

5) Alongside the java and fxml files, are provided three more files, a png, an html, and a css file. These three are responsible for showing the user an error message in case the page they requested can't be reached. The image was downloaded from: "https://www.flaticon.com and it's a property of https://www.freepik.com/". 


