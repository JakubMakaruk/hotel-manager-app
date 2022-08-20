package com.company.panels.RegisterPanel;

import com.company.models.User;
import com.company.services.Facade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller {
    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    Facade server;

    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField repeatPasswordPasswordField;




    private boolean validatePasswords() {
        if (!passwordPasswordField.getText().equals(repeatPasswordPasswordField.getText())) {
            registerMessageLabel.setText("Passwords does not match");
            registerMessageLabel.setTextFill(red);
            return false;
        }
        return true;
    }

    public void createUser(Event event) {
        if (firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank() || usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank() || repeatPasswordPasswordField.getText().isBlank()) {
            registerMessageLabel.setText("Enter all fields.");
            registerMessageLabel.setTextFill(red);
        } else {
            if (validatePasswords()) {
                try {
                    Registry registry = LocateRegistry.getRegistry();
                    server = (Facade) registry.lookup("HotelService");
                } catch (RemoteException | NotBoundException e) {
                    System.out.println(e.getMessage());
                }

                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String username = usernameTextField.getText();
                String password = passwordPasswordField.getText();

                try {
                    if (server.checkIfUserExists(username)) {
                        registerMessageLabel.setText("This user already exists!");
                        registerMessageLabel.setTextFill(red);
                    } else {
                        if (server.signUp(firstName, lastName, username, password)) {
                            registerMessageLabel.setText("Successfully created new user!");
                            registerMessageLabel.setTextFill(green);
                            Thread.sleep(1500);
                            switchToLoginPanel(event);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    public void switchToLoginPanel(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../LoginPanel/login.fxml"));
        Scene registerScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.resizableProperty().setValue(false);
        window.getIcons().add(new Image("D:/DANE/Projekty/hotel-app-java/hotel-app/src/com/company/favicon.png"));
        window.setScene(registerScene);
        window.show();
    }
}
