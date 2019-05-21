/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.codeurjc.ais.tictactoe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adrian
 */
public class BoardTest {

    private Board board;
    
    @Before
    public void setUp() {
        //GIVEN
        board = new Board();
    }
    
    @After
    public void tearDown() {
    }

    private void movimientoJ1(int cell){
        board.getCell(cell).value = "X";
        board.getCell(cell).active = false;
    }
    
    private void movimientoJ2(int cell){
        board.getCell(cell).value = "O";
        board.getCell(cell).active = false;
    }
    
    @Test
    public void testBoardVictoria1() {
        //WHEN
        movimientoJ1(0);
        movimientoJ2(3);
        movimientoJ1(1);
        movimientoJ2(4);
        movimientoJ1(2); //Victoria J1: linea ganadora {0,1,2}
        
        //THEN
        int[] cells = {0,1,2};
        Assert.assertArrayEquals("El ganador deberia ser el jugador1",board.getCellsIfWinner("X"),cells);
    }

    @Test
    public void testBoardVictoria2() {
        //WHEN
        movimientoJ1(0);
        movimientoJ2(6);
        movimientoJ1(1);
        movimientoJ2(4);
        movimientoJ1(8); 
        movimientoJ2(2); //Victoria J2: linea ganadora {6,4,2}
        
        //THEN
        int[] cells = {6,4,2};
        Assert.assertArrayEquals("El ganador deberia ser el jugador2",board.getCellsIfWinner("O"),cells);
    }

    @Test
    public void testBoardEmpate() {
        //WHEN
        movimientoJ1(4);
        movimientoJ2(0);
        movimientoJ1(3);
        movimientoJ2(7);
        movimientoJ1(1); 
        movimientoJ2(2);
        movimientoJ1(6); 
        movimientoJ2(5); 
        movimientoJ1(8); //Se produce un empate
        
        //THEN
        assertTrue("No se ha detectado correctamente el empate",board.checkDraw());
    }
}
