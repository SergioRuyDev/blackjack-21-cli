package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import src.enums.Suits;
import src.model.Cards;
import src.model.Deck;
import src.model.Players;
import src.service.InputProvider;
import src.service.OutputProvider;
import src.service.impl.GameLogicImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameLogicImplTest {


    @Mock
    private InputProvider inputProvider;

    @Mock
    private OutputProvider outputProvider;

    @Mock
    private Deck newDeck;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    private GameLogicImpl gameLogic;
    private Players you;
    private Players dealer;
    private String playerName = "testPlayer";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameLogic = new GameLogicImpl("testPlayer", inputProvider, outputProvider);
        stringCaptor = ArgumentCaptor.forClass(String.class);
        you = mock(Players.class);
        dealer = mock(Players.class);

    }

    @DisplayName("JUnit test to check if constructor initialize the objects correct")
    @Test
    void givenValidValuesAndTestGameLogicImplConstructor() {
        assertEquals(playerName, gameLogic.getPlayerName());
        assertEquals(100, gameLogic.getBalance(), 0.001);
        verify(outputProvider, times(3)).print(anyString());
    }

    @DisplayName("JUnit test to check if playGame method work as expected")
    @Test
    public void givenInputToConsoleAndTestPlayGame() {
        when(inputProvider.getNext()).thenReturn("E"); // Simulate user choosing to end the game

        gameLogic = new GameLogicImpl(playerName, inputProvider, outputProvider);

        gameLogic.playGame();

        verify(outputProvider, times(1)).print("\n" + playerName + ", Do you want to DEAl or END " +
                "the game [D or E]??");
        verify(outputProvider, times(1)).print("\n" + playerName + ", Game Ended!!!");
    }

    @DisplayName("JUnit test to check if playGame end the game when invalid input")
    @Test
    void givenInvalidInputAndTestPlayGame() {
        when(inputProvider.getNext()).thenReturn("invalid"); // Simulate user choosing invalid option

        gameLogic.playGame();

        verify(outputProvider, times(1)).print("\n" + playerName + ", Do you want to DEAl or END " +
                "the game [D or E]??");
        verify(outputProvider, times(1)).print("\n" + playerName + ", Game Ended!!!");
    }

    @DisplayName("JUnit test to check if dealTheGame method work as expected when a valid bet is placed")
    @Test
    public void givenValidBetAndTestDealTheGame() {
        float validBet = 10.0f;
        String playerResponse = "E"; // Assuming "E" to end the play

        // interactions
        when(inputProvider.getNextFloat()).thenReturn(validBet);
        when(inputProvider.getNext()).thenReturn(playerResponse);

        gameLogic = new GameLogicImpl(playerName, inputProvider, outputProvider);

        gameLogic.dealTheGame();

        // Check deducted from the balance
        assertEquals(gameLogic.getBalance(), 100 - validBet, 0.001);
        assertEquals(90, gameLogic.getBalance());
        verify(inputProvider, times(1)).getNextFloat();
        verify(inputProvider, atLeastOnce()).getNext();
        verify(outputProvider, atLeastOnce()).print(anyString());
    }

    @DisplayName("JUnit test to check if dealTheGame method works as expected when exceeds the balance")
    @Test
    void givenInvalidBetAndTestDealTheGame() {
        float invalidBet = 200.0f;

        when(inputProvider.getNextFloat()).thenReturn(invalidBet);

        gameLogic = new GameLogicImpl(playerName, inputProvider, outputProvider);

        gameLogic.dealTheGame();

        assertEquals(gameLogic.getBalance(), 100.0f, 0.001);

        verify(inputProvider, times(1)).getNextFloat();
        verify(outputProvider, atLeastOnce()).print(stringCaptor.capture());

        assertTrue(stringCaptor.getAllValues().stream()
                .anyMatch(val -> val.contains("Your bet amount is wrong, it should be a natural number and should not " +
                        "exceed your balance")));
    }

    @DisplayName("JUnit test for chack if the checkIfBlackJack method work as expected.")
    @Test
    void givenLessThan21AndTestCheckIfNotBlackJack() {

        you.addCardToPlayersHand(new Cards(Suits.CLUBS, 10));
        you.addCardToPlayersHand(new Cards(Suits.DIAMONDS, 10));

        dealer.addCardToPlayersHand(new Cards(Suits.HEARTS, 10));
        dealer.addCardToPlayersHand(new Cards(Suits.SPADES, 9));

        gameLogic.setYou(you);
        gameLogic.setDealer(dealer);

        assertFalse(gameLogic.checkIfBlackJack());

        dealer.emptyHand();
        dealer.addCardToPlayersHand(new Cards(Suits.HEARTS, 10));
        dealer.addCardToPlayersHand(new Cards(Suits.SPADES, 10));

        assertFalse(gameLogic.checkIfBlackJack());
    }

    @DisplayName("JUnit test to check if the yourPlay method work the Hit as expected.")
    @Test
    void givenValidInputAndTestYourPlayHit() {
        when(inputProvider.getNext()).thenReturn("H");
        gameLogic.setBalance(10);
        gameLogic.setBet(5);
        gameLogic.setDoubleDownAllowed(true);

        gameLogic.yourPlay();

        verify(outputProvider).print("Hit or Stay or Double Down? [Enter H or S or DD]");
        verify(outputProvider).print("You entered: H");
        verify(outputProvider, never()).print("Hit or Stay? [Enter H or S(or press any letter to Stay)]");
    }

    @DisplayName("JUnit test to check if the yourPlay method work the Double Down as expected.")
    @Test
    void givenValidInputAndTestYourPlayDoubleDown() {
        when(inputProvider.getNext()).thenReturn("DD");
        gameLogic.setBalance(10);
        gameLogic.setBet(5);
        gameLogic.setDoubleDownAllowed(true);

        gameLogic.yourPlay();

        verify(outputProvider).print("Hit or Stay or Double Down? [Enter H or S or DD]");
        verify(outputProvider).print("You entered: DD");
        verify(outputProvider, never()).print("Hit or Stay? [Enter H or S(or press any letter to Stay)]");
    }

    @DisplayName("JUnit test to check if the yourPlay method work the Stay as expected.")
    @Test
    void givenValidInputAndTestYourPlayStay() {
        when(inputProvider.getNext()).thenReturn("S");
        gameLogic.setBalance(10);
        gameLogic.setBet(5);
        gameLogic.setDoubleDownAllowed(false);

        gameLogic.yourPlay();

        verify(outputProvider).print("Hit or Stay? [Enter H or S(or press any letter to Stay)]");
        verify(outputProvider).print("You entered: S");
    }

    @DisplayName("JUnit test to check if the method Stay work as expected.")
    @Test
    void givenValidMethodStayAndReturnTheString() {
        gameLogic.stay();

        verify(outputProvider).print("\tYou Choose to Stay, Dealer's turn \n");
        assertTrue(gameLogic.isYouDone());
    }


}
