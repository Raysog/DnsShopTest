package other;

import org.openqa.selenium.WebElement;

/**
 * Created by Ivan on 06.09.2019.
 * цена, гарантия,   описание
 */
public class Product {
    private String name;
    private double price;
    private double guaranteePeriod;
    private String description;
    private WebElement mainCharacteristics;

    public Product(String name, double price, double guaranteePeriod, String description, WebElement mainCharacteristics) {
        this.name = name;
        this.price = price;
        this.guaranteePeriod = guaranteePeriod;
        this.description = description;
        this.mainCharacteristics = mainCharacteristics;
    }


    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    public double getGuaranteePeriod(){
        return guaranteePeriod;
    }
}
