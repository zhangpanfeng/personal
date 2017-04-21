package com.darren.personal.scraper;

import org.openqa.selenium.WebDriver;

import com.darren.personal.constant.WebDriverType;
import com.darren.personal.util.WebDriverUtil;

public class Scraper {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        WebDriver driver = WebDriverUtil.getWebDriver("http://blog.csdn.net/zpf336", WebDriverType.CHROME);
        WebDriverToolKit driverToolKit = new WebDriverToolKit(driver);
        int iteration = 0;
        boolean result = WebDriverUtil.clickLink(driverToolKit, "//span[@class='link_title']/a");
        while (result) {
            result = WebDriverUtil.clickLink(driverToolKit,
                    "//ul[@class='article_next_prev']/li[@class='prev_article']/a");
            iteration++;
            if (iteration % 10 == 0) {
                System.out.println(iteration);
            }
        }
        System.out.println(iteration);
        driver.quit();
        long end = System.currentTimeMillis();
        System.out.println("second = " + (end - start) / 1000);
        System.out.println("min = " + ((end - start) / 1000) / 60);
    }
}
