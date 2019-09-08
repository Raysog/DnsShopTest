package other;

import org.openqa.selenium.WebElement;
import pages.ResultsPage;

/**
 * Created by Ivan on 06.09.2019.
 */
public interface Searchingable {
    public ResultsPage search(String text);
}
