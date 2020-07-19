package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.model.Receipt;

import java.util.HashMap;

public class ReceiptsResources extends ServerResource {

    @Post
    public String getUsersReceipts(String username) {
        System.out.println("ho");
        HashMap<Integer, Receipt> usersReceipts = Receipt.getUsersReceipts(username);
        System.out.println("hi");
        return new YaGson().toJson(username, new TypeToken<HashMap<Integer, Receipt>>(){}.getType());
    }
}
