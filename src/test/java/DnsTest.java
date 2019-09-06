import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import other.TestProperties;
import other.Trash;
import pages.MainPage;
import pages.ProductPage;
import pages.ResultsPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan on 06.09.2019.
 *
 * 1) открыть dns-shop
 * 2) в поиске найти playstation
 * 3) кликнуть по playstation 4 slim black
 * 4) запомнить цену
 * 5) Доп.гарантия - выбрать 2 года
 * 6) дождаться изменения цены и запомнить цену с гарантией
 * 7) Нажать Купить
 * 8) выполнить поиск Detroit
 * 9) запомнить цену
 * 10) нажать купить
 * 11) проверить что цена корзины стала равна сумме покупок
 * 12) перейри в корзину
 * 13) проверить, что для приставки выбрана гарантия на 2 года
 * 14) проверить цену каждого из товаров и сумму
 * 15) удалить из корзины Detroit
 * 16) проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
 * 17) добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation)
 * 18) нажать вернуть удаленный товар, проверить что Detroit появился в корзине и  сумма увеличилась на его значение
 *
 * Подсказки: Отдельные PageObject - поиск, страница с результатами, карточка товара, корзина, (и, возможно, позиция товара)
 * Следует создать отдельный класс Product - который будет являться моделью купленного товара (с полями цена, гарантия,   описание и что-тро еще)
 * Методы должны быть максимально параметризируемые, позволяющие проверить любой товар, и выполнить с ним любые шаги из данного теста.
 */




public class DnsTest {
    WebDriver driver;

    @Before
    public void init(){
        System.setProperty("webdriver.chrome.driver", TestProperties.getInstance().getProperty("path.chrome"));
        driver = new ChromeDriver();
        driver.get(TestProperties.getInstance().getProperty("url"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Trash.driver = driver;

    }


    @Test
    public void checkDnsShop(){
        MainPage mainPage = new MainPage();
        mainPage.search("playstation");

        ResultsPage resultsPage = new ResultsPage();
        resultsPage.chooseProduct(TestProperties.getInstance().getProperty("product"));

        ProductPage productPage = new ProductPage();
        productPage.savePriceOfCurrentProduct();
        productPage.addGaranty(TestProperties.getInstance().getProperty("guarantee"));
        productPage.refreshPrice();
        productPage.addToBasket();

        productPage.search(TestProperties.getInstance().getProperty("game"));
        ProductPage gameResultPage = new ProductPage();
        gameResultPage.savePriceOfCurrentProduct();
        gameResultPage.addToBasket();

        gameResultPage.checkPrice();

        /*

        BasketPage basketPage = productCard.goToBasket();
        basketPage.checkTotalPriceIs(Trash.get("product macbook"));*/

    }

    @After
    public void stopTest() throws Exception {
        //driver.quit();
    }
}

/**
 * Created by Ivan on 06.09.2019.
 *
 * + 1) открыть dns-shop
 * + 2) в поиске найти playstation
 * + 3) кликнуть по playstation 4 slim black
 * + 4) запомнить цену
 * + 5) Доп.гарантия - выбрать 2 года
 * + 6) дождаться изменения цены и запомнить цену с гарантией
 * + 7) Нажать Купить
 * + 8) выполнить поиск Detroit
 * + 9) запомнить цену
 * + 10) нажать купить
 * + 11) проверить что цена корзины стала равна сумме покупок
 * 12) перейри в корзину
 * 13) проверить, что для приставки выбрана гарантия на 2 года
 * 14) проверить цену каждого из товаров и сумму
 * 15) удалить из корзины Detroit
 * 16) проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
 * 17) добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation)
 * 18) нажать вернуть удаленный товар, проверить что Detroit появился в корзине и  сумма увеличилась на его значение
 *
 * Подсказки: Отдельные PageObject - поиск, страница с результатами, карточка товара, корзина, (и, возможно, позиция товара)
 * Следует создать отдельный класс Product - который будет являться моделью купленного товара (с полями цена, гарантия,   описание и что-тро еще)
 * Методы должны быть максимально параметризируемые, позволяющие проверить любой товар, и выполнить с ним любые шаги из данного теста.
 */
