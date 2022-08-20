package com.company.panels.LoginPanel;

import com.company.UserSingleton;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Controller {
    String registerSceneName = "../RegisterPanel/register.fxml";
    String homeSceneName = "../HomePanel/home.fxml";

    Color red = Color.valueOf("#FC3F3F");
    Facade server;

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public void signInButtonOnAction(MouseEvent mouseEvent) {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            validateLogin(mouseEvent);
        } else {
            loginMessageLabel.setText("Enter username and password.");
            loginMessageLabel.setTextFill(red);
        }
    }

    public void validateLogin(Event event) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            server = (Facade) registry.lookup("HotelService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        try {
            User user = server.signIn(username, password);

            if (user != null) {
                UserSingleton userSingleton = UserSingleton.getInstance();
                userSingleton.setUser(user);
                this.switchScene(event, homeSceneName);
            } else {
                loginMessageLabel.setText("Wrong username or password");
                loginMessageLabel.setTextFill(red);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchScene(Event event, String sceneName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(sceneName));
        Scene registerScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.resizableProperty().setValue(false);
        window.getIcons().add(new Image("D:/DANE/Projekty/hotel-app-java/hotel-app/src/com/company/favicon.png"));
        window.setScene(registerScene);
        window.show();
    }

    public void switchToRegisterPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, registerSceneName);
    }
}
