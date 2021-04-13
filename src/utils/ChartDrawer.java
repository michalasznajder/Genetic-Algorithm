package utils;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class ChartDrawer extends Application {
    private static int generations;
    private static List<Double> fitness;

    public static int getGenerations() {
        return generations;
    }

    public static void setGenerations(int generations) {
        ChartDrawer.generations = generations;
    }

    public static List<Double> getFitness() {
        return fitness;
    }

    public static void setFitness(List<Double> fitness) {
        ChartDrawer.fitness = fitness;
    }

    @Override
    public void start(Stage stage) throws Exception {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Generations");

        NumberAxis yAxis = new NumberAxis   ();
        yAxis.setLabel("Fitness");

        LineChart linechart = new LineChart(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("No of schools in an year");

        for(int i = 0; i < generations; i++){
            series.getData().add(new XYChart.Data(i, fitness.get(i)));
        }
        //Setting the data to Line chart
        linechart.getData().add(series);

        //Creating a Group object
        Group root = new Group(linechart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage
        stage.setTitle("Line Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
}

