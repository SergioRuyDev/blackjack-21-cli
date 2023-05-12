package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.model.Deck;
import src.model.Players;
import src.service.GameLogicImpl;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class GameLogicImplTest {


    private GameLogicImpl gameLogic;
    @Mock
    private Deck mockDeck;
    @Mock
    private Players mockPlayer;
    @Mock
    private Scanner mockScanner;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        gameLogic = new GameLogicImpl("test player");
//        gameLogic.setNewDeck(mockDeck);
//        gameLogic.setYou(mockPlayer);
//        gameLogic.setDealer(mockPlayer);
//        gameLogic.setScanner(mockScanner);
//    }
//
//
//    @Test
//    void testCheckIfBlackJack_whenPlayerScoreIs21_returnTrue() {
//        when(mockPlayer.getPlayersHandTotal()).thenReturn(21);
//        assertTrue(gameLogic.checkIfBlackJack());
//        assertTrue(gameLogic.isYouDone());
//        assertTrue(gameLogic.isDealerDone());
//    }
}
