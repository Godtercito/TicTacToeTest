/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.codeurjc.ais.tictactoe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author adrian
 */
public class SystemTest {

    protected WebDriver driver1, driver2;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
        WebApp.start();
    }

    @AfterClass
    public static void teardownClass() {
        WebApp.stop();
    }

    @Before
    public void setupTest() {
        driver1 = new FirefoxDriver();
        driver2 = new FirefoxDriver();

        driver1.get("http://localhost:8080");
        driver2.get("http://localhost:8080");

        driver1.findElement(By.id("nickname")).sendKeys("Jugador1");
        driver2.findElement(By.id("nickname")).sendKeys("Jugador2");

        driver1.findElement(By.id("startBtn")).click();
        driver2.findElement(By.id("startBtn")).click();

        waitForBoard();
    }
    @After
    public void teardown() {
        if (driver1 != null) {
            driver1.quit();
        }
        if (driver2 != null) {
            driver2.quit();
        }
    }


    private void movimientoJ1(int cell){
        String celda = "cell-" + cell;
        driver1.findElement(By.id(celda)).click();
    }

    private void movimientoJ2(int cell){
        String celda = "cell-" + cell;
        driver2.findElement(By.id(celda)).click();
    }

    private void waitForBoard(){
        //Es coveniente esperar a que el tablero esta cargado para lograr mayor consistencia en la ejecucion de los test
        WebDriverWait wait1 = new WebDriverWait(driver1,10);
        WebDriverWait wait2 = new WebDriverWait(driver2,10);

        wait1.until(ExpectedConditions.elementToBeClickable(By.id("cell-0")));
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("cell-0")));
    }

    private void waitForAlert(){
        WebDriverWait wait1 = new WebDriverWait(driver1,10);
        WebDriverWait wait2 = new WebDriverWait(driver2,10);

        wait1.until(ExpectedConditions.alertIsPresent());
        wait2.until(ExpectedConditions.alertIsPresent());
    }

    @Test
    public void testSistemaVictoria1() throws Exception {
        movimientoJ1(0);
        movimientoJ2(3);

        movimientoJ1(1);
        movimientoJ2(4);

        movimientoJ1(2); //Con esta jugada gana J1

        waitForAlert();

        assertEquals("El ganador deberia ser el jugador1","Jugador1 wins! Jugador2 looses.",driver1.switchTo().alert().getText());
        assertEquals("El ganador deberia ser el jugador1","Jugador1 wins! Jugador2 looses.",driver2.switchTo().alert().getText());
    }

    @Test
    public void testSistemaVictoria2() throws Exception {
        movimientoJ1(0);
        movimientoJ2(3);

        movimientoJ1(1);
        movimientoJ2(4);

        movimientoJ1(6);
        movimientoJ2(5); //Con esta jugada gana J2

        waitForAlert();

        assertEquals("El ganador deberia ser el jugador2","Jugador2 wins! Jugador1 looses.",driver1.switchTo().alert().getText());
        assertEquals("El ganador deberia ser el jugador2","Jugador2 wins! Jugador1 looses.",driver2.switchTo().alert().getText());
    }

    @Test
    public void testSistemaEmpate() throws Exception {
        movimientoJ1(4);
        movimientoJ2(0);

        movimientoJ1(6);
        movimientoJ2(2);

        movimientoJ1(1);
        movimientoJ2(7);

        movimientoJ1(3);
        movimientoJ2(5);

        movimientoJ1(8); //Con esta jugada se produce un empate

        waitForAlert();

        assertEquals("No se ha detectado correctamente el empate","Draw!",driver1.switchTo().alert().getText());
        assertEquals("No se ha detectado correctamente el empate","Draw!",driver2.switchTo().alert().getText());
    }

}
