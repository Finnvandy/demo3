package com.example.demo3;


import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private String[] symbols = new String[]{"<==3","!","<O-<",";)","<3",":-3",":)",":(",
            "<==3","!","<O-<",";)","<3",":-3",":)",":("};
    private Button[][] cards;
    private String[][] letters;
    private int clicks = 0;
    private ObjectProperty<Button> firstButton = new SimpleObjectProperty<>();
    private ObjectProperty<Button> secondButton = new SimpleObjectProperty<>();

    {
        firstButton.addListener((observableValue, button, t1) -> {
            if (button!=null&&t1!=null) {
                if (letters[GridPane.getRowIndex(button)][GridPane.getColumnIndex(button)]
                        .equals(letters[GridPane.getRowIndex(t1)][GridPane.getColumnIndex(t1)])) {
                    t1.setText(letters[GridPane.getRowIndex(t1)][GridPane.getColumnIndex(t1)]);
                    firstButton.getValue().setDisable(true);
                    secondButton.getValue().setDisable(true);
                    firstButton.setValue(null);
                    secondButton.setValue(null);
                }
                else {
                    secondButton.getValue().setText("");
                    System.out.println("test");
                    firstButton.setValue(null);
                    secondButton.setValue(null);
                    return;
                }
            }
            else if (t1!=null) {
                t1.setText(letters[GridPane.getRowIndex(t1)][GridPane.getColumnIndex(t1)]);
            }
        });
    }

    private int firstRow;
    private int firstCol;
    private int secondRow;
    private int secondCol;
    //...
    @Override
    public void start(Stage primaryStage) {
        //...
        int[] arrangement = fromNChooseR(16,16);
        System.out.println(Arrays.stream(arrangement).mapToObj(value -> String.valueOf(value)).collect(Collectors.joining(",")));
        cards = new Button[4][4];
        letters = new String[4][4];
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        for (int i=0; i<16; i++) {
            int row = arrangement[i]%4;
            int col = arrangement[i]/4;
            Button card = new Button();
            card.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
            letters[row][col] = symbols[i];
            card.setMinSize(100, 100);
            cards[row][col] = card;
            grid.add(card, col, row);
            int finalRow = row;
            int finalCol = col;
            card.setOnAction(event -> {
                firstButton.setValue(card);
                if (firstButton.getValue() != null) {
                    secondButton.setValue(firstButton.getValue());
                }
            });
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        //...
    }
    private void checkMatch() {
        // check if the two selected cards match
        if (letters[firstRow][firstCol].equals(letters[secondRow][secondCol])) {
            System.out.println("It's a match!");
        } else {
            System.out.println("Try again.");
            cards[firstRow][firstCol].setText("");
            cards[secondRow][secondCol].setText("");
        }
        clicks = 0;
    }
    //...

    public int[] fromNChooseR (int n, int r) {
        int[] indices = new int[r];
        for (int i=0; i<r; i++) {
            indices[i] = Double.valueOf(Math.floor(Math.random()*(n-i))).intValue();
        }
        for (int i=0; i<r; i++) {
            outer: while (true) {
                for (int j = 0; j < i; j++) {
                    if (indices[j] == indices[i]) {
                        indices[i]++;
                        continue outer;
                    }
                }
                break outer;
            }
        }
        for (int i = 0; i < Arrays.stream(indices).max().getAsInt(); i++) {
            outer: while (true) {
                for (int j = 0; j < indices.length; j++) {
                    if (indices[j]==i) break outer;
                }
                for (int j = 0; j < indices.length; j++) {
                    if (indices[j]>i) indices[j]--;
                }
                break outer;
            }
        }
        return indices;
    }
}

