package client.view.shopping_menus.products_and_offs_menus.products_view;

import client.controller.products_menu.ProductsMenuController;
import com.jfoenix.controls.JFXSlider;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterRangeItemManager implements Initializable{
    public Text variableName;
    public JFXSlider minSlider, maxSlider;

    private String property;
    private double min;
    private double max;

    public FilterRangeItemManager(){
        property = ProductsMenuManager.getLastRangeProperty();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        variableName.setText(property);

//        try {
////            double cap = AllProductsController.getInstance().getRangeCap(property);
//
//        } catch(MenuException e) {
//            e.printStackTrace();
//        }
        minSlider.setMax(100);
        maxSlider.setMax(100);
        minSlider.setValue(0);
        maxSlider.setValue(100);

        minSlider.setOnMouseReleased(mouseEvent -> {
            min = minSlider.getValue();
            ProductsMenuController.getInstance().disableFilter(property);
            try {
                ProductsMenuController.getInstance().addFilter(property, min, max);
            } catch(MenuException e) {
                e.printStackTrace();
            }
            ProductsMenuManager.getInstance().refresh();
        });

        maxSlider.setOnMouseReleased(mouseEvent -> {
            max = maxSlider.getValue();
            ProductsMenuController.getInstance().disableFilter(property);
            try {
                ProductsMenuController.getInstance().addFilter(property, min, max);
            } catch(MenuException e) {
                e.printStackTrace();
            }
            ProductsMenuManager.getInstance().refresh();
        });
    }
}
