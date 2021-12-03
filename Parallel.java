// package com.hasanzade;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Parallel extends Thread{

    JFrame frame;
    BufferedImage image;
    int squareSize;
    int widthStart, widthEnd, heightStart, heightEnd;

    public Parallel(JFrame frame, BufferedImage image, int squareSize) {
        this.frame = frame;
        this.image = image;
        this.squareSize = squareSize;
    }

    public void setWidthStart(int widthStart) {
        this.widthStart = widthStart;
    }

    public void setWidthEnd(int widthEnd) {
        this.widthEnd = widthEnd;
    }

    public void setHeightStart(int heightStart) {
        this.heightStart = heightStart;
    }

    public void setHeightEnd(int heightEnd) {
        this.heightEnd = heightEnd;
    }

    public void run() {
        Sequential sequential = new Sequential(frame, image, squareSize);
        sequential.process(widthStart, widthEnd, heightStart, heightEnd);
    }
}
