/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

/**
 *
 * @author adrian
 */
public class TestConDobles {
    
    private TicTacToeGame game;
    private Player p0, p1;
    private Connection jugador1, jugador2;
    private List<Object> lista;
    private ArgumentCaptor<Object> jugadores;
    
    
    @Before
    public void setUp() {
        //GIVEN
        game = new TicTacToeGame();
        jugador1 = mock(Connection.class);
        jugador2 = mock(Connection.class);
        game.addConnection(jugador1);
        game.addConnection(jugador2);
        game.addPlayer(new Player(1,"X","Jugador1"));
        p0 = game.getPlayers().get(0);
        
        lista = new ArrayList();
        lista.add(p0);
        
        reset(jugador1); //Reset para empezar a comprobar las llamadas desde este momento
        reset(jugador2);
        
        jugadores = ArgumentCaptor.forClass(Object.class);
    }
    

    private void checkJoinGame(){
        //WHEN
        game.addPlayer(new Player(2,"O","Jugador2"));
        p1 = game.getPlayers().get(1);
        lista.add(p1);
        //THEN
        verify(jugador1).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME), jugadores.capture());
        
        verify(jugador2).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME), jugadores.capture());
        
        reset(jugador1);
        reset(jugador2);
    }
    
    private void checkPlay1(int mark){
        //WHEN
        game.mark(mark);
        //THEN
        verify(jugador1).sendEvent(TicTacToeGame.EventType.SET_TURN, p1);
        verify(jugador2).sendEvent(TicTacToeGame.EventType.SET_TURN, p1);
        
        reset(jugador1);
        reset(jugador2);
    }
    
    private void checkPlay2(int mark){
        //WHEN
        game.mark(mark);
        //THEN
        verify(jugador1).sendEvent(TicTacToeGame.EventType.SET_TURN, p0);
        verify(jugador2).sendEvent(TicTacToeGame.EventType.SET_TURN, p0);
        
        reset(jugador1);
        reset(jugador2);
    }
    
    /**
     * Test of main method, of class WebApp.
     */
    @Test
    public void testDoblesVictoriaJ1() {
        checkJoinGame();
        
        checkPlay1(0);
        
        checkPlay2(3);
        
        checkPlay1(1);
        
        checkPlay2(4);
        
        //WHEN
        game.mark(2); //Con esta jugada gana el jugador 1
        
        //THEN
        ArgumentCaptor<WinnerValue> ganador = ArgumentCaptor.forClass(WinnerValue.class);
        verify(jugador1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), ganador.capture());
        assertEquals("El ganador deberia ser el jugador1",ganador.getValue().player,p0);
        verify(jugador2).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), ganador.capture());
        assertEquals("El ganador deberia ser el jugador1",ganador.getValue().player,p0);   
    }
    
    
    @Test
    public void testDoblesVictoriaJ2() {
        checkJoinGame();
        
        checkPlay1(0);
        
        checkPlay2(3);
        
        checkPlay1(1);
        
        checkPlay2(4);
        
        checkPlay1(7);
        
        //WHEN
        game.mark(5); //Con esta jugada gana el jugador 2
        //THEN
        ArgumentCaptor<WinnerValue> ganador = ArgumentCaptor.forClass(WinnerValue.class);
        verify(jugador1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), ganador.capture());
        assertEquals("El ganador deberia ser el jugador2",ganador.getValue().player,p1);
        verify(jugador2).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), ganador.capture());
        assertEquals("El ganador deberia ser el jugador2",ganador.getValue().player,p1);   
    }

    
    @Test
    public void testDoblesEmpate() {
        checkJoinGame();
        
        checkPlay1(4);
        
        checkPlay2(0);
        
        checkPlay1(6);
        
        checkPlay2(2);
        
        checkPlay1(1);
        
        checkPlay2(7);
        
        checkPlay1(3);
        
        checkPlay2(5);
        
        //WHEN
        game.mark(8); //Con esta jugada se produce un empate
        //THEN
        assertTrue("No se ha detectado correctamente el empate",game.checkDraw());  
    }
    
}
