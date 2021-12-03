// package com.hasanzade;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sequential {
    JFrame frame;
    BufferedImage image;
    int squareSize;

    public Sequential(JFrame frame, BufferedImage image, int squareSize) {
        this.frame = frame;
        this.image = image;
        this.squareSize = squareSize;
    }

    public void process(int widthStart, int widthEnd, int heightStart, int heightEnd) {
        for (int i = heightStart; i < heightEnd; i += squareSize) { // from top to bottom
            for (int j = widthStart; j < widthEnd; j += squareSize) { // from left to right
                int pixelCount = 0;
                int totalRed = 0;
                int totalGreen = 0;
                int totalBlue = 0;
                for (int k = 0; k < squareSize; k++) {
                    for (int l = 0; l < squareSize; l++) {
                        // considering going beyond right and bottom borders
                        if (i + k < heightEnd && j + l < widthEnd) { // stay within borders
                            pixelCount++;
                            int pixel = image.getRGB(j + l, i + k);
                            Color color = new Color(pixel);

                            // average color 1st way
                            // totalRed += color.getRed();
                            // totalGreen += color.getGreen();
                            // totalBlue += color.getBlue();

                            // average color 2nd way (better)
                            totalRed += color.getRed() * color.getRed();
                            totalGreen += color.getGreen() * color.getGreen();
                            totalBlue += color.getBlue() * color.getBlue();
                        }
                    }
                }

                // calculate average color

                // average color 1st way
                // Color color = new Color(totalRed/pixelCount, totalGreen/pixelCount, totalBlue/pixelCount);

                // average color 2nd way (better)
                int averageRed = (int) Math.sqrt(totalRed/pixelCount);
                int averageGreen = (int) Math.sqrt(totalGreen/pixelCount);
                int averageBlue = (int) Math.sqrt(totalBlue/pixelCount);
                Color color = new Color(averageRed, averageGreen, averageBlue);

                for (int k = 0; k < squareSize; k++) {
                    for (int l = 0; l < squareSize; l++) {
                        if (i + k < heightEnd && j + l < widthEnd) {
                            image.setRGB(j + l, i + k, color.getRGB());
                        }
                    }
                }

                // this is added to show smooth processing effect
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // show square-by-square progress of the processing
                frame.validate();
                frame.repaint();
            }
        }
    }
}
