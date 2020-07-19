package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import javafx.scene.chart.PieChart;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class transactionsResources extends ServerResource {
    @Post
    public String getTransactions(String username) {
        String bankResponse = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF("get_transactions " + BankController.getBankController().getUsersTokens().get(username)
            + " *");
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            bankResponse = inputStream.readUTF();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new YaGson().toJson(bankResponse, String.class);
    }
}
