package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan on 06.09.2019.
 */
public class ResultsPage extends BasePage{

    public void chooseProduct(String productName) {
        String productXpath = String.format(".//a[contains(text(), '%s')]", productName);
        WebElement productItem = driver.findElement(By.xpath(productXpath));
        productItem.click();
    }
}
