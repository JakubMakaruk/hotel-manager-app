package com.company.panels.HomePanel;

import com.company.UserSingleton;
import com.company.models.User;
import com.company.services.Facade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String homeSceneName = "../HomePanel/home.fxml";
    String customersSceneName = "../CustomersPanel/customers.fxml";
    String reservationsSceneName = "../ReservationsPanel/reservations.fxml";
    String loginSceneName = "../LoginPanel/login.fxml";
    String managementSceneName = "../ManagementPanel/management.fxml";

    @FXML
    private Label helloTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSingleton userSingleton = UserSingleton.getInstance();
        helloTitle.setText("Hi " + userSingleton.getUser().getFirstname() + ",\nhave a good day!");
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


    public void switchToHomePanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, homeSceneName);
    }

    public void switchToClientsPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, customersSceneName);
    }

    public void switchToReservationsPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, reservationsSceneName);
    }

    public void switchToManagementPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, managementSceneName);
    }
    public void switchToLoginPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, loginSceneName);
        UserSingleton.getInstance().setUser(null);
    }
}
