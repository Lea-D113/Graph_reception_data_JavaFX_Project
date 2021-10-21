package application;


import java.io.IOException;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
//import javafx.event.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sensor.Emettor;
import javafx.scene.input.MouseEvent;

//import Envois.java;
//import Receveur.java;

/**
 * Main class with a graphics windows, with tools bars and containing:
 * - a text
 * - a line
 * - an image
 * - a password field
 * - a graph of data
 * @author Léa D
 *
 */
public class Main extends Application{
	
	// initialization of the graphics window
	public void start (Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setTop(createToolbar());
			root.setBottom(createStatusbar()) ;
			root.setCenter(createMainContent());
			Scene scene = new Scene (root, 800, 400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Demo JavaFX");
			primaryStage.show();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	// To create "object" in our graphics window
	private Node createToolbar() { // To create a tool bar with a button "New", "Open",
		// a separation, and a button "Clean"
		return new ToolBar(new Button("New"), new Button("Open"), new Separator(),new Button("Clean"));
	}

	private Node createStatusbar(){
	HBox statusbar = new HBox(); //To create a bar with a text compartment for the Name
	statusbar.getChildren().addAll(new Label("Name: "),
		new TextField ( "  Mobydick  "));
	return statusbar;
	}

	// We add elements in our graphics window "scene"
	private Node createMainContent() throws IOException{
		Group g = new Group(); //To add each element in this group to show them
		
		//final PasswordField passField = new PasswordField();
		
		/* How we use this group g */
		// here you fill g with whatever Nodes you want
		// using g.getChildren().add(...)
		
		/** We add a text in cyan, and in large letter
		 * In this case, the text is:
		 * Hello
		 * My name is MobyDick
		 **/
		/*Text text = new Text ("Hello\n My name is MobyDick");
		text.setTextAlignment(TextAlignment.JUSTIFY);
		text.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 40)); // Font, and size of the letters
		text.setFill(Color.CYAN); // letters color
		text.setStrokeWidth(2);
		text.setStroke(Color.BLACK); // outlines color
		text.setUnderline(true);
		text.setX(10); // Position in the window
		text.setY(50);
		g.getChildren().add(text); // /!\ important to add an element to our graphics window
		*/
		
		/* To add a line in our graphics window
		 * 
		 */
		/*Line line = new Line ( ) ;
		line.setStartX(300.0);
		line.setStartY(150.0);
		line.setEndX(500.0);
		line.setEndY(150.0);
		line.setStroke(Color.GREEN);
		g.getChildren().add(line);*/
		
		/* To add a picture to our graphic window */
		// URL of the picture we want to add
		Image image = new Image("https://i0.wp.com/travelcoachchile.com/wp-content/uploads/2020/07/chepu-baleine.png?ssl=1");
		ImageView imageView = new ImageView(image);
		imageView.setX(410); // To set the position of the picture in the window
		imageView.setY(20);
		imageView.setFitHeight(200); // To define the size of the picture added
		imageView.setFitWidth(400);
		Glow glow = new Glow(); // Effect: here, the picture glow
		glow.setLevel(0.3); // Level of the glow for our picture
		imageView.setEffect(glow);
		g.getChildren().add(imageView);
		imageView.setImage(null);
		
		/*line.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				public void handle (MouseEvent e) {
					System.out.println("mouse click!");
					// la phrase "mouse click!" apparait dans le terminal
					//quand je clique sur la ligne
				}
			} ) ;*/
		
		/* We add a text compartment with a button
		 * Here, we can write in the compartment text and "submit" it with our button
		 * For now, submitting it doesn't do anything other than block the text 
		 * in the compartment
		 */
		/*Text txt = new Text("Password");
		PasswordField passField = new PasswordField();
		Button button = new Button("Submit");
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.BOTTOM_CENTER); // Here, our password field is in the bottom center of out window
		gridPane.add(txt, 0, 1 ); // We add our element in our grid in the order wanted (to "link" them)
		gridPane.add(passField, 1, 1);
		gridPane.add(button, 0, 2);
		button.setStyle("-fx-background-color: darkslateblue");
		g.getChildren().add(gridPane);*/
		
		//With more than one image to display, there no images displayed
		/*Image image2 = new Image("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.ethic-ocean.org%2Fwp-content%2Fuploads%2F2021%2F02%2Fsaut-baleine-1030x687.jpg&imgrefurl=https%3A%2F%2Fwww.ethic-ocean.org%2Fbaleine-mammifere-marin-continuer-proteger%2F&tbnid=-HqrUMZsJUixDM&vet=10CAoQMyhoahcKEwjomLLz4I3zAhUAAAAAHQAAAAAQAw..i&docid=y_-RvXxqjRGqOM&w=1030&h=687&q=baleine%20png&client=firefox-b-d&ved=0CAoQMyhoahcKEwjomLLz4I3zAhUAAAAAHQAAAAAQAw");
		ImageView imageView2 = new ImageView(image);
		imageView2.setX(500);
		imageView2.setY(70);
		imageView2.setFitHeight(200);
		imageView2.setFitWidth(400);
		g.getChildren().add(imageView2);
		imageView2.setImage(null);
		
		Image image3 = new Image("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.astrolabe-expeditions.org%2Fwp-content%2Fuploads%2F2018%2F11%2FPlanctonPlanchecarre.png&imgrefurl=https%3A%2F%2Fwww.astrolabe-expeditions.org%2Fprogramme-de-sciences%2Fplanktidex%2F&tbnid=pRbdpFLtbRlGRM&vet=12ahUKEwihhvSp7o3zAhWwEWMBHXJ9BYoQMyg4egQIARBH..i&docid=e9p8LcZA7PUsQM&w=1650&h=1650&q=plancton%20png&hl=fr&client=firefox-b-d&ved=2ahUKEwihhvSp7o3zAhWwEWMBHXJ9BYoQMyg4egQIARBH");
		ImageView imageView3 = new ImageView(image);
		//imageView3.setX(700);
		//imageView3.setY(200);
		imageView3.setX(500);
		imageView3.setY(70);
		imageView3.setFitHeight(200);
		imageView3.setFitWidth(400);
		g.getChildren().add(imageView3);
		imageView3.setImage(image3);*/
		
		
		
		/*SetOnAction to do actions (here with the button Submit)*/
		/*button.setOnAction( action -> {
			String pwd = passField.getText();
			if(pwd.equals("Baleine")) { // Here, if we text writed in the text compartment 
										// of our passField is "Baleine", then a picture of whale appear
				//imageView.setImage(null);
				imageView2.setImage(image2);
				//imageView3.setImage(image3);
				//g.getChildren().add(imageView);
				//imageView.setImage(null);
			};
		});*/
		
		//Chart creation (datas graph) (not tested completely)
		/*
	    
	    	        
	    Scene scene  = new Scene(bc,800,600);
	    bc.getData().addAll(series1, series2, series3);
	    stage.setScene(scene);
	    stage.show();*/
		
		// Messages predefined to send to other
		String message1 = "Je cherche du miel...";
		String message2 = "Bonjour! Comment allez-vous?";
		String message3 = "Je veux du miel";
		String message4 = "3.14;5.76";
		/*
		//Then we send messages to different machines (and it work!)
		Emettor msgSend;
		Emettor msgSend2;
		try {
			msgSend = new Emettor("10.10.24.113", 51802); // We define to whom (which IP adress and which port) we send our message
			msgSend.send(message4); //for server Agustin // message send
			//msgSend2 = new Emetteur("10.10.26.57", 63240);
			//msgSend2 = new Emetteur("10.10.25.82", 65355);
			//msgSend.envoi(message3); //for server Agustin (variable port)
			//msgSend2.envoi( message4);
			Thread.sleep(100); // we wait for a bit before closing the socket
			msgSend.close();
			//msgSend2.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		//Initialization of the receptor:
		Receptor recepServor;
		String dataToShow = null;
		String dataTest = "3.14";
		int it = 0;
		//To do the graph
		GraphInRealTime lineChart = new GraphInRealTime("Time/s", "Grades", "Grades sended");
		/*lineChart.addDataSended(dataTest);
		while(it<10) {
			//Integer randomt = ThreadLocalRandom.current().nextInt(10);
			float randomt = ThreadLocalRandom.current().nextInt(10);
			dataTest = Float.toString(randomt);
			lineChart.addDataSended(dataTest); // I have several constant value this way?!?
			it++;
		}*/
		try {
			recepServor = new Receptor(32); // We open our receptor
			dataToShow = recepServor.receivedDatas2(); // processing of the received date
			//To show data on the graph
			lineChart.addDataSended(dataToShow); //make dataToShow a list?
			recepServor.close(); // we close the socket
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*//To do the graph
		GraphInRealTime lineChart = new GraphInRealTime("Time/s", "Grades", "Grades sended");
		lineChart.addDataSended(dataToShow);*/
        //g.getChildren().addAll(lineChart,stop);
		g.getChildren().add(lineChart); // we add our graph to our window
        
		//Pour recevoir:
		/*try {
			//DatagramSocket recep = new DatagramSocket();
			//int numPort = recep.getLocalPort();
			Receveur receiveServer = new Receveur();
			//receiveServer.reception(recep, 32, stop);
			receiveServer.reception();
			//msgSend.envoi("10.10.27.42", numPort, message4);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
        
        ServerThreadDatagrams consoleServer = new ServerThreadDatagrams();
        try {
        	consoleServer.start();
        } catch(Exception e) {
			e.printStackTrace();
		}
			
		return g;
	}
	
}
