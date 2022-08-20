package com.company.panels.AddNewRoomPanel;

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
import java.util.regex.Pattern;

public class Controller {
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    Facade server;

    @FXML
    private Label createUserMessageLabel;
    @FXML
    private TextField roomNameTextField;
    @FXML
    private TextField numberOfBedsTextField;
    @FXML
    private TextField roomNumberTextField;


    public void createRoom(Event event) {
        if (roomNameTextField.getText().isBlank() ||
                numberOfBedsTextField.getText().isBlank() ||
                roomNumberTextField.getText().isBlank()) {
            createUserMessageLabel.setText("Enter all fields.");
            createUserMessageLabel.setTextFill(red);
        } else {
            if (isNumeric(roomNumberTextField.getText())) {
                try {
                    Registry registry = LocateRegistry.getRegistry();
                    server = (Facade) registry.lookup("HotelService");
                } catch (RemoteException | NotBoundException e) {
                    System.out.println(e.getMessage());
                }

                String roomName = roomNameTextField.getText();
                String numberOfBeds = numberOfBedsTextField.getText();
                String roomNumber = roomNumberTextField.getText();

                try {
                    boolean result = server.createRoom(roomNumber, roomName, numberOfBeds);
                    if (result) {
                        createUserMessageLabel.setText("Successfully created new room!");
                        createUserMessageLabel.setTextFill(green);
                        Thread.sleep(1500);
                        switchToManagementPanel(event);
                    } else {
                        createUserMessageLabel.setText("Cannot create new room.");
                        createUserMessageLabel.setTextFill(red);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                createUserMessageLabel.setText("Invalid input values.");
                createUserMessageLabel.setTextFill(red);
            }
        }
    }

    public void switchToManagementPanel(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ManagementPanel/management.fxml"));
        Scene registerScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.resizableProperty().setValue(false);
        window.getIcons().add(new Image("D:/DANE/Projekty/hotel-app-java/hotel-app/src/com/company/favicon.png"));
        window.setScene(registerScene);
        window.show();
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
