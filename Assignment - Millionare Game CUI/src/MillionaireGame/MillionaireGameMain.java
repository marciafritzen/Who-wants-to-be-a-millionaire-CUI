/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireGame;


import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Paul
 */
public class MillionaireGameMain {
    
       public static void main(String[] args) throws IOException {
        System.out.println("=========================\n   WHO WANTS TO BE A MILLIONAIRE  \n=============================");
        System.out.println("INSTRUCTIONS: 1) For choosing answers please input the integer 'OPTION' corresponding to your answer\n       "
                            +"       2) You may use the 50/50 lifeline up to 3 times \n"
                            + "      3) You may also pass on a question 1 time, this will give you a new question");

                
        System.out.println("RULES: In Who wants to be a millionaire there are 2 safe havens and 15 questions");
        System.out.println(" - If the player answers any question incorrectly the game will end");
        System.out.println(" - However if user answers 5 question correctly the player is gaurenteed $1000");
        System.out.println("   and at 10 questions answer correctly player will be gaurenteed to walk away with $32000 ");
        System.out.println(" - question will get more diffcult as the player progresses");

        

        Player player = new Player("", 0);
        Scanner scan = new Scanner(System.in);
        QuestionsArray questionList = new QuestionsArray();
        PlayerArray playerArray = new PlayerArray(); 
        QuestionReader.loadQuestions(questionList);
        
        //randomises questions
        //Collections.shuffle(questionList.getQuestionsArray());
        
        PlayerDataWriter.loadPlayers(playerArray);
        
        Game game = new Game(playerArray,questionList,player);  
        game.StartGame(scan);
    }
}
