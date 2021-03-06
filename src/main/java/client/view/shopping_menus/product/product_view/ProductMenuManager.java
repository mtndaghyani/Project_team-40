package client.view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import server.model.Product;
import client.view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductMenuManager extends MenuManager implements Initializable{
    private static Product product;
    public Pane secondaryInnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if (product != null)
            product.increaseVisitCount();
        setSecondaryPane(secondaryInnerPane);
        setSecondaryInnerPane("/layouts/shopping_menus/general_menu.fxml");
    }

    public void goToGeneral(){
        setSecondaryInnerPane("/layouts/shopping_menus/general_menu.fxml");
    }
    public void goToDigest(){
        setSecondaryInnerPane("/layouts/shopping_menus/digest_menu.fxml");
    }
    public void goToComments(){
        setSecondaryInnerPane("/layouts/shopping_menus/comments_menu.fxml");
    }
    public void goToSellers(){
        setSecondaryInnerPane("/layouts/shopping_menus/sellers_menu.fxml");
    }

    public static void setProduct(Product product){
        ProductMenuManager.product = product;
    }
    public static Product getProduct(){
        return product;
    }
}
