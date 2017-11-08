package com.exampel.myfirstapp;

import java.util.Random;

/**
 * Created by alvaro on 2017-10-17.
 * a class of Hangman
 */

public class Hangman {

    private String[] allWords;
    private String badLettersUsed;
    private String word;
    private boolean[] visible;
    private int triesLeft;
    private char[] hiddenWord;
    private Random number;

    /**
     * A constructor for Hangman
     * @param allWords
     */
    public Hangman(String[] allWords) {
        this.allWords = allWords;
        number = new Random();
        newWord();
        hiddenWord = new char[word.length()];
        visible = new boolean[word.length()];
        triesLeft = 10;
        badLettersUsed = "";

        for(int i = 0; i < getRealWord().length(); i++)
            hiddenWord[i] = '-';

    }

    /**
     * A constructor for hangman with following parameters:
     * @param word
     * @param badLettersUsed
     * @param triesLeft
     * @param hiddenWord
     * @param visible
     */

    public Hangman(String word, String badLettersUsed, int triesLeft, char[] hiddenWord, boolean[] visible) {

        this.badLettersUsed = badLettersUsed;
        this.triesLeft = triesLeft;
        this.word = word;
        this.hiddenWord = hiddenWord;
        this.visible = visible;

    }

    /**
     * Method to pick a word from availabe words
     */
    public void newWord() {
        int nr = number.nextInt(allWords.length);
        word = allWords[nr];

    }


    /**
     * Method for hiding the letters that the user hasnt't guessed yet
     *
     * @return returns the currnt word
     */


    public String getHiddenWord() {

        for (int i = 0; i < word.length(); i++) {

            if(visible[i])
                hiddenWord[i] = word.charAt(i);

        }
        return String.valueOf(hiddenWord);
    }


    /**
     * Makes a guess for a letter. If letter is in word, the letter is marked as "complete" and the letter is saved.
      If letter is not in word, the number of guesses left will be decreased and the letter is saved:
     * @param c is the user input
     */

    public void guess(char c) {

        boolean hit = false;

        for (int i = 0; i < word.length(); i++) {

            if(c == word.charAt(i)) {
                hit = true;
                visible[i] = true;
            } else {
                visible[i] = false;
            }
        }

        if(!hit) {

            if (badLettersUsed.length() < 1)
                badLettersUsed = String.valueOf(c);
            else
                badLettersUsed = badLettersUsed + ", " + String.valueOf(c);

            triesLeft--;
        }
    }

    /**
     * Checks to see if the supplied char has been guessed for already
     * @param c
     * @return Returns true if letter has been used already, false if letter is free
     */

    public boolean hasUsedLetter(char c) {

        boolean isLetterUsed = false;

        for (int i = 0; i < badLettersUsed.length(); i++) {
            if (c == badLettersUsed.charAt(i)) {
                isLetterUsed = true ;
            }
        }
        for (int i = 0; i < getHiddenWord().length(); i++) {
            if (c == getHiddenWord().charAt(i)) {
                isLetterUsed = true;
            }
        }
        return isLetterUsed;
    }


    /**
     * Checks to see if the user has guessed all letters correctly
     * @return Returns true if user has all letters correctly guessed, false if not.
     */

    public boolean hasWon() {

        boolean hasWon = false;

        for (int i = 0; i < getHiddenWord().length(); i++) {

            if(getHiddenWord().charAt(i) == '-') {
                hasWon = false;
                break;
            } else {
                hasWon = true;
            }
        }

        return hasWon;

    }

    /**
     * Checks to see if the user has used up all her guesses
     * @return
     * Returns true if user has lost(has no guesses left), false if the opposite
     */

    public boolean hasLost() {

        if(getTriesLeft() == 0)
            return true;
        else
            return false;
    }


    /**
     *
     * @return Returns a String with all wrong guesses the user has made
     */

    public String getBadLettersUsed() {
        return badLettersUsed;

    }

    /**
     *
     * @return Returns a boolean array
     */
    public boolean[] getVisible() {
        return visible;
    }

    /**
     *
     * @return Returns a word that user has to guess
     */
    public String getRealWord() {
        return word;
    }

    /**
     *
     * @return Returns the amount of tries the user has laft
     */
    public int getTriesLeft() {
        return triesLeft;
    }

    /**
     *
     * @param input is user input
     *
     * @return Returns true if user input is a character and false if not
     */
    public boolean isLetter(String input) {

        return input.matches("[a-zA-Z]+");


    }

}
