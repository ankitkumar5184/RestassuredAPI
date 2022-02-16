package DataRestApi;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import io.restassured.response.Response;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AsurredApi
{
    String key = "sk_test_51KTsMNSBtf5IInhSXpO9zJZhqrGS3v0pwaJWWBAvygQ3yRauJ4S4cW2s4lCTu4GmkSDy9rWLQwnzziOlkNkcgNG000bym6khmJ";
    @DataProvider(name = "test")
    public Object[][] data() throws IOException {
        String path = System.getProperty("user.dir") + "\\Config\\details.xls";
        FileInputStream fis = new FileInputStream(path);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet = workbook.getSheetAt(0);
        int row = sheet.getLastRowNum();
        Object[][] arr = new Object[row-1][2];
        int a = 0;
        for (int i = 1; i < row; i++) {
            arr[a][0] = sheet.getRow(i).getCell(0).getStringCellValue();
            a++;
        }
        return arr;
    }

    @Test(dataProvider = "test")
    public void Edetails (String email, String name) {
        Response res= given().auth().basic(key, "").formParam("email", email).
                formParam("name", name).when().post("https://api.stripe.com/v1/customers");
        int rescode = res.getStatusCode();
        System.out.println("code is: " + rescode);
        String response = res.getBody().asString();
        System.out.println(response);
    }
}
