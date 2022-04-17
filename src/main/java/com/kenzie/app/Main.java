package com.kenzie.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;
import static com.kenzie.app.CluesHttpClient.getCluesList;

public class Main {
    private static final String cluesURL = "https://jservice.kenzie.academy/api/clues";

    public static void main(String[] args) throws IOException {
        try {
            String catTitle;
            String question;
            String answer;
            String input;
            int playerPoints = 0;
            Scanner scan = new Scanner(System.in);
            int index;

            String response = CluesHttpClient.sendGET(cluesURL);
            List<Clue> listOfClues = getCluesList(response);
            List<Clue> listOfTenClues = CluesHttpClient.generateRandomIndicesFromList(listOfClues);

            try {
                displayMenu();
                for (index = 0; index <= listOfTenClues.size() - 1; index ++) {
                    //looping through the ten random clues to grab trivia elements
                    catTitle = listOfTenClues.get(index).getCategory().getTitle();
                    question = listOfTenClues.get(index).getQuestion();
                    answer = listOfTenClues.get(index).getAnswer();

                    //displaying elements pertinent to game
                    System.out.println(
                            "Current score : " + playerPoints + "\n"
                            + "CATEGORY: " + catTitle + "\n"
                            + "Question: " + question + "?");

                    input = scan.nextLine();

                    if(checkEmptyString(input)) { //check empty string call and move to next question... they only get one chance!
                        System.out.println("You didn't enter anything... OH WELL!" + "\n");
                        continue;
                    }

                    if(checkForRightAnswer(input, answer)) { //check formatted input against answer element
                        System.out.println("CORRECT!" + "\n");
                        playerPoints++;
                    }
                    else {
                        System.out.println("False! The correct answer is: " + answer + "\n");
                        //display correct answer on wrong input while not adding to total player points
                    }

                    if (listOfTenClues.size() - 1 == index) { //end game displaying end string and total points printout
                        System.out.println("THANKS FOR PLAYING!");
                        System.out.println("You're total score was: " + playerPoints);
                    }
                }
                    System.out.println("THANKS FOR PLAYING!");
                    System.out.println("You're total score was: " + playerPoints);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkEmptyString(String input) {
        return input.equals("");
    }

    //Done: check the wrong answer
    public static boolean checkForRightAnswer(String input, String answer) {
        boolean isTrue = false;
        input = input.toLowerCase(Locale.ROOT);
        answer = answer.toLowerCase(Locale.ROOT);

        String inputNoPunctuation = input.replaceAll("[.,?!:]", "");
        String inputNoExtraSpaces = inputNoPunctuation.replaceAll("  ", " ");
        String inputFormatted = inputNoExtraSpaces;

        if(inputFormatted.equals(answer)) {
            isTrue = true;
        }
        return isTrue;
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println("************ Welcome To Trivia Night! ************");
        System.out.println();
        System.out.println("You will be asked a total of 10 questions");
        System.out.println("Good luck!" + "\n");
    }
}

