package server.model.search;

import server.model.Category;
import server.model.Product;
import server.model.enumerations.PropertyType;
import server.model.enumerations.SetUpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFilterOld{
    private ArrayList<Product> products;
    private Category category;
    private String productName;
    private String productCompany;
    private String sellerName;
    private SetUpStatus status;
    private Range price;
    private HashMap<String , String> stringProperties;
    private HashMap<String, Range> valueProperties;
    private HashMap<String, Boolean> availableExtraStringProperties;
    private HashMap<String , Boolean> availableExtraValueProperties;
    //FALSE : NOT DECLARED TRUE : DECLARED

    public ProductFilterOld(ArrayList<Product> products, Category category, String productName, String productCompany,
                            String sellerName, SetUpStatus status, Range price,
                            HashMap<String, String> stringProperties, HashMap<String, Range> valueProperties){
        this.products = products;
        this.category = category;
        this.productName = productName;
        this.productCompany = productCompany;
        this.sellerName = sellerName;
        this.status = status;
        this.price = price;
        this.stringProperties = stringProperties;
        this.valueProperties = valueProperties;
        availableExtraValueProperties = new HashMap<>();
        availableExtraStringProperties = new HashMap<>();
        if (category != null){
            for(Map.Entry<String, PropertyType> extraProperty : category.getExtraProperties().entrySet()) {
                if (stringProperties.containsKey(extraProperty) || valueProperties.containsKey(extraProperty)) {
                    if(extraProperty.getValue() == PropertyType.STRING)
                        availableExtraStringProperties.put(extraProperty.getKey(), Boolean.TRUE);
                    if(extraProperty.getValue() == PropertyType.RANGE)
                        availableExtraValueProperties.put(extraProperty.getKey(), Boolean.TRUE);
                }else{
                    if(extraProperty.getValue() == PropertyType.STRING)
                        availableExtraStringProperties.put(extraProperty.getKey(), Boolean.FALSE);
                    if(extraProperty.getValue() == PropertyType.RANGE)
                        availableExtraValueProperties.put(extraProperty.getKey(), Boolean.FALSE);
                }
            }

        }
    }

    public ArrayList<String> getAvailableFilters(){
        ArrayList<String> availableFilters = new ArrayList<>();
        availableFilters.addAll(getAvailableExtraStringProperties());
        availableFilters.addAll(getAvailableExtraValueProperties());
        return availableFilters;
    }

    public ArrayList<String> getAvailableExtraStringProperties(){
        ArrayList<String> availableStringProperties = new ArrayList<>();
        for(Map.Entry<String, Boolean> stringBooleanEntry : availableExtraStringProperties.entrySet()) {
            if(!stringBooleanEntry.getValue())
                availableStringProperties.add(stringBooleanEntry.getKey());
        }
        return availableStringProperties;
    }

    public ArrayList<String> getAvailableExtraValueProperties(){
        ArrayList<String> availableValueProperties = new ArrayList<>();
        for(Map.Entry<String, Boolean> stringBooleanEntry : availableExtraValueProperties.entrySet()) {
            if(!stringBooleanEntry.getValue())
                availableValueProperties.add(stringBooleanEntry.getKey());
        }
        return availableValueProperties;
    }

    public void disableFilter(String name){

        if(name.equalsIgnoreCase("category")){
            category = null;
            availableExtraStringProperties.clear();
            availableExtraValueProperties.clear();
        }else if(name.equalsIgnoreCase("name")){
            productName = null;
        }else if(name.equalsIgnoreCase("company")){
            productCompany = null;
        }else if(name.equalsIgnoreCase("seller")){
            sellerName = null;
        }else if(name.equalsIgnoreCase("status")){
            status = null;
        }else if(name.equalsIgnoreCase("price")){
            price = null;
        }else if(valueProperties.get(name) != null){
            valueProperties.remove(name);
            availableExtraValueProperties.put(name, Boolean.FALSE);
        }else if(stringProperties.get(name) != null){
            stringProperties.remove(name);
            availableExtraValueProperties.put(name, Boolean.FALSE);
        }

    }

    public ArrayList<Product> getFilter(){

        ArrayList<Product> filteredProduct = new ArrayList<>();

        for(Product product : products) {
            boolean isAddingProductValid = true;

            if(category != null && !product.getCategory().contains(category.getName())){
                continue;
            }
            if(productName != null && !product.getName().contains(productName)){
                continue;
            }
            if(productCompany != null && !product.getCompany().contains(productCompany)){
                continue;
            }
            if(sellerName != null && (!product.getSeller().getFirstName().contains(sellerName) &&
                    !product.getSeller().getLastName().contains(sellerName)) ){
                continue;
            }
            if(status != null && !product.getStatus().equals(status)){
                continue;
            }
            if(price != null && !price.contains(product.getPrice())){
                continue;
            }
            for(Map.Entry<String, Range> stringRangeEntry : valueProperties.entrySet()) {
                Range value = stringRangeEntry.getValue();
                double productValue = product.getValueProperty(stringRangeEntry.getKey());
                if(!value.contains(productValue)){
                    isAddingProductValid = false;
                    break;
                }
            }
            for(Map.Entry<String, String> stringStringEntry : stringProperties.entrySet()) {
                String value = stringStringEntry.getValue();
                String productValue = product.getStringProperty(stringStringEntry.getKey());
                if(!productValue.equals(value)){
                    isAddingProductValid = false;
                    break;
                }
            }
            if (isAddingProductValid)
                filteredProduct.add(product);
        }

        return filteredProduct;
    }

    public static ProductFilterOld getInstance(ArrayList<Product> products, HashMap<String, String> stringFilters, HashMap<String,Range> integerFilters){
        Category category = null;
        String productName = null;
        String productCompany = null;
        String sellerName = null;
        SetUpStatus status = null;
        Range range = null;
        HashMap<String, String> stringProperties = new HashMap<>();
        HashMap<String, Range>  valueProperties = new HashMap<>();
        for (String filter : stringFilters.keySet()) {
            if (filter.equalsIgnoreCase("name"))
                productName = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("category"))
                category = Category.getCategoryByName(stringFilters.get(filter));
            else if (filter.equalsIgnoreCase("company"))
                productCompany = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("seller"))
                sellerName = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("status"))
                status = SetUpStatus.valueOf(stringFilters.get(filter));
            else
                stringProperties.put(filter, stringFilters.get(filter));
        }

        for (String filter : integerFilters.keySet()) {
            if (filter.equalsIgnoreCase("price"))
                range = integerFilters.get(filter);
            else
                valueProperties.put(filter, integerFilters.get(filter));
        }

        return new ProductFilterOld(products, category, productName, productCompany, sellerName, status, range, stringProperties, valueProperties);
    }
}
