package PageView;

import Support.SetupServer;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage {
    SetupServer driver;
    By btnFilter = By.xpath("//button[@class='btn btn-filter module_grid__btn_filter btn btn-default']");
    By requestStatus = By.id("formControlsSelect");
    By Inactive = By.xpath("//option[contains(text(),'Inactive')]");
    By btnApplyFilter = By.xpath("//button[@class='btn-filter btn btn-default']");
    By lblFirstName = By.xpath("//th[contains(text(),'First Name')]");
    By sort = By.xpath("//th[contains(text(),'First Name')]/span");
    By getListRequestStatus = By.xpath("//tbody/tr/td[2]");
    By getListName = By.xpath("//tbody/tr/td[6]");

    public HomePage(SetupServer driver) {
        this.driver = driver;
    }

    public void Verify_filter_Student_Access_Request_with_INACTIVE() {
        clickFiltersButton();
        chooseRequestStatus();
        selectInactiveOption();
        clickApplyFiltersButton();
        verifyFitersByRequestStatusWithInactive();
    }

    public void Verify_sorting_of_First_Name_column() {
        columnFirstName();
        // Descending
        verifySortingofFirstName();
        columnFirstName();
        // Ascending
        verifySortingofFirstName();
    }

    private void verifyFitersByRequestStatusWithInactive() {
        System.out.println("verify Fiters By Request Status With Inactive");
        List<WebElement> elements = driver.findElements(getListRequestStatus);
        for (WebElement element : elements) {
            String actualFiters = element.getText().split(" ")[0];
//            Assert.assertEquals("Inactive", actualFiters);
            if (actualFiters.equals("Inactive")) {
                System.out.println("Filter by 'INACTIVE' - PASSED");
            } else {
                System.out.println("Filter by 'INACTIVE' - FAILED");
                driver.captureScreenShot("Filter by 'INACTIVE' - FAILED");
                break;
            }
        }
    }

    // click button Filters
    private void clickFiltersButton() {
        System.out.println("Click 'Filters' button");
        driver.findElement(btnFilter).click();
    }

    // Drop-down request Status
    private void chooseRequestStatus() {
        System.out.println("Click 'Request Status'");
        driver.findElement(requestStatus).click();
    }

    // select option INACTIVE
    private void selectInactiveOption() {
        System.out.println("select 'INACTIVE' option");
        driver.Wait(2);
        driver.findElement(Inactive).click();
    }

    // button Apply Filters
    private void clickApplyFiltersButton() {
        System.out.println("Click 'Apply Filters' button");
        driver.Wait(3);
        driver.findElement(btnApplyFilter).click();
    }

    private void verifySortingofFirstName() {
        System.out.println("Verify sorting of First Name column");
        driver.Implicit_Wait(5);
        String getValue = driver.findElement(sort).getAttribute("class");
        List<WebElement> elements = driver.findElements(getListName);
        List actualList = new ArrayList();
        for (WebElement element : elements) {
            actualList.add(element.getText());
        }
        List expectedList = new ArrayList();
        expectedList.addAll(actualList);

        if (getValue.equals("order dropup")) {
            Collections.sort(expectedList);
            Assert.assertArrayEquals(expectedList.toArray(), actualList.toArray());
            System.out.println("Sorted 'First Name' in ascending - PASSED");

        } else {
            Collections.sort(expectedList, Collections.reverseOrder());
            Assert.assertArrayEquals(expectedList.toArray(), actualList.toArray());
            System.out.println("Sorted 'First Name' in descending - PASSED");
        }
    }

    private void columnFirstName() {
        System.out.println("Click 'First Name' column");
        driver.Explicit_Wait(5, lblFirstName).click();
    }
}
