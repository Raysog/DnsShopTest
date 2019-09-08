package other;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 06.09.2019.
 */
public class Trash {
    private static Map<String,String> map = new HashMap<String, String>();
    private static ArrayList<Product> productsList = new ArrayList<Product>();

    public static WebDriver driver;


    public static double getBascketSum(){
        double sum = 0;
        for (Product product : productsList) {
            sum+=product.getPrice();
        }
        return sum;
    }

    public static ArrayList<Product> getProductsList (){
        return productsList;
    }

    public static String get(String key){
        return map.get(key);
    }

    public static void put(String key, String value){
        map.put(key, value);
    }

    public static void addToList (Product product){
        productsList.add(product);
    }

    public static void removeFromList (String productName){
         boolean flag = false;
         for (Product product : productsList) {
             if(product.getName().equals(productName)){
                 productsList.remove(product);
                 flag = true;
                 System.out.println("Продукт удален");
                 break;
             }
         }
         if (flag == false){
             System.out.println("Продукт не найден!");
         }
     }

     public static void changeProductPrice(String productName, double price){
         for (Product product : productsList) {
             if(product.getName().equals(productName)){
                 product.setPrice(price);
                 System.out.println("Цена обновлена");
             }  else {
                 System.out.println("Продукт не найден");
             }
         }
     }

    public static List<Product> findGuarantedElements(){
        List<Product> guarantedElements = new ArrayList<Product>();
        for (Product product : productsList) {
            if (product.getGuaranteePeriod() > 0) {
                guarantedElements.add(product);
            }
        }
        return guarantedElements;
    }

    public static List<Product> findNotGuarantedElements(){
        List<Product> guarantedElements = new ArrayList<Product>();
        for (Product product : productsList) {
            if (product.getGuaranteePeriod() == 0) {
                guarantedElements.add(product);
            }
        }
        return guarantedElements;
    }


    public static String printMap(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> pair : map.entrySet()) {
            sb.append("key: " + pair.getKey() + "\nvalue: " + pair.getValue() + "\n\n");
        }
        return sb.toString();
    }

    public static String printList(){
        StringBuilder sb = new StringBuilder();
        for (Product product: productsList) {
            sb.append("name: " + product.getName() + "\nprice: " + product.getPrice() + "\n\n");
        }
        return sb.toString();
    }
}
