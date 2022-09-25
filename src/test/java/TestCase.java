import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TestCase extends TestBase {

     /*
    Uçtan uca ödeme ekranına kadar Selenium’da java dili ile chrome browser kullanarak test otomasyon ödevi yapılacak.
    Ödeme ekranı doldurulmayacak. Aşağıdaki senaryoyu web ve mobil olmak üzere 2 çeşit oluşturabilirlerse çok iyi olur. En az Web’de yapmak zorunlu.
    - Senaryoya üye kaydı oluşturmadan devam edilecek.
    - Giyim--> Aksesuar--> Kadın İç Giyim-->Dizaltı Çorap bölümüne girilir.
    - Açılan ürünün siyah olduğu doğrulanır.
    - Sepete ekle butonuna tıklanır.
    - Sepeti Görüntüle butonuna tıklanır.
    - Sepeti Onayla butonuna tıklanır.
    - Üye olmadan devam et butonuna tıklanır.
    - Mail ekranı gelir.
    - Sonrasında adres ekranı gelir. Adres oluştur dedikten sonra ödeme ekranı gelir.
    - Siparişi tamamla butonuna tıklayarak, ödeme ekranına gidildiği ,doğru ekrana yönlendiklerini kontrol edecekler.
     */

    @Test
    public void a101TestCase() throws InterruptedException {

        // Kullanici a.101.com.tr sayfasina gider.
        driver.get("https://a101.com.tr");

        // Cookies kabul edilir.
        driver.findElement(By.xpath("//button[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll']")).
                click();

        // Kullanıcı "Giyim&Aksesuar" bolumune mouse'u getirerek bekler.
        WebElement giyimAksesuar = driver.findElement(By.xpath("(//a[@title='GİYİM & AKSESUAR'])[1]"));
        actions.moveToElement(giyimAksesuar).perform();

        // Kullanıcı "Kadın İç Giyim" bolumun altındaki "Dizaltı Çorap" bolumune tiklar.
        driver.findElement(By.xpath("//a[@title='Dizaltı Çorap']")).click();

        // Kullanıcı açılan sayfada ilk ürüne tıklar ve bu ürünün "SİYAH" olup olmadığını doğrular.
        driver.findElement(By.xpath("(//img[@class=' ls-is-cached lazyloaded'])[1]")).click();
        String actualUser = driver.findElement(By.xpath("//div[@class='selected-variant-text']")).getText();
        String expectedUser = "SİYAH";
        Assert.assertTrue(actualUser.contains(expectedUser));

        // Kullanıcı Sepete ekle butonuna tıklanır.
        driver.findElement(By.xpath("//button[@class='add-to-basket button green block with-icon js-add-basket']")).
                click();

        // Kullanıcı Sepeti Görüntüle butonuna tıklanır.
        driver.findElement(By.xpath("//a[@class='go-to-shop']")).click();
        Thread.sleep(1000);

        // Kullanıcı Sepeti Onayla butonuna tıklanır.
        driver.findElement(By.xpath("//a[@class='button green checkout-button block js-checkout-button']")).
                click();

        // Kullanıcı Üye olmadan devam et butonuna tıklanır.
        driver.findElement(By.xpath("//a[@title='ÜYE OLMADAN DEVAM ET']")).click();

        // Kullanıcı Mail ekranının seçilebildğini denetler.
        WebElement mailBox = driver.findElement(By.xpath("//input[@name='user_email']"));
        Assert.assertTrue(mailBox.isEnabled());

        // Kullanıcı Mail bilgilerini girer.
        driver.findElement(By.xpath("(//input[@class='js-form-input'])[2]")).
                sendKeys(faker.internet().emailAddress(), Keys.ENTER);

        // Kullanıcı adres ekranınının görünür olduğunu denetler.
        WebElement adresBox = driver.findElement(By.xpath("(//a[@class='new-address js-new-address'])[1]"));
        Assert.assertTrue(adresBox.isDisplayed());

        // Kullanıcı "Yeni adres oluştur" butonuna tıklar.
        driver.findElement(By.xpath("(//a[@class='new-address js-new-address'])[1]")).click();

        // Kullanıcı "Adres Başlığı" bilgilerini girer.
        WebElement adresBasligi = driver.findElement(By.xpath("//input[@name='title']"));
        actions.click(adresBasligi).sendKeys(faker.address().firstName()).sendKeys(Keys.TAB)
                .sendKeys(faker.name().firstName()).sendKeys(Keys.TAB)
                .sendKeys(faker.name().lastName()).sendKeys(Keys.TAB).
                sendKeys(faker.phoneNumber().phoneNumber()).perform();

        // Kullanıcı il seçer.
        driver.findElement(By.xpath("//select[@name='city']")).sendKeys("KONYA");
        Thread.sleep(2000);

        // Kullanıcı ilçe seçer.
        driver.findElement(By.xpath("//select[@name='township']")).sendKeys("SELÇUKLU");
        Thread.sleep(2000);

        // Kullanıcı mahalle seçer.
        driver.findElement(By.xpath("//select[@class='js-district']")).
                sendKeys("AKADEMİ MAH");

        // Kullanıcı adres bilgilerini girer.
        driver.findElement(By.xpath("//textarea[@name='line']")).
                sendKeys(faker.address().fullAddress() + Keys.PAGE_DOWN);

        // Kullanıcı "Kaydet" butonuna tıklar.
        driver.findElement(By.xpath("//button[@class='button green js-set-country js-prevent-emoji']")).click();

        // Kullanıcı kargo biilgilerini seçer.
        driver.findElement(By.xpath("(//div[@class='radio'])[4]")).click();
        Thread.sleep(2000);

        // Kullanıcı adres kaydedildikten sonra kullanıcı "Kaydet ve Devam Et" butonuna tıklar.
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();

        // Kullanıcı "Ödeme Seçenekleri" sayfasına geldiğini doğrular.
        WebElement paymentPage = driver.findElement(By.xpath("//*[text()='2. ÖDEME SEÇENEKLERİ']"));
        Assert.assertTrue(paymentPage.isDisplayed());
    }
}
