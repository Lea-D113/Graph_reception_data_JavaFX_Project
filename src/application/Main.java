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
 * - a graph of data to show data sended (not working yet)
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
	HBox statusbar = new HBox(); //To create a bar with a text compartment for a Name (author name)
	statusbar.getChildren().addAll(new Label("Name: "),
		new TextField ( "  Mobydick  "));
	return statusbar;
	}

	// We add elements in our graphics window "scene"
	private Node createMainContent() throws IOException{
		Group g = new Group(); //To add each element in this group to show them
		
		// Different type of predefined messages to send
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
		
		
		//Initialization of the receiver:
		Receptor recepServor;
		String dataToShow = null;
		String dataTest = "3.14"; //Random initialization to not have a problem with a data = null
		int it = 0;
		//To do the graph
		GraphInRealTime lineChart = new GraphInRealTime("Time/s", "Grades", "Grades sended");
		
		//g.getChildren().add(lineChart); //test to see if the graph show even when the receiver run
		// test fail, nothing change if this line is either before or after the lines to add data
		
		/*lineChart.addDataSended(dataTest);
		while(it<10) {
			//Integer randomt = ThreadLocalRandom.current().nextInt(10);
			float randomt = ThreadLocalRandom.current().nextInt(10);
			dataTest = Float.toString(randomt);
			lineChart.addDataSended(dataTest); // I have several constant value this way?!?
			it++;
		}*/
		try {
			recepServor = new Receptor(32); // We open our receiver
			while(it<20) { //This way, all the datas are send, and at 
			dataToShow = recepServor.receivedDatas2(); // processing of the received date
			//To show data on the graph
			lineChart.addDataSended(dataToShow); //make dataToShow a list?
			it++;
			}
			recepServor.close(); // we close the socket
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//To do the graph
		//lineChart.addDataSended(dataToShow);
		g.getChildren().add(lineChart); // we add our graph to our window
        
		//To receive data:
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
        
		
		return g;
	}
	
}
