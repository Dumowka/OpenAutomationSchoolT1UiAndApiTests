package ui.tests;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import ui.common.DriverConfiguration;
import ui.common.UIProperties;
import ui.pages.MainPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractTest {

    protected static final UIProperties properties = ConfigFactory.create(UIProperties.class);

    protected MainPage mainPage;

    @BeforeAll
    void globalBeforeAll() {
        new DriverConfiguration().configureDriver(properties);
        open("/");
        mainPage = new MainPage();
    }

    @AfterAll
    void cleanupTest() {
        closeWebDriver();
    }
}
