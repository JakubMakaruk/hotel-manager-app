package com.company.panels.AddNewCustomerPanel;

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
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField telephoneTextField;


    public void createCustomer(Event event) {
        if (firstNameTextField.getText().isBlank() ||
                lastNameTextField.getText().isBlank() ||
                emailTextField.getText().isBlank() ||
                addressTextField.getText().isBlank() ||
                cityTextField.getText().isBlank() ||
                postCodeTextField.getText().isBlank() ||
                telephoneTextField.getText().isBlank()) {
            createUserMessageLabel.setText("Enter all fields.");
            createUserMessageLabel.setTextFill(red);
        } else {
            if (isNumeric(telephoneTextField.getText())) {
                try {
                    Registry registry = LocateRegistry.getRegistry();
                    server = (Facade) registry.lookup("HotelService");
                } catch (RemoteException | NotBoundException e) {
                    System.out.println(e.getMessage());
                }

                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String email = emailTextField.getText();
                String address = addressTextField.getText();
                String city = cityTextField.getText();
                String postCode = postCodeTextField.getText();
                String telephone = telephoneTextField.getText();

                try {
                    boolean result = server.createCustomer(firstName, lastName, email, address, city, postCode, telephone);
                    if (result) {
                        createUserMessageLabel.setText("Successfully created new user!");
                        createUserMessageLabel.setTextFill(green);
                        Thread.sleep(1500);
                        switchToCustomersPanel(event);
                    } else {
                        createUserMessageLabel.setText("Cannot create new customer.");
                        createUserMessageLabel.setTextFill(red);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                createUserMessageLabel.setText("Invalid telephone number.");
                createUserMessageLabel.setTextFill(red);
            }
        }
    }

    public void switchToCustomersPanel(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../CustomersPanel/customers.fxml"));
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
