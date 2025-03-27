import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenGoogle {
    public static void main(String[] args) {
        // Set the path to chromedriver.exe explicitly
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");

        // Create instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Open Google
        driver.get("https://www.google.com");

        // Close the browser after use
        driver.quit();
    }
}
