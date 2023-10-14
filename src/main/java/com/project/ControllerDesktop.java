package com.project;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ControllerDesktop implements Initializable{
    
    private ExecutorService executor = Executors.newFixedThreadPool(4); 

    private Double pb1 =0.0, pb2=0.0, pb3=0.0, valAnt2 = 0.0, valAnt3=0.0;

    private Random rand;

    private boolean pause1 = true, pause2 = true, pause3 = true;

    private final Object pb1Lock = new Object(), pb2Lock = new Object(), pb3Lock = new Object();

    @FXML
    private Button button1, button2, button3;

    @FXML
    private ProgressBar bar1, bar2, bar3;

    @FXML
    public Label task1, task2, task3;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bar1.setStyle("-fx-accent: #f6eddb; -fx-control-inner-background: #ec8d75; -fx-text-box-border: #f6eddb;");
        bar2.setStyle("-fx-accent: #f6eddb; -fx-control-inner-background: #ec8d75; -fx-text-box-border: #f6eddb;");
        bar3.setStyle("-fx-accent: #f6eddb; -fx-control-inner-background: #ec8d75; -fx-text-box-border: #f6eddb;");

        button1.setStyle("-fx-background-color: #bd4b64;");
        button2.setStyle("-fx-background-color: #bd4b64;");
        button3.setStyle("-fx-background-color: #bd4b64;");
        rand = new Random();

        // ProgressBar 1
        executor.execute(()->{
            double r1 = calcPb1();
            while(true){  
                synchronized(pb1Lock){
                    while(pause1){
                        try {
                            pb1Lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(pb1 + r1 >= 1.0)pb1 = 1.0;
                    else pb1 += r1; 
                    Platform.runLater(() -> {
                        updateText(task1, "Tasca 1: ", Math.round(pb1*100));
                        bar1.setProgress(pb1);
                    });
                    if(pb1 < 1)r1 = calcPb1();
                    if(pb1 >= 1){
                        pause1 = true;
                        Platform.runLater(() -> {
                            button1.setText("Inicar");
                        });
                    }
                }     
            }
        });

        // ProgressBar 2
        executor.execute(()->{
            double r2 = calcPb2();
            while(true){
                synchronized(pb2Lock){
                    while(pause2){
                        try {
                            pb2Lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(pb2 + r2 >= 1.0)pb2 = 1.0;
                    else pb2 += r2; 
                    Platform.runLater(() -> {
                        updateText(task2, "Tasca 2: ", Math.round(pb2*100));
                        bar2.setProgress(pb2);
                    });
                    if(pb2 < 1) r2 = calcPb2();
                    if(pb2 >= 1){
                        pause2 = true;
                        Platform.runLater(() -> {
                            button2.setText("Inicar");
                        });
                    }
                }
            }
        });

        // ProgressBar 3
        executor.execute(()->{
            double r3 = calcPb3();
            while(true){
                synchronized(pb3Lock){
                    while(pause3){
                        try {
                            pb3Lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(pb3 + r3 >= 1.0)pb3 = 1.0;
                    else pb3 += r3; 
                    Platform.runLater(() -> {
                        updateText(task3, "Tasca 3: ", Math.round(pb3*100));
                        bar3.setProgress(pb3);
                    });
                    if(pb3 < 1) r3 = calcPb3();
                    if(pb3 >= 1){
                        pause3 = true;
                        Platform.runLater(() -> {
                            button3.setText("Inicar");
                        });
                    }
                }
            }

        });

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pause1){
                    pause1 = false;
                    if(pb1 >= 1){
                        pb1 = 0.0;
                        bar1.setProgress(0);
                        task1.setText("Tasca 1: 0%");
                    }
                    button1.setText("Aturar");
                    synchronized(pb1Lock){
                        pb1Lock.notify();
                    }
                }
                else{
                    pause1 = true;
                    button1.setText("Iniciar");
                }
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pause2){
                    pause2 = false;
                    if(pb2 >= 1){
                        pb2 = 0.0;
                        bar2.setProgress(0);
                        task2.setText("Tasca 1: 0%");
                    }
                    button2.setText("Aturar");
                    synchronized(pb2Lock){
                        pb2Lock.notify();
                    }
                }
                else{
                    pause2 = true;
                    button2.setText("Iniciar");
                }
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pause3){
                    pause3 = false;
                    if(pb3 >= 1){
                        pb3 = 0.0;
                        bar3.setProgress(0);
                        task3.setText("Tasca 1: 0%");
                    }
                    button3.setText("Aturar");
                    synchronized(pb3Lock){
                        pb3Lock.notify();
                    }
                }
                else{
                    pause3 = true;
                    button3.setText("Iniciar");
                }
            }
        });
    }

    public void updateText(Label l, String text, Long value){
        l.setText(text + value + "%");
    }

    public double calcPb1(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1.0/100.0;
    }

    public double calcPb2(){
        int time = rand.nextInt(3) +3;
        double value = rand.nextDouble(3) +2;
        double r = value + valAnt2;
        valAnt2 = value;
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return r/100;
    }

    public double calcPb3(){
        int time = rand.nextInt(6) +3;
        double value = rand.nextDouble(3) +4;
        double r = value + valAnt2;
        valAnt2 = value;
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return r/100;
    }
}