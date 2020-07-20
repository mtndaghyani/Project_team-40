package server.server_resources.customer_account_controller;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.ServerAuthenticator;
import server.controller.accounts.CustomerAccountController;

public class CustomerResource extends ServerResource {
    @Post
    public void createCustomer(){
        CustomerAccountController manager = CustomerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        manager.createCustomerAccount(username, password, firstName, lastName, email, phone, 0);
        ServerAuthenticator.getInstance().addToCustomerVerifier(username, password);
    }

}
