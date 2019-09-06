package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import other.Searchingable;
import other.Trash;

/**
 * Created by Ivan on 06.09.2019.
 */
public abstract class BasePage implements Searchingable{
    WebDriver driver;


    @FindBy(xpath = "//input[@autocomplete='off']")
    WebElement searchTextField;

    @FindBy(xpath = "//span[@class='ui-input-search__icon ui-input-search__icon_search'][ancestor::nav]")
    WebElement searchButton;


    public BasePage() {
        this.driver = Trash.driver;
        PageFactory.initElements(driver, this);
    }

    public void search(String text){
        searchTextField.sendKeys(text);
        searchButton.click();
    }


}
