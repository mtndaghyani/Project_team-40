package server.controller.menus;

import exceptions.MenuException;
import server.model.Cart;
import server.model.Comment;
import server.model.Product;
import server.model.enumerations.Status;
import server.model.requests.AddComment;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductController{

    public static ProductController productController;
    private String productName;
    private Product productChosen;

    private ProductController(){

    }

    public static ProductController getInstance(){
        if(productController == null)
            productController = new ProductController();

        return productController;
    }

    public Product digestProduct(String productID) throws MenuException{
        Product product = Product.getProductById(productID);
        if(product == null)
            throw new MenuException("No product with such name exists.");
        return product;
    }
    //Server
    public void addProductToCart(String username, String productID, int count) throws MenuException{
        Product product = Product.getProductById(productID);
        if(product == null)
            throw new MenuException("No product with such name exists.");

        if(count > product.getCount())
            throw new MenuException("Not enough goods available in stock.");

        if(username == null) {
            Cart.getThisCart().addProduct(productID, count);
        }
        else {
            Customer customer = (Customer)User.getUserByUsername(username);
            customer.getCart().put(productID, count);
        }
    }
    //Server
    public ArrayList<String> getSellersForProduct(String productName){
        ArrayList<String> sellers = new ArrayList<>();

        for(Product product : Product.getAllProducts().values()) {
            if(product.getName().equals(productName))
                sellers.add(product.getSeller().getUsername());
        }
        return sellers;
    }

    public void selectSellerForProduct(String productName, String sellerUsername) throws MenuException{
        ArrayList<Product> products = new ArrayList<>();
        for(Product product : Product.getAllProducts().values()) {
            if(product.getName().equals(productName))
                products.add(product);
        }

        if(products.size() == 0)
            throw new MenuException("No product with such name exists");

        Product productSelected = null;

        for(Product product : products) {
            if(product.getSeller().getUsername().equals(sellerUsername))
                productSelected = product;
        }

        if(productSelected == null)
            throw new MenuException("This seller doesn't offer product with this name.");

        productChosen = productSelected;
    }

    public Product getProductChosen(){
        return productChosen;
    }

    public ArrayList<String> getProductAttributesFace(String productID) throws MenuException{
        Product product = Product.getProductById(productID);

        if(product == null)
            throw new MenuException("No product with such name exists.");

        ArrayList<String> productAttributes = new ArrayList<>();
        productAttributes.addAll(Arrays.asList(
                "Seller",
                "Name",
                "Price",
                "Status",
                "Company",
                "Category",
                "Explanation"
        ));

        for(String name : product.getExtraStringProperties().keySet()) {
            productAttributes.add(name);
        }

        for(String name : product.getExtraValueProperties().keySet()) {
            productAttributes.add(name);
        }

        return productAttributes;
    }
    //Server
    public HashMap<String, String> getProductAttributes(String productID) throws MenuException{
        Product product = Product.getProductById(productID);

        if(product == null)
            throw new MenuException("No product with such name exists.");

        HashMap<String, String> productAttributes = new HashMap<>();

        productAttributes.put("Seller", product.getSeller().getUsername());
        productAttributes.put("Name" , product.getName());
        productAttributes.put("Price", String.valueOf(product.getPrice()));
        productAttributes.put("Status", product.getStatus().toString().toLowerCase());
        productAttributes.put("Company", product.getCompany());
        productAttributes.put("Category", product.getCategory());
        productAttributes.putAll(product.getExtraStringProperties());

        for(Map.Entry<String, Double> entry : product.getExtraValueProperties().entrySet()) {
            productAttributes.put(entry.getKey(), entry.getValue().toString());
        }

        return productAttributes;
    }
    //Server
    public ArrayList<Comment> getComments(String productID) throws MenuException{
        Product product = Product.getProductById(productID);

        if(product == null)
            throw new MenuException("No product with such name exists.");

        return product.getComments();
    }

    //Server
    public void addComment(String username, String productID, String title, String content) throws MenuException{
        Product product = Product.getProductById(productID);
        User user = User.getUserByUsername(username);

        if(product == null)
            throw new MenuException("No product with such name exists.");
        if(user == null)
            throw new MenuException("You are not logged in.");
        Comment comment = null;
        for(Comment comment1 : product.getComments()) {
            if(comment1.getUsername().equals(user.getUsername())) {
                comment = comment1;
                comment.updateText(title, content);
                comment.setStatus(Status.Waiting);
            }
        }

        if(comment == null) {
            comment = new Comment(user.getUsername(), productID, title, content);
            Comment.addComment(comment);
            product.addComment(comment);
        }


        Manager.addRequest(new AddComment(comment));

    }

    public double getProductsRatings(String productId) {
        return Product.getProductById(productId).getAverageScore();
    }
}
