package com.veromeev.bsuir.dip.l1.hopfield;

import com.veromeev.bsuir.dip.l1.fxapp.DIP1;
import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HopfieldNetwork {

    private int formSize;

    private int[][] weights;
    private int[][] neurons;

    private List<ARGBImage> idealForms;

    public HopfieldNetwork(String[] idealFormNames) throws IOException {
        idealForms = new ArrayList<>();
        for (int i = 0; i < idealFormNames.length; i++) {
            System.out.println(
                    DIP1.hopfieldPath + idealFormNames[i] + ".png");
            val image = new ARGBImage(
                    DIP1.hopfieldPath + idealFormNames[i] + ".png");
            if (image.width() != image.heght()) {
                throw new IllegalArgumentException("Image is not square");
            }
            if (formSize != 0 && image.heght() != formSize) {
                throw new IllegalArgumentException("Images are not equal size");
            }
            idealForms.add(image);
            formSize = image.width();

        }
        weights = new int[formSize * formSize][formSize * formSize];
        neurons = new int[formSize][formSize];

        train();
    }

    private void train() {
        for (ARGBImage currentForm: idealForms)  {
            for (int i = 0; i < formSize * formSize; i++) {
                for (int j = 0; j < formSize * formSize; j++) {
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
        for (int i = 0; i < formSize; i++) {
            for (int j = 0; j < formSize; j++) {
                neurons[i][j] = inputForm.pixel(i,j).hopfieldValue();
            }
        }
        boolean somethingChanged= true;
        int loopCounter = 0;
        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 0; i < formSize; i++) {
                for (int j = 0; j < formSize; j++) {
                    int currentNeuronValue = neurons[i][j];
                    int newValue = calcNewValue(inputForm, i, j);
                    if (currentNeuronValue != newValue) {
                        somethingChanged = true;
                    }
                    neurons[i][j] = newValue;
                }
            }
            loopCounter++;
        }
        System.out.println(loopCounter + " loops");
        return printNeuronOutputs();
    }

    private int calcNewValue(ARGBImage inputForm, int i, int j) {
        int newNeuronValue = -1;
        for (int x = 0; x < formSize; x++) {
            for (int y = 0; y < formSize; y++) {
                newNeuronValue +=
                        weights[i * formSize + j][x * formSize + y]
                        * inputForm.pixel(x,y).hopfieldValue();
            }
        }
        return newNeuronValue > 0 ? 1 : -1;
    }

    private ARGBImage printNeuronOutputs() throws IOException {

        ARGBImage networkResult = new ARGBImage(formSize, formSize);

        for (int i = 0; i < formSize; i++) {
            for (int j = 0; j < formSize; j++) {
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
