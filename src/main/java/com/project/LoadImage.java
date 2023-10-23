package com.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import javafx.application.Platform;

public class LoadImage {

    private Random rand;

    public void load(Consumer<BufferedImage> callBack){
        CompletableFuture.supplyAsync(() -> {
            try {

                rand = new Random();
                Thread.sleep((rand.nextInt(45)+5)*1000);

                File file = new File("src/main/resources/assets/images/si.jpg");

                BufferedImage img = ImageIO.read(file);


                return img;
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                return null;
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        })
        .thenAcceptAsync(content -> {
            callBack.accept(content);
        }, Platform::runLater);

    }
}
