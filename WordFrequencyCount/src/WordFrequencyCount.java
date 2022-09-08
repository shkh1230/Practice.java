/*
 * Shehryar Khan
 * COSC-2436
 * Assignment #3
 * Ms.Dash
 */
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author shkh1
 */
public class WordFrequencyCount extends Application {

    private static final String Column1MapKey = "A";
    private static final String Column2MapKey = "B";
    private static ArrayList<String> names = new ArrayList<>();
    private static String array1[];
    private static int array2[];

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        Scene scene = new Scene(new Group());
        stage.setTitle("Word Frequency count");
        stage.setWidth(300);
        stage.setHeight(500);

        TableColumn<Map, String> firstDataColumn = new TableColumn<>("Words");
        TableColumn<Map, String> secondDataColumn = new TableColumn<>("Word Frequency Count");
        TableView table_view = new TableView<>(generateDataInMap());

        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
        firstDataColumn.setMinWidth(130);
        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
        secondDataColumn.setMinWidth(130);

        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);
        table_view.getColumns().setAll(firstDataColumn, secondDataColumn);

        final VBox vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table_view);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
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

    private ObservableList<Map> generateDataInMap() throws FileNotFoundException {
        List<Integer> count = new ArrayList<Integer>();
        array1 = names.toArray(new String[names.size()]);
        quickSort(array1, 0, array1.length - 1);
        for (int i = 0; i < array1.length; i++) {
            
                String words = names.get(i);
                int occurrences = Collections.frequency(names, words);
                count.add(occurrences);
            
            
        }
        ObservableList<Map> allData = FXCollections.observableArrayList();

        for (int i = 1; i < array1.length; i++) {
            Map<String, String> dataRow = new HashMap<>();


            String value1 = array1[i];

            array2 = count.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
       
            String value2 = String.valueOf(array2[i]);

            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2);

            allData.add(dataRow);
        }
        return allData;
    }
}
