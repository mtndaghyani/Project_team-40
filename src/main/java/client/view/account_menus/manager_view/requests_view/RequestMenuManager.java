package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.model.requests.Request;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RequestMenuManager extends MenuManager implements Initializable {
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRequests();
    }

    private void loadRequests() {
        ArrayList<Request> requests = RequestHandler.get("/accounts/manager_account_controller/requests/",
                new HashMap<>(), true, new TypeToken<ArrayList<Request>>(){}.getType());
        for (Request request : requests) {
            try {
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_request_menus/request_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(request, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Request request, HBox hBox) {
        Label requestIdLabel =((Label) hBox.getChildren().get(0));
        Label requestTypeLabel =((Label) hBox.getChildren().get(1));
        Label requestStatusLabel =((Label) hBox.getChildren().get(2));
        requestIdLabel.setText(request.getRequestId());
        requestTypeLabel.setText(request.getType());
        requestStatusLabel.setText(request.getStatus());
    }

}
