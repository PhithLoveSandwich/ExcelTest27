import static org.junit.jupiter.api.Assertions.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

class UC1CheckLogin {

    @Test
    void testCheckLogin() throws Exception {
        // ตั้งค่า ChromeDriver อัตโนมัติ
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // จัดรูปแบบวันที่สำหรับบันทึกลง Excel
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String testDate = formatter.format(new Date());
        String testerName = "Naruapon Suwawijit";

        // กำหนดที่อยู่ของไฟล์ Excel
        String path = "C:\\Users\\PeteL\\eclipse-workspace\\DataDrivenTesting.Npru\\testdata.xlsx";

        try (FileInputStream fs = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fs)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum() + 1;

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            for (int i = 1; i < rowCount; i++) {
                try {
                    driver.get("https://katalon-demo-cura.herokuapp.com/");
                    wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-make-appointment"))).click();

                    // ดึงข้อมูลจาก Excel
                    String testcaseid = sheet.getRow(i).getCell(0).toString();
                    String username = (sheet.getRow(i).getCell(1) != null) ? sheet.getRow(i).getCell(1).toString() : "";
                    String password = (sheet.getRow(i).getCell(2) != null) ? sheet.getRow(i).getCell(2).toString() : "";

                    if (testcaseid.equals("tc104")) {
                        username = "";
                        password = "";
                    }

                    // กรอกข้อมูลล็อกอิน
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txt-username"))).sendKeys(username);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txt-password"))).sendKeys(password);
                    driver.findElement(By.id("btn-login")).click();

                    // ดึงค่า Expected Result จาก Excel
                    String expected = (sheet.getRow(i).getCell(3) != null) ? sheet.getRow(i).getCell(3).toString() : "";
                    String actual;

                    try {
                        if (testcaseid.equals("tc101")) {
                            actual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2"))).getText();
                        } else {
                            actual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[2]"))).getText();
                        }
                    } catch (NoSuchElementException e) {
                        actual = "Element Not Found";
                    }

                    // บันทึกผลลัพธ์ลง Excel
                    Row row = sheet.getRow(i);
                    row.createCell(4).setCellValue(actual);
                    row.createCell(5).setCellValue(actual.equals(expected) ? "Pass" : "Fail");
                    row.createCell(6).setCellValue(testDate);
                    row.createCell(7).setCellValue(testerName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // เขียนข้อมูลลงไฟล์ Excel
            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // ปิด WebDriver หลังเสร็จสิ้น
            driver.quit();
        }
    }
}




