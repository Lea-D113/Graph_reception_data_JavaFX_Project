package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

/**
 * public class to create a graph we fill in real time
 * 
 * @author Léa
 *
 */

public class GraphInRealTime extends LineChart<String, Number> {

	// To limit the number of date shown, so that the graph do not become unreadable
	// as time pass
	final int WINDOW_SIZE = 20;
	
	ScheduledExecutorService scheduledExecutorService;
	// defining a series to display data
	XYChart.Series<String, Number> seriesData = new XYChart.Series<>(); 

	/**
	 * Constructor to initialize the graph and add the buttons of the graph
	 * 
	 * @param String xlabel: the time for this graph
	 * @param String ylabel: the type of data added
	 * @param String graphTitle: title of the graph
	 */
	public GraphInRealTime(String xlabel, String ylabel, String graphTitle) {

		super(new CategoryAxis(), new NumberAxis());

		// defining the axes
		// getXAxis().setLabel("Time/s");
		getXAxis().setLabel(xlabel);
		getXAxis().setAnimated(false); // axis animations are removed
		// getYAxis().setLabel("Value");
		getYAxis().setLabel(ylabel);
		getYAxis().setAnimated(false); // axis animations are removed

		// creating the line chart with two axis created above
		// final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		// setTitle("Real time JavaFX Charts");
		setTitle(graphTitle);
		setAnimated(false); // disable animations

		// to name series
		seriesData.setName("Data Series");

		// add series to chart
		getData().add(seriesData);

		// To stop the graph (no more data add)
		Button stop = new Button("STOP");
		stop.setOnAction(action -> {
			scheduledExecutorService.shutdownNow();
		});

		// Later, if thread doesn't work, to activate the receptor, so not used for now
		Button startReception = new Button("Active la réception");
		startReception.setOnAction(action -> {
			// scheduledExecutorService.shutdownNow();
			// recepServor = new Receptor(32);

		});
	}

	/**
	 * Methods to add random number as data to our graph (without returning
	 * anything) and make the x axis the time axis
	 */
	public void addRandomNumberSeries() {

		// this is used to display time in HH:mm:ss format
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		// setup a scheduled executor to periodically put data into the chart
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		// put dummy data onto graph per second
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			// get a random integer between 0-10
			Integer random = ThreadLocalRandom.current().nextInt(10);

			// Update the chart
			Platform.runLater(() -> {
				// get current time
				Date now = new Date();
				// put random number with current time
				seriesData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));

				if (seriesData.getData().size() > WINDOW_SIZE)
					seriesData.getData().remove(0);
			});
		}, 0, 1, TimeUnit.SECONDS);

	}

	/**
	 * Methods to add the parameter of our as a data to our graph and make the x
	 * axis the time axis
	 * 
	 * @param dataSended
	 */
	public void addDataSended(String dataSended) {
		/*// defining a series to display data
		XYChart.Series<String, Number> seriesData = new XYChart.Series<>();
		seriesData.setName("Data Send Series");

		// add series to chart
		getData().add(seriesData);*/
		// this is used to display time in HH:mm:ss format
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		// setup a scheduled executor to periodically put data into the chart
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		// We convert the data (in String format) to integer
		// int dataShow = Integer.parseInt(dataSended); //for integer
		float dataShow = Float.parseFloat(dataSended); // For float

		// put dummy data onto graph per second
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			// get a random integer between 0-10
			// Integer random = ThreadLocalRandom.current().nextInt(10);

			// Update the chart
			Platform.runLater(() -> {
				// get current time
				Date now = new Date();
				// put random number with current time
				seriesData.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), dataShow));

				if (seriesData.getData().size() > WINDOW_SIZE)
					seriesData.getData().remove(0);
			});
		}, 0, 1, TimeUnit.SECONDS);
	}
}
