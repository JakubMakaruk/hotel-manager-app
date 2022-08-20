package com.company.panels.CustomersPanel;

import com.company.UserSingleton;
import com.company.models.Customer;
import com.company.services.Facade;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String loginSceneName = "../LoginPanel/login.fxml";
    String homeSceneName = "../HomePanel/home.fxml";
    String customersSceneName = "../CustomersPanel/customers.fxml";
    String reservationsSceneName = "../ReservationsPanel/reservations.fxml";
    String managementSceneName = "../ManagementPanel/management.fxml";
    String addNewCustomerSceneName = "../AddNewCustomerPanel/addnewcustomer.fxml";

    Color green = Color.valueOf("#81DC4D");
    Color red = Color.valueOf("#FC3F3F");

    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> secondNameColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> cityColumn;
    @FXML
    private TableColumn<Customer, String> postCodeColumn;
    @FXML
    private TableColumn<Customer, String> telephoneColumn;

    @FXML
    private TextField inputSearch;


    List<Customer> customers;
    Facade server;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            server = (Facade) registry.lookup("HotelService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
        postCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postCode"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephone"));

        try {
            customers = server.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tableView.getItems().addAll(customers);
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

    public void searchCustomerByName(MouseEvent mouseEvent) throws SQLException, RemoteException {
        if (inputSearch.getText().equals("")) {
            customers = server.getCustomers();
            tableView.getItems().clear();
            tableView.getItems().addAll(customers);
        } else {
            String fullname[] = inputSearch.getText().split(" ");
            List<Customer> searchResult = new ArrayList<>();

            if (fullname.length == 2) {
                String _firstname = fullname[0], _secondname = fullname[1];
                searchResult = server.getCustomersByName(_firstname, _secondname);
            } else if (fullname.length == 1) {
                String _firstname = fullname[0];
                searchResult = server.getCustomersByFirstName(_firstname);
            }
            customers = searchResult;
            tableView.getItems().clear();
            tableView.getItems().addAll(searchResult);
        }
    }

    public void switchToAddNewCustomerPanel(MouseEvent mouseEvent) throws IOException {
        this.switchScene(mouseEvent, addNewCustomerSceneName);
    }

    public boolean removeSelectedUser(MouseEvent mouseEvent) throws SQLException, RemoteException {
        List<Customer> customers = tableView.getSelectionModel().getSelectedItems();
        for (Customer customer : customers) {
            server.removeCustomerById(customer.getId());
        }
        this.searchCustomerByName(mouseEvent);
        return true;
    }
}
