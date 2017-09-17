package com.veromeev.bsuir.dip.l1.fxapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestInputParameters {

    private ArrayList<Double> parameters = new ArrayList<>();

    public ArrayList<Double> getParameters() {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Label labelCoef = new Label();
        labelCoef.setText(
                "Input coefficient: "
        );
        Label labelGamma = new Label();
        labelGamma.setText(
                "Input gamma: "
        );
        labelCoef.setMinSize(100, 20);
        labelGamma.setMinSize(100, 20);

        TextField textCoef = new TextField();
        textCoef.setPromptText("Enter double value");
        textCoef.setMinSize(70, 20);
        TextField textGamma = new TextField();
        textGamma.setMinSize(70, 20);
        textGamma.setPromptText("Enter double value");

        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setMinSize(70, 30);
        confirm.setOnAction(event -> onConfirmedClicked(textCoef, textGamma, primaryStage));

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(labelCoef, labelGamma);
        vBox1.setPadding(new Insets(10,10,10,10));
        vBox1.setSpacing(10);
        VBox vBox2 = new VBox();
        vBox2.getChildren().addAll(textCoef, textGamma);
        vBox2.setPadding(new Insets(10,10,10,10));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox1, vBox2);
        VBox vBox3 = new VBox();
        vBox3.getChildren().addAll(hBox, confirm);
        vBox3.setPadding(new Insets(20,20,20,20));
        vBox3.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.setPadding(new Insets(30,30,30,30));
        root.getChildren().add(vBox3);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("test input");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        return this.parameters;
    }

    private void onConfirmedClicked(TextField textCoef, TextField textGamma,
                                    Stage primaryStage) {
        this.parameters.add(new Double(textCoef.getText()));
        this.parameters.add(new Double(textGamma.getText()));

        System.out.println(this);
        primaryStage.close();
    }

    @Override
    public String toString() {
        return "Coefficient: " + this.parameters.get(0) +
                "\nGamma: " + this.parameters.get(1);
    }
}