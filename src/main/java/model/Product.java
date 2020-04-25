package model;

import model.enumerations.SetUpStatus;
import model.users.Costumer;
import model.users.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private static HashMap<String, Product> allProducts = new HashMap<>();
    private String productId;
    private SetUpStatus status;
    private String name;
    private String company;
    private double price;
    private int count;
    private Seller seller;
    private Category category;
    private String explanation;
    private ArrayList<Score> allScores;
    private ArrayList<Comment> comments;
    private boolean isInOff;
    private ArrayList<Costumer> allBuyers;
    private HashMap<String, String> extraStringProperties;
    private HashMap<String, Double> extraValueProperties;
    private int visitCount;

    public Product(String name, String company, double price,
                   int count, Seller seller, Category category) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.count = count;
        this.seller = seller;
        this.category = category;
        this.allBuyers = new ArrayList<>();
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.extraStringProperties = new HashMap<>();
        this.extraValueProperties = new HashMap<>();
        productId = Utility.generateId();
    }

    public static void addProduct(Product product){
        allProducts.put(product.productId, product);
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getName(){
        return name;
    }

    public String getCompany(){
        return company;
    }

    public String getExplanation(){
        return explanation;
    }

    public static HashMap<String, Product> getAllProducts(){
        return allProducts;
    }

    public int getVisitCount(){
        return visitCount;
    }

    public SetUpStatus getStatus(){
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public boolean isInOff() {
        return isInOff;
    }

    public HashMap<String, Double> getExtraValueProperties(){
        return extraValueProperties;
    }

    public HashMap<String, String> getExtraStringProperties(){
        return extraStringProperties;
    }

    public String getStringProperty(String name){
        return extraStringProperties.get(name);
    }

    public Double getValueProperty(String name){
        return extraValueProperties.get(name);
    }

    public ArrayList<Costumer> getAllBuyers() {
        return allBuyers;
    }

    public void setStatus(SetUpStatus status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public double getAverageScore(){
        double sum = 0.0;
        for(Score score : allScores) {
            sum += score.getScore();
        }
        return sum / allScores.size();
    }

    public void setInOff(boolean inOff) {
        isInOff = inOff;
    }

    public void increaseVisitCount(int visitCount) {
        visitCount++;
    }

    public void addExtraProperty(String name, Double value){
        extraValueProperties.put(name, value);
    }

    public void addExtraProperty(String name, String value){
        extraStringProperties.put(name, value);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addScore(Score score){
        allScores.add(score);
    }

    public static void removeProduct(String id){
        allProducts.remove(id);
    }

    public static Product getProductById(String id){
        return allProducts.get(id);
    }

    public static void loadData(){}

    public static void saveData(){}

}
