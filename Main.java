// package com.hasanzade;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    static int squareSize, width, height;
    static String filename, mode;
    static JFrame frame;
    static BufferedImage image;

    public static void main(String[] args) {
        filename = args[0];
        squareSize = Integer.parseInt(args[1]);
        mode = args[2];
        drawImage();
    }

    private static void drawImage() {
        try {
            image = ImageIO.read(new File(filename));
            JLabel label = new JLabel(new ImageIcon(image));

            frame = new JFrame();
            frame.add(label);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            width = image.getWidth();
            height = image.getHeight();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;
            if (width > screenWidth || height > screenHeight) {
                frame.setSize(screenSize.width, screenSize.height);
            }

            if (mode.equals("S")) { // single threaded mode
                frame.setTitle("Single Threaded Processing");
                singleThreadedProcess();
            }
            else if (mode.equals("M")) { // multi threaded mode
                frame.setTitle("Multi Threaded Processing");
                multiThreadedProcess();
            }
            else {
                System.out.println("Incorrect mode option entered!");
            }

            ImageIO.write(image, "jpg", new File("result.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void singleThreadedProcess() throws IOException {
        Sequential sequential = new Sequential(frame, image, squareSize);
        sequential.process(0, width, 0, height);
    }

    private static void multiThreadedProcess() throws IOException {
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int partial = squareSize * ((height / squareSize) / numberOfCores);
        // divide task proportionally according to the number of cores
        Parallel[] threads = new Parallel[numberOfCores];
        for (int i = 0; i < numberOfCores; i++) {
            // divided horizontally to be processed in parallel
            int heightStart = i * partial;
            // if it is the last part to be processed heightEnd is equal to the height of image
            // otherwise it is equal to the next partial height
            int heightEnd = (i + 1 == numberOfCores) ? height : (i + 1) * partial;

            // create thread for each core
            Parallel parallel = new Parallel(frame, image, squareSize);
            // set coordinates for each parallel process
            parallel.setWidthStart(0);
            parallel.setWidthEnd(width);
            parallel.setHeightStart(heightStart);
            parallel.setHeightEnd(heightEnd);

            parallel.start();
            threads[i] = parallel;
        }

        try {
            for (int i = 0; i < numberOfCores; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}