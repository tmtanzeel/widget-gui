import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Formatter;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
  String status="Unavialable";
  String time="";
  String current="";
  static String city="delhi";
  static String country="india";
  String icon;
  String remote="";
  
  Button button;

  Label l1, l2, l3, l4, l5;
  Label label1;
  
  ImageView imv, buttonicon;

  GridPane gp;
  Scene scene;
  Task<Void> task1;
  Task<Void> task2;
  Task<Void> task3;
  Task<Void> task4;
  Task<Void> task5;

  @Override
  public void start(Stage primarystage) {
	  gp=new GridPane();
	  gp.setStyle("-fx-background-color: transparent;");
	  gp.setPadding(new Insets(0, 10, 10, 10));
	  gp.setAlignment(Pos.CENTER);
	  gp.setVgap(0);
	  gp.setHgap(5);
	  
	  Image image = new Image(getClass().getResourceAsStream("images/here.png"));
	  buttonicon=new ImageView(image);
	  button=new Button("", buttonicon);
	  button.setOnAction(e -> {
          System.out.println("click event called");
          CreatorClass.askdialogue();
	  });

	  l1=new Label("Locale");
	  l1.getStyleClass().add("today");

	  l2=new Label();
	  l2.getStyleClass().add("day");

	  l3=new Label();
	  l3.getStyleClass().add("time");

	  l4=new Label(""+current);
	  l4.getStyleClass().add("temp");
	  
	  l5=new Label();
	  l5.getStyleClass().add("remote");

	  imv=new ImageView();
	  label1= new Label();
	  label1.setGraphic(imv);

	  gp.add(l1, 0, 0, 4, 1);
	  gp.add(l2, 5, 1, 1, 1);
	  gp.add(l3, 0, 1, 4, 1);
	  gp.add(l4, 0, 2, 1, 1);
	  gp.add(label1, 5, 0, 1, 1);
	  gp.add(l5, 5, 2, 4, 1);
	  gp.add(button, 7, 1, 4, 1);
	  
	  monitor();

	  scene=new Scene(gp, 650, 300, Color.TRANSPARENT);
	  scene.getStylesheets().add(Main.class.getResource("css/Main.css").toExternalForm());

	  primarystage.setScene(scene);
	  primarystage.initStyle(StageStyle.TRANSPARENT);
	  primarystage.setTitle("WINDOW");
	 
	  Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	  
	  //set Stage boundaries to the lower right corner of the visible bounds of the main screen
	  primarystage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 650);
	  primarystage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 300);
	  primarystage.setWidth(650);
	  primarystage.setHeight(300);
	  primarystage.show();
	  new Thread(task1).start();
	  new Thread(task2).start();
	  new Thread(task3).start();
	  new Thread(task4).start();
	  new Thread(task5).start();
  }
  
  public void monitor() {
	  task1=new Task<Void>() {
		  @Override
		  protected Void call() {
			  while(true) {
				  updateMessage(""+time());				  
			  }
		  }
	  };
	  l3.textProperty().bind(task1.messageProperty());
	  
	  task2=new Task<Void>() {
		  @Override
		  protected Void call() {
			  while(true) {
				  updateMessage(""+date());
			  }
		  }
	  };
	  l4.textProperty().bind(task2.messageProperty());
	  
	  task3=new Task<Void>() {
		  @Override
		  protected Void call() {
			  while(true) {
				  try {
					  status="";
					  updateMessage(""+pigeon()+"\n"+city+", "+country);
					  Thread.sleep(5000);
				  }
				  catch(Exception e) {
					  System.out.println("Exception caught: " +e);
					  status="Unavailable";
				  }
			  }
		  }
	  };
	  l2.textProperty().bind(task3.messageProperty());
	  
	  task4 = new Task<Void>() {
	        @Override
	        protected Void call() {
	            System.out.println("run called");
	            //int i = 1;
	            icon="";
	            while (true) {
	                try {
	                    Thread.sleep(1000);
	                    //updateMessage(i + ".png");
	                    updateMessage(icon + ".png");
	                    //System.out.println("i: " + i);	   
	                } catch (Exception e) {
	                }
	                //i++;
	            }
	        }
	    };
	    task4.messageProperty().addListener((observable, oldValue, newValue) -> {
	        System.out.println(newValue);
	        try {
	        	Image image = new Image(getClass().getResourceAsStream("images/" + newValue));
		        imv.setImage(image);
	        }
	        catch(Exception e) {
	        	//System.out.println("You need an open network");	        	
	        }
	    });
	    
		task5=new Task<Void>() {
			  @Override
			  protected Void call() {
				  while(true) {
					  try {
						  remote="";
						  updateMessage(""+orca());
						  Thread.sleep(5000);
					}
					catch (Exception e) {
						e.printStackTrace();
					}				  
				  }
			  }
		  };
		  l5.textProperty().bind(task5.messageProperty());
  }
  
  public String time() {
	  Formatter fmt=new Formatter();
	  Calendar cal=Calendar.getInstance();
	  time=fmt.format("%tR", cal).toString();
	  fmt.close();
	  return time;
  }
  
  public String date() {
	  Formatter fmt=new Formatter();
	  Calendar cal=Calendar.getInstance();
	  current=fmt.format("%ta, %td %tb", cal, cal, cal).toString();
	  fmt.close();
	  return current;
  }
  
  public String orca() throws Exception {
	  String location=city +"," +country;
	  System.out.println(remote);
	  String url = "https://api.opencagedata.com/geocode/v1/json?q="+location+"&key=d32e94f9d716414ba3601a1db6776f0a&pretty=1";		
		 URL obj = new URL(url);
		 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 // optional default is GET
		 con.setRequestMethod("GET");
		 //add request header
		 con.setRequestProperty("User-Agent", "Mozilla/5.0");
		 //int responseCode = con.getResponseCode();
		 //System.out.println("\nSending 'GET' request to URL : " + url);
		 //System.out.println("Response Code : " + responseCode);
		 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 String inputLine;
		 StringBuffer response = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 response.append(inputLine);
		 }
		 in.close();
		 //print in String
		 //System.out.println(response.toString());
		 //Read JSON response
		 JSONObject obj1 = new JSONObject(response.toString());
		 JSONObject jsonObject = obj1.getJSONObject("timestamp");
		 remote+=jsonObject.get("created_http");
		 remote=remote.replaceAll("(?<=\\d):\\d{2}(?=\\s)", "");
		 //System.out.println(remote);
		 
		 JSONObject json = new JSONObject(response.toString());
	     JSONArray result = json.getJSONArray("results");
	     JSONObject result1 = result.getJSONObject(0);
	     JSONObject geometry = result1.getJSONObject("annotations");
	     JSONObject locat = geometry.getJSONObject("timezone");
	     remote+=" | "+locat.get("name").toString();
	     
	     json = new JSONObject(response.toString());
	     result = json.getJSONArray("results");
	     result1 = result.getJSONObject(0);
	     geometry = result1.getJSONObject("annotations");
	     locat = geometry.getJSONObject("timezone");
	     Merlin object=new Merlin();
	     String tz=object.merlin(locat.get("short_name").toString());
	     System.out.println(tz);
	     remote+="\n"+tz;
		 return remote;
  }
  
  public String pigeon() throws Exception {
	  String location;
	  if(country.contentEquals("")) {
		  location=city;  
	  }
	  else {
		  location=city +"," +country;
	  }
	  String url = "http://api.openweathermap.org/data/2.5/weather?q="+location+"&units=metric&appid=f61256a1e44dcae4dbfc9b7e024dfe67";		
		 URL obj = new URL(url);
		 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 // optional default is GET
		 con.setRequestMethod("GET");
		 //add request header
		 con.setRequestProperty("User-Agent", "Mozilla/5.0");
		 //int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL : " + url);
		 //System.out.println("Response Code : " + responseCode);
		 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		 String inputLine;
		 StringBuffer response = new StringBuffer();
		 while ((inputLine = in.readLine()) != null) {
			 response.append(inputLine);
		 }
		 in.close();
		 //print in String
		 //System.out.println(response.toString());
		 //Read JSON response
		 JSONObject myResponse = new JSONObject(response.toString());
		 	    		
		 //PREPARE status
		 JSONObject obj1 = new JSONObject(response.toString());
		 JSONObject jsonObject = obj1.getJSONObject("main");
		 status+=jsonObject.get("temp");
		 status+=" \u00b0C | ";
		 JSONObject obj2 = new JSONObject(response.toString());
		 JSONArray arr = obj2.getJSONArray("weather");
		 status+=arr.getJSONObject(0).get("description");
		 
		 JSONObject obj3 = new JSONObject(response.toString());
		 JSONArray arrx = obj3.getJSONArray("weather");
		 icon=arrx.getJSONObject(0).get("icon").toString();    
		    
         city=myResponse.getString("name").toString();
         
         JSONObject obj4 = new JSONObject(response.toString());
         JSONObject arry = obj4.getJSONObject("sys");
		 country=arry.getString("country").toString(); 
	 
		 System.out.println("Status: "+status);
		 System.out.println("City: "+city);
		 System.out.println("Country: "+country);
		 System.out.println("Icon: "+icon);
		 return status;
  }
  
  public static void main(String[] args) {
	    launch(args);
  }
}
