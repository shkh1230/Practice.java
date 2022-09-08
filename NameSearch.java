/*
 * Shehryar Khan
 * COSC-2436
 * Assignment #2
 * Ms.Dash
 */

import java.io.BufferedReader;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shkh1
 */
public class NameSearch extends Application {

    //Store the string values in files in an arraylist
    private static ArrayList<String> names = new ArrayList<>();
    //Convert arraylist to array
    private static String array[];
    private static String str;
    private static int num;

    @Override
    public void start(Stage primaryStage) {
        RadioButton btn1 = new RadioButton("Show Names");
        RadioButton btn2 = new RadioButton("QuickSort");
        Button button = new Button("Submit");
        Label message = new Label("Enter a name below to search");
        Label message2 = new Label();
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();
        text1.setPrefWidth(800);
        text2.setPrefWidth(800);
        text3.setPrefWidth(100);
        GridPane grid = new GridPane();
        grid.add(btn1, 0, 1);
        grid.add(text1, 0, 2);
        grid.add(btn2, 0, 3);
        grid.add(text2, 0, 4);
        grid.add(message, 0, 5);
        grid.add(text3, 0, 6);
        grid.add(button, 0, 7);
        grid.add(message2, 1, 7);

        VBox vbox = new VBox(10, btn1, text1, btn2, text2, message, text3);
        HBox hbox = new HBox(10, button, message2);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                array = names.toArray(new String[names.size()]);
                text1.setText(Arrays.toString(array));
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                array = names.toArray(new String[names.size()]);
                quickSort(array, 0, array.length - 1);
                text2.setText(Arrays.toString(array));
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                array = names.toArray(new String[names.size()]);
                quickSort(array, 0, array.length - 1);
                int num = binarySearch(array, text3.getText());
                if (num >= 0) {
                    str = String.valueOf(num);
                    message2.setText("The name is found at index: " + str);
                } else {

                    message2.setText("The name entered is not found");
                }

            }
        });

        Scene scene = new Scene(grid, 800, 225);
        grid.setVgap(0);
        grid.setHgap(5);
        GridPane.setConstraints(hbox, 0, 7);
        grid.getChildren().addAll(vbox);
        grid.getChildren().addAll(hbox);
        primaryStage.setTitle("Name Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Use Buffer and File reader to read the lines in the names file and add it
     * to ArrayLists Once added to the ArrayLists, I will convert the ArrayLists
     * to String[]
     *
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        {

            BufferedReader bufReader = new BufferedReader(new FileReader("names.txt"));

            String line = bufReader.readLine();
            while (line != null) {
                names.add(line);
                line = bufReader.readLine();
            }

            bufReader.close();
        }
        launch(args);
    }

    /**
     * this method uses recursion and picks an element as pivot and partitions
     * the given array around the picked pivot
     *
     * @param a
     * @param start
     * @param end
     */
    private static void quickSort(String[] a, int start, int end) {

        int i = start;
        int j = end;

        if (j - i >= 1) {
            String pivot = a[i];
            while (j > i) {
                while (a[i].compareTo(pivot) <= 0 && i < end && j > i) {
                    i++;
                }
                while (a[j].compareTo(pivot) >= 0 && j > start && j >= i) {
                    j--;
                }

                if (j > i) {
                    swap(a, i, j);
                }
            }
            swap(a, start, j);
            quickSort(a, start, j - 1);
            quickSort(a, j + 1, end);
        }
    }

    /**
     * This method facilitates the quickSort method's need to swap two elements
     *
     * @param a
     * @param i
     * @param j
     */
    private static void swap(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * this methods requires a sorted array and finds the index of the given
     * value
     *
     * @param a
     * @param x
     * @return
     */
    public static int binarySearch(String[] a, String x) {
        int low = 0;
        int high = a.length - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;

            if (a[mid].compareTo(x) < 0) {
                low = mid + 1;
            } else if (a[mid].compareTo(x) > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

}
