package com.example.demo3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Button[][] cards;
    private String[][] letters;
    private int clicks = 0;
    private int firstRow;
    private int firstCol;
    private int secondRow;
    private int secondCol;
    //...
    @Override
    public void start(Stage primaryStage) {
        //...
        cards = new Button[4][4];
        letters = new String[4][4];
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                String letter = null;
                switch ((row*4+col)%8) {
                    case 0: letter = "<==3"; break;
                    case 1: letter = "!"; break;
                    case 2: letter = "<O-<"; break;
                    case 3: letter = ";)"; break;
                    case 4: letter = "<3"; break;
                    case 5: letter = ":-3"; break;
                    case 6: letter = ":)"; break;
                    case 7: letter = ":("; break;
                }
                Button card = new Button();
                letters[row][col] = letter;
                card.setMinSize(100, 100);
                cards[row][col] = card;
                grid.add(card, col, row);
                int finalRow = row;
                int finalCol = col;
                card.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // handle the click event for the card
                        cards[finalRow][finalCol].setText(letters[finalRow][finalCol]);
                        if (clicks == 0) {
                            firstRow = finalRow;
                            firstCol = finalCol;
                            clicks++;
                        } else if (clicks == 1) {
                            secondRow = finalRow;
                            secondCol = finalCol;
                            clicks++;
                            checkMatch();
                        }
                    }
                });
            }
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
}
