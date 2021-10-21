package application;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * public class to create a graph with series of datas (not tested)
 * @author Léa
 *
 */
public class GraphDatas {

	final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    XYChart.Series series1;
    XYChart.Series series2;
    XYChart.Series series3;
	
	public GraphDatas() {
		Stage stage = new Stage();
	    stage.setTitle("Bar Chart Sample");
	    final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
	    bc.setTitle("Country Summary");
	    xAxis.setLabel("Country");       
	    yAxis.setLabel("Value");
	}
	
	public void addDatasSeries() {
		
		series1 = new XYChart.Series();
	    series1.setName("2003");       
	    series1.getData().add(new XYChart.Data(austria, 25601.34));
	    series1.getData().add(new XYChart.Data(brazil, 20148.82));
	    series1.getData().add(new XYChart.Data(france, 10000));
	    series1.getData().add(new XYChart.Data(italy, 35407.15));
	    series1.getData().add(new XYChart.Data(usa, 12000));      
	        
	    series2 = new XYChart.Series();
	    series2.setName("2004");
	    series2.getData().add(new XYChart.Data(austria, 57401.85));
	    series2.getData().add(new XYChart.Data(brazil, 41941.19));
	    series2.getData().add(new XYChart.Data(france, 45263.37));
	    series2.getData().add(new XYChart.Data(italy, 117320.16));
	    series2.getData().add(new XYChart.Data(usa, 14845.27));  
	        
	    series3 = new XYChart.Series();
	    series3.setName("2005");
	    series3.getData().add(new XYChart.Data(austria, 45000.65));
	    series3.getData().add(new XYChart.Data(brazil, 44835.76));
	    series3.getData().add(new XYChart.Data(france, 18722.18));
	    series3.getData().add(new XYChart.Data(italy, 17557.31));
	    series3.getData().add(new XYChart.Data(usa, 92633.68));  
		
	}
}
