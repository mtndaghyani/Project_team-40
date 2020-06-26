package view.shopping_menus.products_and_offs_menus.products_view;

import controller.menus.AllProductsController;
import controller.menus.ProductController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Off;
import model.Product;
import model.users.Seller;
import view.MenuManager;
import view.shopping_menus.product.product_view.ProductMenuManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductItemManager extends MenuManager implements Initializable{
    private static final String PATH = "/images/";
    private Product product;

    public ImageView image;
    public Label name;
    public Label price;
    public Label score;
    public Text offText;
    public Button digest;

    public ProductItemManager(){
        initializeProduct();
    }

    private void initializeProduct(){
        product = AllProductsController.getInstance().getAllProducts().get(ProductsMenuManager.getIndexOfLastUser());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {
            Image frame = new Image(getClass().getResourceAsStream("/product_images/" + product.getName() + ".jpg"));
            image.setImage(frame);
        }catch(Exception e){
        }

        digest.setOnAction(actionEvent -> {
            ProductMenuManager.setProduct(product);
            setMainInnerPane("/layouts/shopping_menus/product_menu.fxml");
        });

        //set other properties of product
        if(product != null){
            name.setText(product.getName());
            price.setText(product.getBasePrice() + " / " + product.getPrice());
            score.setText(Double.toString(product.getAverageScore()));
        }

        offText.setText("-");
        if(product.isInOff()){
            ArrayList<String> offIds = ((Seller) Seller.getUserByUsername(product.getSellerUsername())).getOffIds();
            for(String offId : offIds) {
                Off off = Off.getOffByID(offId);
                if(off.getProducts().contains(product)){
                    offText.setText("Until : " + off.getEndDate());
                }
            }
        }
    }

}
