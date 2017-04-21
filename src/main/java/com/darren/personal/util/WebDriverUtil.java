package com.darren.personal.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.darren.personal.constant.WebDriverType;
import com.darren.personal.scraper.WebDriverToolKit;

public class WebDriverUtil {

    public static WebDriver getWebDriver(String url) {
        return getWebDriver(url, WebDriverType.FIREFOX);
    }

    public static WebDriver getWebDriver(String url, WebDriverType type) {
        WebDriver driver = null;
        if (WebDriverType.FIREFOX.equals(type)) {
            driver = new FirefoxDriver();
        } else if (WebDriverType.CHROME.equals(type)) {
            driver = new ChromeDriver();
        } else {
            driver = new FirefoxDriver();
        }
        //driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.get(url);

        return driver;
    }

    public static boolean clickLink(WebDriverToolKit driverToolKit, String xpath) {
        if (!driverToolKit.getDriver().getCurrentUrl().startsWith("http")) {
            return false;
        }
        List<WebElement> elements = driverToolKit.getDriver().findElements(By.xpath(xpath));
        if (elements.size() > 0) {
            driverToolKit.getWaiter().until(ExpectedConditions.elementToBeClickable(elements.get(0)));
            WebElement link = elements.get(0);
            link.click();
            

            return true;
        } else {
            return false;
        }
    }
}
