package com.company.panels.ReservationsPanel;

import com.company.UserSingleton;
import com.company.models.Customer;
import com.company.models.Reservation;
import com.company.models.Room;
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
    String addNewReservationSceneName = "../AddNewReservationPanel/addnewreservation.fxml";

    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    @FXML
    private TableView<Reservation> tableView;
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    @FXML
    private TableColumn<Reservation, Customer> customerColumn;
    @FXML
    private TableColumn<Reservation, Room> roomColumn;
    @FXML
    private TableColumn<Reservation, LocalTime> startDateColumn;
    @FXML
    private TableColumn<Reservation, LocalTime> endDateColumn;

    @FXML
    private TextField inputSearch;


    List<Reservation> reservations;
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

        idColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        customerColumn.setCellValueFactory(Customer -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Customer.getValue().getCustomer().getFirstName() + " " + Customer.getValue().getCustomer().getSurname() + " | Phone: " + Customer.getValue().getCustomer().getTelephone());
            return property;
        });
        roomColumn.setCellValueFactory(Room -> {
            SimpleObjectProperty property2 = new SimpleObjectProperty();
            property2.setValue("Room: " + Room.getValue().getRoom().getName() +
                                " | Number: " + Room.getValue().getRoom().getRoomNumber());
            return property2;
        });
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, LocalTime>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, LocalTime>("endTime"));

        try {
            reservations = server.getReservations();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tableView.getItems().addAll(reservations);
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

    public void switchToAddNewReservationPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, addNewReservationSceneName);
    }

    public void searchReservationByCustomer(MouseEvent mouseEvent) throws SQLException, RemoteException {
        if (inputSearch.getText().equals("")) {
            reservations = server.getReservations();
        } else {
            String fullname[] = inputSearch.getText().split(" ");
            List<Reservation> searchResult = new ArrayList<>();

            if (fullname.length == 2) {
                String _firstname = fullname[0], _secondname = fullname[1];
                searchResult = server.getReservationByName(_firstname, _secondname);
            } else if (fullname.length == 1) {
                String _firstname = fullname[0];
                searchResult = server.getReservationByFirstName(_firstname);
            }
            reservations = searchResult;
        }
        tableView.getItems().clear();
        tableView.getItems().addAll(reservations);
    }

    public boolean removeSelectedReservation(MouseEvent mouseEvent) throws SQLException, RemoteException {
        List<Reservation> reservations = tableView.getSelectionModel().getSelectedItems();
        for (Reservation reservation : reservations) {
            server.removeReservationById(reservation.getId());
        }

        this.searchReservationByCustomer(mouseEvent);
        return true;
    }
}
