package com.company.panels.ManagementPanel;

import com.company.UserSingleton;
import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
import com.company.models.User;
import com.company.services.Facade;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String loginSceneName = "../LoginPanel/login.fxml";
    String homeSceneName = "../HomePanel/home.fxml";
    String customersSceneName = "../CustomersPanel/customers.fxml";
    String reservationsSceneName = "../ReservationsPanel/reservations.fxml";
    String managementSceneName = "../ManagementPanel/management.fxml";
    String addNewRoomSceneName = "../AddNewRoomPanel/addnewroom.fxml";

    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    @FXML
    private Label customersStatistics;
    @FXML
    private Label roomsStatistics;
    @FXML
    private Label reservationsStatistics;
    @FXML
    private TextField inputPreviousPassword;
    @FXML
    private TextField inputNewPassword;
    @FXML
    private TextField inputRepeatNewPassword;

    @FXML
    private Label previousPasswordIncorrectLabel;
    @FXML
    private Label passwordsNotMatchLabel;
    @FXML
    private Label passwordsChangedSuccessfullyLabel;


    Facade server;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSingleton userSingleton = UserSingleton.getInstance();
        try {
            Registry registry = LocateRegistry.getRegistry();
            server = (Facade) registry.lookup("HotelService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            customersStatistics.setText(String.valueOf(server.getCustomers().size()));
            roomsStatistics.setText(String.valueOf(server.getAllRooms().size()));
            reservationsStatistics.setText(String.valueOf(server.getReservations().size()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (RemoteException e) {
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

    public void switchToLoginPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, loginSceneName);
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

    public void switchToNewRoomPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, addNewRoomSceneName);
    }

    public void changePassword(MouseEvent mouseEvent) throws SQLException, RemoteException {
        if (UserSingleton.getInstance().getUser().getPassword().equals(inputPreviousPassword.getText())) {
            if (inputNewPassword.getText().equals(inputRepeatNewPassword.getText())) {
                UserSingleton.getInstance().getUser().setPassword(inputNewPassword.getText());
                server.changePassword(UserSingleton.getInstance().getUser().getUsername(), inputNewPassword.getText());
                passwordsChangedSuccessfullyLabel.setVisible(true);
                previousPasswordIncorrectLabel.setVisible(false);
                passwordsNotMatchLabel.setVisible(false);
            } else {
                passwordsChangedSuccessfullyLabel.setVisible(false);
                previousPasswordIncorrectLabel.setVisible(false);
                passwordsNotMatchLabel.setVisible(true);
            }
        } else {
            passwordsChangedSuccessfullyLabel.setVisible(false);
            previousPasswordIncorrectLabel.setVisible(true);
            passwordsNotMatchLabel.setVisible(false);
        }
    }
}
