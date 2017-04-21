package com.darren.personal.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverToolKit {
    private static final int DEFAULT_WAIT_SECONDS = 10;
    private WebDriver driver;
    private WebDriverWait waiter;

    public WebDriverToolKit(WebDriver driver) {
        this(driver, DEFAULT_WAIT_SECONDS);
    }

    public WebDriverToolKit(WebDriver driver, int waitSeconds) {
        this.driver = driver;
        waiter = new WebDriverWait(driver, waitSeconds);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWaiter() {
        return waiter;
    }

}
