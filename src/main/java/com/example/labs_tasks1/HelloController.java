package com.example.labs_tasks1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnFirst;

    @FXML
    private Button btnLast;

    @FXML
    private Button btnAuto;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnChooseDir;

    @FXML
    private ProgressBar progressBar;

    private List<File> imageFiles;
    private int currentIndex = 0;
    private Timeline timeline;

    // Геттеры для доступа к приватным полям
    public Label getWelcomeText() {
        return welcomeText;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Button getBtnNext() {
        return btnNext;
    }

    public Button getBtnPrev() {
        return btnPrev;
    }

    public Button getBtnFirst() {
        return btnFirst;
    }

    public Button getBtnLast() {
        return btnLast;
    }

    public Button getBtnAuto() {
        return btnAuto;
    }

    public Button getBtnPause() {
        return btnPause;
    }

    public Button getBtnChooseDir() {
        return btnChooseDir;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public List<File> getImageFiles() {
        return imageFiles;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    // Сеттеры для изменения состояния
    public void setImageFiles(List<File> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNextButtonClick() {
        currentIndex = (currentIndex + 1) % imageFiles.size();
        showImage(imageFiles.get(currentIndex));
        updateProgressBar();
    }

    @FXML
    protected void onPrevButtonClick() {
        currentIndex = (currentIndex - 1 + imageFiles.size()) % imageFiles.size();
        showImage(imageFiles.get(currentIndex));
        updateProgressBar();
    }

    @FXML
    protected void onFirstButtonClick() {
        if (!imageFiles.isEmpty()) {
            currentIndex = 0;
            showImage(imageFiles.get(currentIndex));
            updateProgressBar();
        }
    }

    @FXML
    protected void onLastButtonClick() {
        if (!imageFiles.isEmpty()) {
            currentIndex = imageFiles.size() - 1;
            showImage(imageFiles.get(currentIndex));
            updateProgressBar();
        }
    }

    @FXML
    protected void onAutoButtonClick() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> onNextButtonClick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void onPauseButtonClick() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    protected void onChooseDirButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            List<File> imageFiles = loadImagesFromDirectory(selectedDirectory);
            ImageSliderBuilder builder = new ImageSliderBuilder(this);
            builder.setImageFiles(imageFiles);
            builder.setCurrentIndex(0);
            builder.build();
        }
    }

    private List<File> loadImagesFromDirectory(File directory) {
        List<File> images = new ArrayList<>();
        try {
            Files.walk(Paths.get(directory.getPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().matches(".*\\.(png|jpg|jpeg|gif|bmp|tiff)"))
                    .forEach(images::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public void showImage(File imageFile) {
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        welcomeText.setText("Viewing: " + imageFile.getName());
    }

    public void updateProgressBar() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            double progress = (double) (currentIndex + 1) / imageFiles.size();
            progressBar.setProgress(progress);
        } else {
            progressBar.setProgress(0);
        }
    }
}