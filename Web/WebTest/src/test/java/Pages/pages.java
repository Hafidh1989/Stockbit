package Pages;

import org.openqa.selenium.By;

public class pages {
    public static final By PAGE_TITLE = By.xpath("//*[@id=\"app\"]/div/div/div[1]/div");
    public static final By SELECT_VALUE = By.xpath("//*[@id=\"withOptGroup\"]/div/div[1]/div[1]");
    public static final By ANOTHER_VALUE_SELECTED = By.xpath("//*[@id=\"withOptGroup\"]/div/div[1]/div[1]");
    public static final By SELECT_ONE = By.xpath("//*[@id=\"selectOne\"]/div/div[1]/div[1]");
    public static final By OTHER_SELECTED_VAL = By.xpath("//*[@id=\"selectOne\"]/div/div[1]/div[1]");
    public static final By SELECT_OLD_STYLE = By.xpath("//*[@id=\"oldSelectMenu\"]");
    public static final By MULTISELECT_DROPDOWN = By.xpath("//*[@id=\"selectMenuContainer\"]/div[7]/div/div/div/div[1]");

    // Books
    public static final By SEARCH_BOX_BOOK = By.xpath("//*[@id=\"searchBox\"]");
    public static final By NO_ROWS_SEARCH = By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/div[3]");
    public static final By SEARCH_BOOK_RESULT = By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/div");
    public static final By BOOK_LINK = By.xpath("//*[@id=\"see-book-Git Pocket Guide\"]/a");
    public static final By BOOK_TITLE = By.xpath("//*[@id=\"title-wrapper\"][@id=\"title-wrapper\"]/div[2]");
    public static final By BOOK_AUTHOR = By.xpath("//*[@id=\"author-wrapper\"][@id=\"author-wrapper\"]/div[2]");
    public static final By BOOK_PUBLISHER = By.xpath("//*[@id=\"publisher-wrapper\"][@id=\"publisher-wrapper\"]/div[2]");

}
