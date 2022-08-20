package com.company.panels.AddNewReservationPanel;

import com.company.models.Customer;
import com.company.models.Room;
import com.company.models.User;
import com.company.services.Facade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    Facade server;

    List<Customer> customers;
    List<Room> rooms;

    @FXML
    private Label createReservationMessageLabel;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox roomComboBox;
    @FXML
    private DatePicker startDateDatePicker;
    @FXML
    private DatePicker endDateDatePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            server = (Facade) registry.lookup("HotelService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            customers = server.getCustomers();
            rooms = server.getAllRooms();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        customerComboBox.getItems().addAll(customers);
        roomComboBox.getItems().addAll(rooms);
    }


    public void createReservation(Event event) {
        if (customerComboBox.getSelectionModel().isEmpty() ||
                roomComboBox.getSelectionModel().isEmpty() ||
                startDateDatePicker.getValue() == null ||
                endDateDatePicker.getValue() == null) {
            createReservationMessageLabel.setText("Enter all fields.");
            createReservationMessageLabel.setTextFill(red);
        } else {

            try {
                Registry registry = LocateRegistry.getRegistry();
                server = (Facade) registry.lookup("HotelService");
            } catch (RemoteException | NotBoundException e) {
                System.out.println(e.getMessage());
            }

            Customer customer = (Customer) customerComboBox.getSelectionModel().getSelectedItem();
            Room room = (Room) roomComboBox.getSelectionModel().getSelectedItem();
            LocalDate startDate = startDateDatePicker.getValue();
            LocalDate endDate = endDateDatePicker.getValue();

            try {
                boolean result = server.createReservation(customer, room, startDate, endDate);
                if (result) {
                    createReservationMessageLabel.setText("Succesfully created new reservation!");
                    createReservationMessageLabel.setTextFill(green);
                    Thread.sleep(1500);
                    switchToReservationPanel(event);
                } else {
                    createReservationMessageLabel.setText("Cannot create new reservation!");
                    createReservationMessageLabel.setTextFill(red);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void switchToReservationPanel(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ReservationsPanel/reservations.fxml"));
        Scene registerScene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
