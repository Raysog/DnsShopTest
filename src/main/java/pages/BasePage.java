package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import other.Basketable;
import other.Searchingable;
import other.Trash;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 06.09.2019.
 */
public abstract class BasePage implements Searchingable, Basketable{
    WebDriver driver;


    @FindBy(xpath = "//input[@autocomplete='off']")
    WebElement searchTextField;

    @FindBy(xpath = "//span[@class='ui-input-search__icon ui-input-search__icon_search'][ancestor::nav]")
    WebElement searchButton;

    @FindBy(xpath = "//a[@class='btn-cart-link'][ancestor::nav]")
    WebElement goToBasket;


    public BasePage() {
        this.driver = Trash.driver;
        PageFactory.initElements(driver, this);
    }

    public ResultsPage search(String text){
        searchTextField.sendKeys(text);
        searchButton.click();
        return new ResultsPage();
    }

    public void checkTotalPriceIs(){
        double actualPrice = Double.parseDouble(driver.findElement(By.xpath("//span[@data-of='totalPrice'][ancestor::div[@class='buttons']]")).getText().replace(" ", ""));
        double expectedPrice = Trash.getBascketSum();
        assertEquals("Цены разные", expectedPrice, actualPrice, 0);
    }

    public BasketPage goToBasket(){
        goToBasket.click();
        return new BasketPage();
    }


}
