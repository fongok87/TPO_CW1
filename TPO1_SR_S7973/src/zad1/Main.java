/**
 *
 *  @author Smoczyński Rafał S7973
 *
 */

package zad1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Service s;
	private static String weatherJson;
	private static Double rate1;
	private static Double rate2;
	private static String wiki;
	
	private Scene scene; 
    private Tab primary;
    private BorderPane mcontainer, bcontainer;
    private TextArea field;
    private WebView browser;
    private WebEngine engine;   
	
  public static void main(String[] args) {
    s = new Service("Poland");
    weatherJson = s.getWeather("Warsaw");
    rate1 = s.getRateFor("USD");
    rate2 = s.getNBPRate();
    wiki = s.getWiki("Warsaw");
    
    launch(args);
  }
  
  public void start(Stage primaryStage) {
	  
	 prepareScene(primaryStage);
     primaryStage.setTitle("Web Clients");
     primaryStage.setScene(scene);
     primaryStage.show();
  }
  
  private void prepareScene(Stage primaryStage) { 
	  TabPane container = new TabPane();
	  
      primary = new Tab("Web Clients");
      
      container.getTabs().addAll(primary);
  	
      mcontainer = new BorderPane();
      
      field = new TextArea();
      bcontainer = new BorderPane();
      
      browser = new WebView();
      engine = browser.getEngine();
      bcontainer.setCenter(browser);
      primary.setContent(bcontainer); 
      mcontainer.setRight(bcontainer);
      mcontainer.setLeft(field);
     
      TilePane r = new TilePane(); 
      Button pogoda = new Button("Pogoda");
      Button waluta = new Button("Waluta");
      Button nbp = new Button("NBP");
      Button opis = new Button("Opis");
      r.getChildren().add(pogoda);
      r.getChildren().add(waluta);
      r.getChildren().add(nbp);
      r.getChildren().add(opis);
      
      pogoda.setOnAction((event) -> {	
      		field.clear();
      		field.appendText("" + weatherJson);
      });
      
      waluta.setOnAction((event) -> {	
    		field.clear();
    		field.appendText("" + rate1);
      });
      
      nbp.setOnAction((event) -> {	
  			field.clear();
  			field.appendText("" + rate2);
      });
      
      opis.setOnAction((event) -> {	
    		engine.load(wiki);
        });
     
      mcontainer.setBottom(r);
      
      primary.setContent(mcontainer); 
      
      scene = new Scene(container, 1300, 600);
  }
  
}
