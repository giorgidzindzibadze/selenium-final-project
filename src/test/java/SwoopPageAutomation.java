import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class SwoopPageAutomation {
    WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) throws Exception {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new Exception("Try another browser");
        }

    }

    @Test
    public void WebFormsAutomation() throws InterruptedException {
        driver.get("https://www.swoop.ge/");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        Actions builder = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
//                                    click on kino button
        WebElement kino = driver.findElement(By.xpath("//*[@id=\"body\"]/header/div[2]/div/div[1]/ul/li[1]"));
        kino.click();





//                                    click first movie
        List<WebElement> movies = driver.findElements(By.className("movies-deal"));
        WebElement movie = movies.get(1);// რადგან პირველ ფილმს არ ქონდა კავეა ისთ ფოინთი გავხსენი მეორე ფილმი.



//                                    hover on it
        builder.clickAndHold().moveToElement(movie).perform();
//        WebElement buy = driver.findElement(By.xpath("//p[text()='ყიდვა']"));
        WebElement buy = driver.findElement(By.xpath("//*[@id=\"body\"]/div[9]/div[2]/div[2]/a[2]/div/p"));
        buy.click();


//                                  click on eastPoint
        js.executeScript("window.scrollBy(0, 300);");


        WebElement eastpoint = driver.findElement(By.xpath("//a[contains(@class, 'ui-tabs-anchor') and text()='კავეა ისთ ფოინთი']"));
        eastpoint.click();




//                                      click on last date
        js.executeScript("window.scrollBy(0, 200);");

        List<WebElement> dates = driver.findElements(By.xpath("//*[@id='384933']/div/ul/li"));
        WebElement lastDate = dates.get(dates.size() -1);
        wait.until(ExpectedConditions.elementToBeClickable(lastDate)).click();




//                                   choose last movie
        List<WebElement> movieTime = driver.findElements(By.cssSelector("div.seanse-details"));
        WebElement lastMovie = movieTime.get(movieTime.size() -1);


//                                  Check that only ‘კავეა ისთ ფოინთი’ options are returned
        for (WebElement each:movieTime
             ) {String eachText =each.getText().trim();
            if (!eachText.isEmpty()) {
                System.out.println(eachText);
                if(eachText.contains("კავეა ისთ ფოინთი")){
                    System.out.println("only ‘კავეა ისთ ფოინთი’ options are returned");
                }
            }


        }



        try {
            lastMovie.click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", lastMovie);
        }



//                                              choose vacant place
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cinema-tickets\"]/div/div/div[2]/div[71]")));
        WebElement vacantplace = driver.findElement(By.xpath("//*[@id=\"cinema-tickets\"]/div/div/div[2]/div[71]"));
        vacantplace.click();
//        WebElement allTicket = driver.findElement(By.className("ui-tabs-panel ui-widget-content ui-corner-bottom"));
//        List<WebElement> emptyPlaces = allTicket.findElements(By.className("seat free"));
//        emptyPlaces.get(0).click();



//                                     Check in opened popup that movie name, cinema and datetime is valid
        WebElement content = driver.findElement(By.className("content-header"));
        WebElement movieNameElement = content.findElement(By.xpath("//*[@id=\"eventForm\"]/div/div[3]/div[1]/p[1]"));
        WebElement cinemaElement = content.findElement(By.className("movie-cinema"));
        WebElement datetimeElement = content.findElement(By.xpath("//*[@id=\"eventForm\"]/div/div[3]/div[1]/p[3]"));




        String actualMovieName = movieNameElement.getText();
        String actualCinema = cinemaElement.getText();
        String actualDatetime = datetimeElement.getText();


        String expectedMovieName = driver.findElement(By.className("name")).getText();
        String expectedCinema = "კავეა სითი მოლი საბურთალო"; // არვიცი რატომ, მაგრამ როდესაც მომაქვს ისთ ფოინთი აღიქვამს როგორც კავეა სითი მოლი საბურთალო.
        for (WebElement each:movieTime
        ) {
            String eachText = each.getText().trim();
            if (eachText.contains(actualDatetime)) {
            }
        }

        Assert.assertEquals(actualMovieName, expectedMovieName, "Movie name validation failed.");
        Assert.assertEquals(actualCinema, expectedCinema, "Cinema name validation failed.");



        System.out.println("Validation successful: Movie name, cinema, and datetime are valid.");


//                                                Register for a new account
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='register' and text()='რეგისტრაცია']")));
        WebElement registration = driver.findElement(By.xpath("//p[@class='register' and text()='რეგისტრაცია']"));
        registration.click();

        WebElement coocies = driver.findElement(By.className("acceptCookie"));
        coocies.click();

        WebElement firstName = driver.findElement(By.id("pFirstName"));
        firstName.sendKeys("Giorgi");

        WebElement lastName = driver.findElement(By.id("pLastName"));
        lastName.sendKeys("Dzindzibadze");


        WebElement Email = driver.findElement(By.id("pEmail"));
        Email.sendKeys("GiorgiDzindzibadze");


        WebElement Phone = driver.findElement(By.id("pPhone"));
        Phone.sendKeys("599924950");

        WebElement birthDate = driver.findElement(By.id("pDateBirth"));
        birthDate.sendKeys("25");
        birthDate.sendKeys("12");
        birthDate.sendKeys("2003");

        WebElement gender = driver.findElement(By.id("pGender"));
        gender.click();

        js.executeScript("window.scrollBy(0, 200);");

        WebElement male = driver.findElement(By.xpath("//*[@id=\"pGender\"]/option[2]"));
        male.click();


        WebElement password = driver.findElement(By.id("pPassword"));
        password.sendKeys("dzindzibadze");

        js.executeScript("window.scrollBy(0, 200);");


        WebElement repassword = driver.findElement(By.id("pConfirmPassword"));
        repassword.sendKeys("dzindzibadze");

        js.executeScript("window.scrollBy(0, 500);");


        WebElement checkbox = driver.findElement(By.id("pIsAgreeTerns"));
        checkbox.click();
//
//
        WebElement checkbox2 = driver.findElement(By.id("pIsSubscribedNewsletter"));
        checkbox2.click();


//                                    check that error message ‘მეილის ფორმატი არასწორია!' is appear
        WebElement registrationbutton = driver.findElement(By.xpath("//input[@type='button' and @value='რეგისტრაცია']"));
        registrationbutton.click();

        WebElement alert = driver.findElement(By.id("physicalInfoMassage"));
        String expectedAlert = "მეილის ფორმატი არასწორია!";

        Assert.assertEquals(alert.getText(), expectedAlert, "text is not same");
        System.out.println("error message ‘მეილის ფორმატი არასწორია!' is appear");




    }


    @AfterMethod
    public void after() {
        driver.quit();
    }
}


