# Command line Blackjack Game in Java
====================================

## Description of Project
=========================

Game develop for playing only with command line.

# Requirements and steps for run the program
============================================

Requisite: Minimum Java 8+ installed.


## Functionalities
==================

:bell: Bet amount should not be greater than balance ( 100 or whatever (winning condition)).

:bell: Bet amount should be a natural number (>0).

:bell: If balance is 0, game should end.

:bell: Check every inputMismatch exception.

:bell: After bet has been placed, balance = balance – bet.

:bell: Check  balance>=bet,  for showing Double down option.

:bell: Double down option should not be shown,  after first Hit during a Deal.

:bell: At every point,  bet + balance = initial balance before dealing a hand.

:bell: BlackJack  or Natural 21 is calculated for two card hand, for every Deal.

:bell: Total of cards number in hand is correct.

:bell: Queen, King, Jack counted as 10.

:bell: Ace is counted as 11 or 1.

:bell: if 2 Ace, one should count as 1.

:bell: Whenever total of a hand goes above 21 for a player, Ace is counted as one.

:bell: Dealer's turn comes, after Player finishes.

:bell: Check Player's and Dealer's BlackJack (initial hand total == 21).

:bell: Check Bust condition (total of hand > 21).

:bell: Dealer hits until < 17, stays >= 17.

:bell: Decide winner-- comparing totals of both the players if total of both is less than equal to 21.

:bell: if one player busts, other automatically wins.

:bell: if total of both players is equal, its a push (even if, initial hands of both totals to 21).

:bell: In case of Double Down chosen by The Player, draw only one card.


# Inform notes:
=====

•	A four deck shoe is used.
•	Each chip is $1.
•	The player may double down on any initial two card hand.

# Tech information:
======

The game was written in java and the libs used for Unit tests was:
* Java 17
* JUnit 5
* Mockito 
