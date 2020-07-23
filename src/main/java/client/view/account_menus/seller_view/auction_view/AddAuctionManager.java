package client.view.account_menus.seller_view.auction_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddAuctionManager implements Initializable {
    public VBox vBoxItems;
    public ChoiceBox productChoiceBox;
    public Label dateError;
    public Label productError;
    public TextField deadlineField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        ArrayList<Product> products = RequestHandler.get("/accounts/seller_account_controller/products/", queries,
                true, new TypeToken<ArrayList<Product>>(){}.getType());
        for(Product product : products) {
            setProductsItem(product);
        }
    }

    private void setProductsItem(Product product) {
        try {
            AnchorPane item = FXMLLoader.load(getClass().
                    getResource("/layouts/seller_menus/sellers_auctions_menus/product_item.fxml"));
            HBox hBox = (HBox) item.getChildren().get(0);
            ((Label) hBox.getChildren().get(0)).setText(product.getProductId());
            ((Label) hBox.getChildren().get(1)).setText(product.getName());
            ((Label) hBox.getChildren().get(2)).setText(String.valueOf(product.getPrice()));
            vBoxItems.getChildren().add(hBox);
            productChoiceBox.getItems().add(product.getProductId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAuction() {
        try {
            verifyInput();

        } catch (Exception ignored) {}
    }

    private void verifyInput() throws Exception {
        if(deadlineField.getText().isBlank()) {
            dateError.setText("Fill this field!");
            throw new Exception();
        } else {
            dateError.setText("");
        }
        if(productChoiceBox.getSelectionModel().getSelectedItem() == null) {
            productError.setText("Choose a product!");
            throw new Exception();
        } else {
            productError.setText("");
        }
    }
}
