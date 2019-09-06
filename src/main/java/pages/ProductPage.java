package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import other.Product;
import other.TestProperties;
import other.Trash;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 06.09.2019.
 */
public class ProductPage extends BasePage {

    double period = 0;
    By productPrice = By.xpath("//span[@class='current-price-value'][ancestor::div[@class='hidden-xs hidden-sm price-block-wrap price-block-wrap_view_desktop']]");
    By productName = By.xpath("//h1[@class='page-title price-item-title']");
    By addToBasket = By.xpath("//button[@class='btn btn-cart btn-lg']");
    By description = By.xpath("//p[ancestor::div[@class='price-item-description']]");
    By mainCharacteristics = By.xpath("//div[@class='options-group']");

    By addGuarantee = By.xpath("//select[@class='form-control select']");
    By goToBasket = By.xpath(".//a[@href='/cart']");

    By addedToBasketFlag = By.xpath("//button[@data-url='/order/begin/']");

    public void savePriceOfCurrentProduct(){
        String value = driver.findElement(productPrice).getAttribute("data-price-value");
        String key = driver.findElement(productName).getText();
        Trash.put(key, value);
    }


    public void addToBasket(){
        driver.findElement(addToBasket).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.pollingEvery(1, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(addedToBasketFlag));

        /*Integer.parseInt(driver.findElement(productPrice).getAttribute("data-price-value")),*/

        Trash.addToList(new Product(driver.findElement(productName).getText(),
                                    Double.parseDouble(Trash.get(driver.findElement(productName).getText())),
                                    period,
                                    driver.findElement(description).getText(),
                                    driver.findElement(mainCharacteristics)
                        ));
    }

    public void addGaranty(String guarantee) {
        Select select = new Select(driver.findElement(addGuarantee));
        select.selectByValue(guarantee);
        period = Double.parseDouble(guarantee);
    }

    public void refreshPrice(){
        String val = driver.findElement(productPrice).getText();
        String value =  val.replace(" ", "");
        Trash.changeProductPrice(driver.findElement(productName).getText(), Double.parseDouble(value));
        String key = driver.findElement(productName).getText();
        Trash.put(key, value);
    }

    public void checkPrice(){
        double actualPrice = Double.parseDouble(driver.findElement(By.xpath("//span[@data-of='totalPrice'][ancestor::div[@class='buttons']]")).getText().replace(" ", ""));
        System.out.println("actual: " + actualPrice);
        double expectedPrice = Trash.getBascketSum();
        System.out.println("expected: " + expectedPrice);
        assertEquals("Цены разные", expectedPrice, actualPrice, 0);
        //assertEquals("Цены разные", expectedPrice, actualPrice);
    }

    /*public BasketPage goToBasket(){
        driver.findElement(goToBasket).click();
        return new BasketPage(driver);
    }*/
}
