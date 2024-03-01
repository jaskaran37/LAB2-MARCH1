package com.example.jkrnkaur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableView<OnlineOrder> tableView;
    @FXML
    private TableColumn<OnlineOrder, String> productColumn;
    @FXML
    private TableColumn<OnlineOrder, Integer> customerIdColumn;
    @FXML
    private TableColumn<OnlineOrder, String> customNameColumn;
    @FXML
    private TableColumn<OnlineOrder, Integer> ageColumn;
    @FXML
    private TableColumn<OnlineOrder, Double> billColumn;

    @FXML
    private TextField eproduct;
    @FXML
    private TextField ecustId;
    @FXML
    private TextField ecustName;
    @FXML
    private TextField eage;
    @FXML
    private TextField ebill;

    private ObservableList<OnlineOrder> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customNameColumn.setCellValueFactory(new PropertyValueFactory<>("customName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        billColumn.setCellValueFactory(new PropertyValueFactory<>("bill"));

        tableView.setItems(orders);
        populateTable();
    }

    private void populateTable() {
        orders.clear();
        String jdbcUrl = "jdbc:mysql://localhost:3306/market_db";
        String dbUser = "jkrn";
        String dbPassword = "j,@";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM onlineorder";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int customerId = resultSet.getInt("customerid");
                String customName = resultSet.getString("customname");
                int age = resultSet.getInt("age");
                double bill = resultSet.getDouble("bill");

                orders.add(new OnlineOrder(product, customerId, customName, age, bill));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void insertData() {
        String product = eproduct.getText();
        int customerId = Integer.parseInt(ecustId.getText());
        String customName = ecustName.getText();
        int age = Integer.parseInt(eage.getText());
        double bill = Double.parseDouble(ebill.getText());

        insertOrder(product, customerId, customName, age, bill);
        populateTable();
    }

    private void insertOrder(String product, int customerId, String customName, int age, double bill) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/market_db";
        String dbUser = "jkrn";
        String dbPassword = "j,@";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO onlineorder (product, customerid, customname, age, bill) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product);
            statement.setInt(2, customerId);
            statement.setString(3, customName);
            statement.setInt(4, age);
            statement.setDouble(5, bill);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteData() {
        int customerId = Integer.parseInt(ecustId.getText());
        deleteOrder(customerId);
        populateTable();
    }

    private void deleteOrder(int customerId) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/market_db";
        String dbUser = "jkrn";
        String dbPassword = "j,@";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM onlineorder WHERE customerid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateData() {
        String product = eproduct.getText();
        int customerId = Integer.parseInt(ecustId.getText());
        String customName = ecustName.getText();
        int age = Integer.parseInt(eage.getText());
        double bill = Double.parseDouble(ebill.getText());

        updateOrder(product, customerId, customName, age, bill);
        populateTable();
    }

    private void updateOrder(String product, int customerId, String customName, int age, double bill) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/market_db";
        String dbUser = "jkrn";
        String dbPassword = "j,@";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE onlineorder SET product = ?, customname = ?, age = ?, bill = ? WHERE customerid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product);
            statement.setString(2, customName);
            statement.setInt(3, age);
            statement.setDouble(4, bill);
            statement.setInt(5, customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
