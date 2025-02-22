package doemon.controller;

import doemon.Doemon;
import doemon.exception.DoemonException;
import doemon.response.Response;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    /** String that is printed when Doemon is exited. */
    private static final String EXIT_STRING = "I'm going to sleep now...See you again soon!";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    /** Instance of doemon being used */
    private Doemon doemon;

    /** Image of the user avatar */
    private Image userImage = new Image(getClass().getResourceAsStream("/images/nota.png"));
    /** Image of the doemon chatbot avatar */
    private Image doemonImage = new Image(getClass().getResourceAsStream("/images/doemon.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getDoemonDialog(Response.welcomeString(), doemonImage));
    }

    /**
     * Sets the doemon field.
     *
     * @param d Doemon instance.
     */
    public void setDoemon(Doemon d) {
        doemon = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the
     * other containing Doemon's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = doemon.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDoemonDialog(response, doemonImage)
            );
            if (response.equals(EXIT_STRING)) {
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                Stage stage = (Stage) dialogContainer.getScene().getWindow();
                delay.setOnFinished((event) -> stage.close());
                delay.play();
            }
        } catch (DoemonException e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDoemonDialog(e.toString(), doemonImage)
            );
        }
        userInput.clear();
    }
}
