package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import other.Product;
import other.Trash;


import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ivan on 07.09.2019.
 */
public class BasketPage extends BasePage{

    List<Product> products = null;
    List<WebElement> productsElements = driver.findElements(By.xpath("//div[@class='cart-list__product']"));

    public void checkGuarantee(String productName){
        Product interestedProduct = null;
        products = Trash.findGuarantedElements();
        for (Product product: products) {
            if (product.getName().contains(productName)){
                interestedProduct = product;
                break;
            }
        }

        WebElement selectedElement = null;
        for (WebElement element : productsElements) {
            String elementName = element.findElement(By.xpath("//a[@class='cart-list__product-name-link']")).getText();
            if(elementName.contains(productName)){
                selectedElement = element;
                break;
            }
        }


        if ((interestedProduct != null) && (selectedElement != null)) {
            WebElement guaranteeButton = selectedElement.findElement(By.xpath("//div[@class='radio radio_checked'][ancestor::div[@class='cart-list__product-additional-warranty cart-list__product-additional-warranty_wide-screen']]"));
            String value = (interestedProduct.getGuaranteePeriod() + "").replace(".0", "");
            assertTrue("Установлена ложная гарантия", guaranteeButton.getText().contains(value));
        } else {
            System.out.println("Продукт не найден");
        }

        products = null;

    }

    public void checkEveryPriceAndSum () {
        Map<String, Double> storedPrice = getStoredPrice();
        Map<String, Double> visiblePrice = getVisiblePrice();
        List<String>[] array = checkMaps(storedPrice, visiblePrice);
        if (array == null) {
            for (String s : storedPrice.keySet()) {
                assertEquals(String.format("Цена на товар %s разная", s), storedPrice.get(s), visiblePrice.get(s));
            }
            double totalVisiblePrice = Double.parseDouble(driver.findElement(By.xpath("//span[ancestor::div[@class='item-price'][ancestor::div[@class='total-amount__info-block']]]")).getText().replace(" ", ""));
            double totalStoredPrice = 0;
            for (String s: storedPrice.keySet()) {
                totalStoredPrice+=storedPrice.get(s);
            }
            assertEquals("Итоговая цена разная", totalStoredPrice, totalVisiblePrice, 0);
        } else {
            for (int i = 0; i < array.length; i++){
                for (String s : array[i]) {
                    if (i == 0) {
                        System.out.println(String.format("Товар %s отсутствует в памяти", s));
                    } else {
                        System.out.println(String.format("Товар %s отсутствует в корзине", s));
                    }
                }
            }
        }
    }

    private List<String>[] checkMaps(Map<String, Double> stored,Map<String, Double> visible) {
        List<String>[] array = new ArrayList[2];
        List<String> inStored = new ArrayList<String>();
        List<String> inVisible = new ArrayList<String>();
        for (String s: stored.keySet()) {
            if(!visible.keySet().contains(s)){
                inStored.add(s);
            }
        }
        for (String s: visible.keySet()) {
            if(!stored.keySet().contains(s)){
                inVisible.add(s);
            }
        }
        if (!inStored.isEmpty()){
            array[0] = inStored;
        }
        if (!inVisible.isEmpty()){
            array[1] = inVisible;
        }
        if ((inStored.isEmpty()) && (inVisible.isEmpty())){
            array = null;
        }
        return array;
    }


    private Map<String,Double> getStoredPrice() {
        Map<String,Double> storedPrice = new TreeMap<String, Double>();
        List<Product> products = Trash.getProductsList();
        for (Product product : products) {
            if(!storedPrice.containsKey(product.getName())){
                storedPrice.put(product.getName(), product.getPrice());
            } else {
                storedPrice.put(product.getName(), storedPrice.get(product.getName()) + product.getPrice());
            }
        }
        return storedPrice;
    }


    private Map<String,Double> getVisiblePrice() {
        Map<String,Double> visiblePrice = new TreeMap<String, Double>();
        List<WebElement> productCards = driver.findElements(By.xpath("//div[@class='cart-list__product-thumbnail']"));
        WebElement name = null;
        WebElement guarantyPrice = null;
        WebElement productPrice = null;

        for (WebElement productCard : productCards) {
            double guarantyPriceValue = 0;
            name = productCard.findElement(By.xpath("./*//a[ancestor::div[@class='cart-list__product-name']]"));
            productPrice = productCard.findElement(By.xpath("./*//span[ancestor::div[@class='cart-list__product-price'][ancestor::div[@class='cart-list__product-thumbnail']]]"));
            try{
                guarantyPrice = productCard.findElement(By.xpath("./*//label[@class='radio__label'][ancestor::div[@class='radio radio_checked'][ancestor::div[@class='cart-list__product-additional-warranty cart-list__product-additional-warranty_wide-screen']]]"));
            } catch (Exception e) {
                guarantyPrice = null;
            }
        if(guarantyPrice != null) {
            String guarantyPriceText = guarantyPrice.getText();
            guarantyPriceValue = Double.parseDouble(guarantyPriceText.substring(guarantyPriceText.indexOf("(") + 1, guarantyPriceText.length() - 1).replace(" ", ""));
        }
            String productPriceText = productPrice.getText();
            double productPriceValue = Double.parseDouble(productPriceText.replace(" ", ""));
            double totalProductPrice = guarantyPriceValue + productPriceValue;
            if(!visiblePrice.containsKey(name.getText())){
                visiblePrice.put(name.getText(), totalProductPrice );
            } else {
                visiblePrice.put(name.getText(), visiblePrice.get(name.getText()) + totalProductPrice);
            }

        }
        return visiblePrice;
    }
}
