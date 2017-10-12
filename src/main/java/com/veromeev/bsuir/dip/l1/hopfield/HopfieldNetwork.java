package com.veromeev.bsuir.dip.l1.hopfield;

import com.veromeev.bsuir.dip.l1.fxapp.DIP1;
import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HopfieldNetwork {

    private static final int FORM_SIZE = 10;
    private static final int NUMBER_OF_FORMS = 3;

    private int[][] weights;
    private int[][] neurons;

    private List<ARGBImage> idealForms;

    public HopfieldNetwork() throws IOException {
        weights = new int[FORM_SIZE * FORM_SIZE][FORM_SIZE * FORM_SIZE];
        neurons = new int[FORM_SIZE][FORM_SIZE];

        idealForms = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_FORMS; i++) {
            idealForms.add(new ARGBImage(
                    DIP1.hopfieldPath + i + ".png"));
        }
    }

    public void train() {

        for (ARGBImage currentForm: idealForms)  {

            for (int i = 0; i < FORM_SIZE * FORM_SIZE; i++) {
                for (int j = 0; j < FORM_SIZE * FORM_SIZE; j++) {
                    if (i == j) {
                        weights[i][j] = 0;
                    }
                    else {
                        weights[i][j] +=
                                currentForm.getPixelByOffset(i).hopfieldValue()
                                        * currentForm.getPixelByOffset(j).hopfieldValue();
                    }
                }
            }

        }

    }

    public ARGBImage recognizeForm(ARGBImage inputForm) throws IOException {
        for (int i = 0; i < FORM_SIZE; i++) {
            for (int j = 0; j < FORM_SIZE; j++) {
                neurons[i][j] = inputForm.pixel(i,j).hopfieldValue();
            }
        }
        boolean somethingChanged= true;

        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 0; i < FORM_SIZE; i++) {
                for (int j = 0; j < FORM_SIZE; j++) {
                    int currentNeuronValue = neurons[i][j];
                    int newValue = calcNewValue(inputForm, i, j);
                    if (currentNeuronValue != newValue) {
                        somethingChanged = true;
                    }
                    neurons[i][j] = newValue;
                }
            }
        }

        return printNeuronOutputs();
    }

    private int calcNewValue(ARGBImage inputForm, int i, int j) {
        int newNeuronValue = -1;
        for (int x = 0; x < FORM_SIZE; x++) {
            for (int y = 0; y < FORM_SIZE; y++) {
                newNeuronValue +=
                        weights[i * FORM_SIZE + j][x * FORM_SIZE + y]
                        * inputForm.pixel(x,y).hopfieldValue();
            }
        }
        return newNeuronValue > 0 ? 1 : -1;
    }

    private ARGBImage printNeuronOutputs() throws IOException {

        ARGBImage networkResult = new ARGBImage(FORM_SIZE, FORM_SIZE);

        for (int i = 0; i < FORM_SIZE; i++) {
            for (int j = 0; j < FORM_SIZE; j++) {
                ARGBPixel p = new ARGBPixel();

                p.setRGB(0, 0, 0);
                networkResult.setPixel(neurons[i][j] == 1
                        ? new ARGBPixel(0, 0, 0)
                        : new ARGBPixel(255, 255,255),
                        i, j);
            }
        }
        return networkResult;
    }

}
