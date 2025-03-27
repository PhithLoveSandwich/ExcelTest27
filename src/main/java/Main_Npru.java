import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;

public class Main_Npru {
    @Test
    void test101() throws Exception {
        System.setProperty("webdriver.chrome.driver", "./chromedriver/chromedriver-win32/chromedriver.exe");

        String path = "./Excel/data.xlsx";
        FileInputStream fs = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int row = sheet.getLastRowNum() + 1;

        for (int i = 1; i < row; i++) {
            WebDriver driver = new ChromeDriver();
            driver.get("https://sc.npru.ac.th/sc_shortcourses/signup");

            Row rows = sheet.getRow(i);
            if (rows == null) continue;

            // ดึงค่าจาก Excel
            String nameTitleTha = (rows.getCell(1) != null) ? rows.getCell(1).toString() : "";
            String firstnameTha = (rows.getCell(2) != null) ? rows.getCell(2).toString() : "";
            String lastnameTha = (rows.getCell(3) != null) ? rows.getCell(3).toString() : "";
            String nameTitleEng = (rows.getCell(4) != null) ? rows.getCell(4).toString() : "";
            String firstnameEng = (rows.getCell(5) != null) ? rows.getCell(5).toString() : "";
            String lastnameEng = (rows.getCell(6) != null) ? rows.getCell(6).toString() : "";
            String birthDate = (rows.getCell(7) != null) ? String.valueOf((int) rows.getCell(7).getNumericCellValue()) : "";
            String birthMonth = (rows.getCell(8) != null) ? String.valueOf((int) rows.getCell(8).getNumericCellValue()) : "";
            String birthYear = (rows.getCell(9) != null) ? String.valueOf((int) rows.getCell(9).getNumericCellValue()) : "";
            String idCard = (rows.getCell(10) != null) ? rows.getCell(10).toString() : "";
            String password = (rows.getCell(11) != null) ? rows.getCell(11).toString() : "";
            String mobile = (rows.getCell(12) != null) ? rows.getCell(12).toString() : "";
            String email = (rows.getCell(13) != null) ? rows.getCell(13).toString() : "";
            String address = (rows.getCell(14) != null) ? rows.getCell(14).toString() : "";
            String province = (rows.getCell(15) != null) ? rows.getCell(15).toString() : "";
            String district = (rows.getCell(16) != null) ? rows.getCell(16).toString() : "";
            String subDistrict = (rows.getCell(17) != null) ? rows.getCell(17).toString() : "";
            String postalCode = (rows.getCell(18) != null) ? String.valueOf((int) rows.getCell(18).getNumericCellValue()) : "";

            System.out.println("birthMonth" + birthMonth);
            // กรอกข้อมูลลงฟอร์ม
            new Select(driver.findElement(By.id("nameTitleTha"))).selectByVisibleText(nameTitleTha);
            driver.findElement(By.id("firstnameTha")).sendKeys(firstnameTha);
            driver.findElement(By.id("lastnameTha")).sendKeys(lastnameTha);
            new Select(driver.findElement(By.id("nameTitleEng"))).selectByVisibleText(nameTitleEng);
            driver.findElement(By.id("firstnameEng")).sendKeys(firstnameEng);
            driver.findElement(By.id("lastnameEng")).sendKeys(lastnameEng);
            new Select(driver.findElement(By.id("birthDate"))).selectByVisibleText(birthDate);
            new Select(driver.findElement(By.id("birthMonth"))).selectByValue(birthMonth);
            new Select(driver.findElement(By.id("birthYear"))).selectByValue(birthYear);
            driver.findElement(By.id("idCard")).sendKeys(idCard);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("mobile")).sendKeys(mobile);
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("address")).sendKeys(address);
            driver.findElement(By.id("province")).sendKeys(province);
            driver.findElement(By.id("district")).sendKeys(district);
            driver.findElement(By.id("subDistrict")).sendKeys(subDistrict);
            driver.findElement(By.id("postalCode")).sendKeys(postalCode);

            WebElement accept = driver.findElement(By.id("accept"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if (!accept.isSelected()){
                js.executeScript("arguments[0].click();",accept);
            }
//            driver.quit();
        }

        workbook.close();
        fs.close();
    }
}