package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class WalletManager extends MenuManager implements Initializable {
    public Label walletCredit;
    public Button toBankButton;
    public Button toWalletButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        String response = RequestHandler.get("/accounts/seller_customer_common/wallet/", queries,
                true, String.class);
        if(response.startsWith("customer"))
            ((Pane) toBankButton.getParent()).getChildren().remove(toBankButton);
        walletCredit.setText(response.split("\\s")[1]);
    }

    public void moveCreditToBank() {
        ManageCreditMenuManager.setIsToWallet(false);
        setSecondaryInnerPane("/layouts/customer_seller_common_menus/wallet_menus/manage_credit_menu.fxml");
    }

    public void moveCreditToWallet() {
        ManageCreditMenuManager.setIsToWallet(true);
        setSecondaryInnerPane("/layouts/customer_seller_common_menus/wallet_menus/manage_credit_menu.fxml");
    }


}
