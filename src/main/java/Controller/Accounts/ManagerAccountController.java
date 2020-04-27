package Controller.Accounts;

import exceptions.AccountsException;
import model.Category;
import model.DiscountCode;
import model.Product;
import model.requests.Request;
import model.users.Customer;
import model.users.Manager;
import model.users.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ManagerAccountController extends AccountController{

    private static ManagerAccountController managerAccountController;

    public void createManagerAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber) throws AccountsException {
        if(User.doesUserExist(username))
            throw new AccountsException("User exists with this username.");
        new Manager(username, password, firstName, lastName, email, phoneNumber);
    }

    public ArrayList<String> getAllUserNames(){
        return new ArrayList<>(User.getAllUsernames());
    }

    public User getUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found");
        return user;

    }

    public void deleteUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found.");
        User.deleteUser(user);
    }

    public void removeProduct(String productID) throws AccountsException {
        Product product = Product.getProductById(productID);
        if(product == null)
            throw new AccountsException("Product not found.");
        Product.removeProduct(productID);
    }

    private void addCustomers(DiscountCode discountCode,ArrayList<String> customers) throws AccountsException {
        for (String customer : customers) {
            User user = User.getUserByUsername(customer);
            if(!(user instanceof Customer))
                throw new AccountsException("You can just add customers.");
            discountCode.addCostumer((Customer)user);
        }
    }
    public void createDiscount(String startDate, String endDate, int percentage, double maxDiscount, int countForEachUser,
                              ArrayList<String> listOfUsernames) throws AccountsException {
        SimpleDateFormat startFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat endFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start;
        Date end;
        try {
            start = startFormat.parse(startDate);
            end = endFormat.parse(endDate);
        }
        catch (Exception e){
            throw new AccountsException("Invalid date format");
        }
        DiscountCode discountCode = new DiscountCode(start, end, percentage, maxDiscount, countForEachUser);
        if(discountCode.isExpired())
            throw new AccountsException("Invalid date");
        addCustomers(discountCode, listOfUsernames);
    }

    public ArrayList<DiscountCode> getAllDiscountCodes(){
        return DiscountCode.getAllDiscountCodes();
    }

    public DiscountCode getDiscount(String code) throws AccountsException {
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(discountCode == null)
            throw new AccountsException("Discount code not found.");
        return discountCode;
    }

    public void removeDiscount(String code) throws AccountsException{
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if (discountCode == null)
            throw new AccountsException("Discount code not found.");
        DiscountCode.removeDiscountCode(discountCode);
    }

    public void editDiscount(String code, String field, String newField){

    }

    public ArrayList<Request> getALlRequests(){
        return Manager.getRequests();
    }

    public Request getRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        return request;
    }

    public void acceptRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        request.setAccepted(true);
        request.action();
    }

    public void declineRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        request.setAccepted(false);
    }

    public HashMap<String, String> getAllCategories(){
        return null;
    }

    public Category getCategory(String categoryName){
        return null;
    }

    public void editCategory(String categoryName, String field, String newField) throws AccountsException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new AccountsException("Category not found.");
        if (field.equalsIgnoreCase("name")){
            if (Category.getCategoryByName(newField) == null)
                throw new AccountsException("A category exists with this name.");
            category.setName(newField);
        }
        else if (!category.getExtraProperties().contains(field))
            throw new AccountsException("There is no field with this name.");
        else{
            int fieldIndex = category.getExtraProperties().indexOf(field);
            category.getExtraProperties().set(fieldIndex, newField);
        }
        //TODO: How to change products properties after editing category
    }

    public void createCategory(String categoryName,ArrayList<String> properties){

    }

    public void removeCategory(String categoryName){

    }

    public void addSubCategory(String parentName, String subName){

    }

    public void removeSubCategory(String parentName, String subName){

    }

    private ManagerAccountController(){

    }

    public static ManagerAccountController getInstance(){
        if(managerAccountController == null)
            managerAccountController = new ManagerAccountController();

        return managerAccountController;
    }
}
