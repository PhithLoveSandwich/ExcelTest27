import static org.junit.jupiter.api.Assertions.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class DataTest {

    @Test
    public void testFormSubmission() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        FileInputStream fis = null;
        XSSFWorkbook workbook = null;

        try {
            // เปิดไฟล์ Excel
            fis = new FileInputStream("C:\\Users\\PeteL\\eclipse-workspace\\DataDrivenTesting.Npru\\data.xlsx");
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1); // ใช้แถวที่ 1 (ไม่ใช่ 0) ถ้ามีหัวข้อ

            // อ่านค่าจาก Excel
            String firstNameTha = (row.getCell(0) != null) ? row.getCell(0).getStringCellValue() : "";
            String lastNameTha = (row.getCell(1) != null) ? row.getCell(1).getStringCellValue() : "";
            String firstNameEng = (row.getCell(2) != null) ? row.getCell(2).getStringCellValue() : "";
            String lastNameEng = (row.getCell(3) != null) ? row.getCell(3).getStringCellValue() : "";
            String birthDate = (row.getCell(4) != null) ? row.getCell(4).getStringCellValue() : "";
            String birthMonth = (row.getCell(5) != null) ? row.getCell(5).getStringCellValue() : "";
            String birthYear = (row.getCell(6) != null) ? row.getCell(6).getStringCellValue() : "";
            String idCard = (row.getCell(7) != null) ? row.getCell(7).getStringCellValue() : "";
            String password = (row.getCell(8) != null) ? row.getCell(8).getStringCellValue() : "";
            String mobile = (row.getCell(9) != null) ? row.getCell(9).getStringCellValue() : "";
            String email = (row.getCell(10) != null) ? row.getCell(10).getStringCellValue() : "";
            String address = (row.getCell(11) != null) ? row.getCell(11).getStringCellValue() : "";
            String province = (row.getCell(12) != null) ? row.getCell(12).getStringCellValue() : "";
            String district = (row.getCell(13) != null) ? row.getCell(13).getStringCellValue() : "";
            String subDistrict = (row.getCell(14) != null) ? row.getCell(14).getStringCellValue() : "";
            String postalCode = (row.getCell(15) != null) ? row.getCell(15).getStringCellValue() : "";

            // ตรวจสอบวันที่
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = birthDate;  // ใช้วันที่จาก Excel โดยตรง
            try {
                if (!birthDate.isEmpty()) {
                    Date date = dateFormat.parse(birthDate);
                    formattedDate = dateFormat.format(date);
                }
            } catch (Exception e) {
                e.printStackTrace(); // แสดงข้อผิดพลาดกรณีไม่สามารถแปลงวันที่ได้
            }

            // เปิดหน้าเว็บ
            driver.get("https://sc.npru.ac.th/sc_shortcourses/signup");
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // กรอกฟอร์ม
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstnameTha"))).sendKeys(firstNameTha);
            driver.findElement(By.id("lastnameTha")).sendKeys(lastNameTha);
            driver.findElement(By.id("firstnameEng")).sendKeys(firstNameEng);
            driver.findElement(By.id("lastnameEng")).sendKeys(lastNameEng);
            driver.findElement(By.name("birthDate")).sendKeys(formattedDate);  // ใช้วันที่ที่ปรับแล้ว
            driver.findElement(By.name("birthMonth")).sendKeys(birthMonth);
            driver.findElement(By.name("birthYear")).sendKeys(birthYear);
            driver.findElement(By.id("idCard")).sendKeys(idCard);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("mobile")).sendKeys(mobile);
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("address")).sendKeys(address);
            driver.findElement(By.name("province")).sendKeys(province);
            driver.findElement(By.id("district")).sendKeys(district);
            driver.findElement(By.id("subDistrict")).sendKeys(subDistrict);
            driver.findElement(By.id("postalCode")).sendKeys(postalCode);

            // คลิก Accept
            driver.findElement(By.id("accept")).click();

            // รอการยืนยัน
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationMessage")));
            
            // ตรวจสอบว่าฟอร์มถูกส่งสำเร็จ
            assertNotNull(confirmation);
            System.out.println("Form submission completed!");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed due to an exception: " + e.getMessage());
        } finally {
            try {
                if (fis != null) fis.close();
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}

