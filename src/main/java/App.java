import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class App {
    public static void main(String[] args) {
        // ตั้งค่า WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // เปิดเว็บไซต์
            driver.get("https://sc.npru.ac.th/sc_shortcourses/signup");
            driver.manage().window().maximize();

            // กรอกข้อมูลในฟอร์ม
            driver.findElement(By.id("firstnameTha")).sendKeys("ธนวัฒน์");
            driver.findElement(By.id("lastnameTha")).sendKeys("ศรีแสง");
            driver.findElement(By.id("firstnameEng")).sendKeys("Thanawat");
            driver.findElement(By.id("lastnameEng")).sendKeys("Srisaeng");

            // เลือก dropdown
            driver.findElement(By.name("birthDate")).sendKeys("21");
            driver.findElement(By.name("birthMonth")).sendKeys("2");
            driver.findElement(By.name("birthYear")).sendKeys("2004");

            // กรอกเลขบัตรประชาชน
            driver.findElement(By.id("idCard")).sendKeys("1709901519481");

            // กรอกรหัสผ่าน (เข้ารหัสแล้ว)
            driver.findElement(By.id("password")).sendKeys("61+8fpJBjO1ZlVAZn3YVmA==");

            // กรอกเบอร์โทรศัพท์และอีเมล
            driver.findElement(By.id("mobile")).sendKeys("0855564028");
            driver.findElement(By.id("email")).sendKeys("664259020@webmail.npru.ac.th");

            // ที่อยู่
            driver.findElement(By.id("address")).sendKeys("19/1");

            // จังหวัด
            driver.findElement(By.name("province")).sendKeys("ราชบุรี");

            // อำเภอ
            WebElement district = driver.findElement(By.id("district"));
            district.sendKeys("เมือ");
            district.sendKeys(Keys.ENTER);
            district.sendKeys("เมืองราชบุรี");

            // ตำบลและรหัสไปรษณีย์
            driver.findElement(By.id("subDistrict")).sendKeys("โคกหม้อ");
            driver.findElement(By.id("postalCode")).sendKeys("70000");

            // ยอมรับข้อตกลง
            driver.findElement(By.id("accept")).click();

            // แสดงข้อความยืนยัน
            System.out.println("Form submission completed!");
            
            Thread.sleep(180000);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ปิด Browser
            driver.quit();
        }
    }
}