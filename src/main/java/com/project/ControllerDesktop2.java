package com.project;


import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;


public class ControllerDesktop2 implements Initializable{

    @FXML
    public Label vista0Label;

    @FXML
    private Button carregar, aturar;

    @FXML
    private ProgressBar bar1;

    @FXML
    private GridPane grid;

    LoadImage imageLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bar1.setStyle("-fx-accent: #f6eddb; -fx-control-inner-background: #ec8d75; -fx-text-box-border: #f6eddb;");

        carregar.setStyle("-fx-background-color: #bd4b64;");
        aturar.setStyle("-fx-background-color: #bd4b64;");

        imageLoader = new LoadImage();

        for(int i = 0; i<3; ++i){
            for(int j = 0; j<8; ++j){
                final int rowIndex = j;
                final int colIndex = i;
                imageLoader.load((BufferedImage img) -> {
                    int Width = 75; 
                    int Height = 75;
                    
                    WritableImage fxImage = new WritableImage(Width, Height);
                    PixelWriter writer = fxImage.getPixelWriter();
                    for (int x = 0; x < Width; x++) {
                        for (int y = 0; y < Height; y++) {
                            int srcX = x * img.getWidth() / Width;
                            int srcY = y * img.getHeight() / Height;
                            int argb = img.getRGB(srcX, srcY);
                            writer.setArgb(x, y, argb);
                        }
                    }
                
                    ImageView imageView = new ImageView(fxImage);
                    imageView.setFitWidth(Width);
                    imageView.setFitHeight(Height);
                    grid.add(new ImageView(fxImage), rowIndex, colIndex);
                });
            }
        }

        vista0Label.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                UtilsViews.setViewAnimating("Desktop");
            }
        });
    }
    
}
