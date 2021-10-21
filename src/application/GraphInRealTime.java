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
 * @author L�a
 *
 */

public class GraphInRealTime extends LineChart<String, Number> {

	// To limit the number of date shown, so that the graph do not become unreadable
	// as time pass
	final int WINDOW_SIZE = 20;
	ScheduledExecutorService scheduledExecutorService;

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
		// setTitle("Realtime JavaFX Charts");
		setTitle(graphTitle);
		setAnimated(false); // disable animations

		//To stop the graph (no more data add)
		Button stop = new Button("STOP");
		stop.setOnAction(action -> {
			scheduledExecutorService.shutdownNow();
		});

		// Later, if thread doesn't work, to activate the receptor
		Button startReception = new Button("Active la r�ception");
		startReception.setOnAction(action -> {
			// scheduledExecutorService.shutdownNow();
			// recepServor = new Receptor(32);

		});
	}

	public void addRandomNumberSeries() {
		// defining a series to display data
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Data Series");

		// add series to chart
		getData().add(series);
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
				series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));

				if (series.getData().size() > WINDOW_SIZE)
					series.getData().remove(0);
			});
		}, 0, 1, TimeUnit.SECONDS);

	}

	public void addDataSended(String dataSended) {
		// defining a series to display data
		XYChart.Series<String, Number> seriesSend = new XYChart.Series<>();
		seriesSend.setName("Data Send Series");

		// add series to chart
		getData().add(seriesSend);
		// this is used to display time in HH:mm:ss format
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		// setup a scheduled executor to periodically put data into the chart
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		
		//We convert the data (in String format) to integer
		//int dataShow = Integer.parseInt(dataSended); //for integer
		float dataShow = Float.parseFloat(dataSended); //For float

		// put dummy data onto graph per second
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			// get a random integer between 0-10
			//Integer random = ThreadLocalRandom.current().nextInt(10);

			// Update the chart
			Platform.runLater(() -> {
				// get current time
				Date now = new Date();
				// put random number with current time
				seriesSend.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), dataShow));

				if (seriesSend.getData().size() > WINDOW_SIZE)
					seriesSend.getData().remove(0);
			});
		}, 0, 1, TimeUnit.SECONDS);
	}
}
