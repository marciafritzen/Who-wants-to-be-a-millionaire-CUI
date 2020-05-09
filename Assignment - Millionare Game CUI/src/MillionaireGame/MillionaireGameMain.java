/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireGame;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Paul
 */
public class MillionaireGameMain {
    //this class holds main function which creates instaces
    
       public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n                 WHO WANTS TO BE A MILLIONAIRE  \n             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("INSTRUCTIONS: 1) For choosing answers please input the integer 'OPTION' corresponding to your answer\n"
                            +"              2) 3 lifelines each of which you can only use once \n"
                            + "              ");

                
        System.out.println("       RULES: In Who wants to be a millionaire there are 2 safe havens and 15 questions");
        System.out.println("               - If the player answers any question incorrectly the game will end");
        System.out.println("               - However if user answers 5 question correctly the player is gaurenteed $1000");
        System.out.println("                 and at 10 questions answer correctly player will be gaurenteed to walk away with $32000 ");
        System.out.println("               - Questions are ramdomised and do not get more difficult \n");

        

        Player player = new Player("", 0);
        Scanner scanner = new Scanner(System.in);
        
        PlayerArray playerArray = new PlayerArray(); 
        QuestionsArray questionList = new QuestionsArray();

        QuestionReader.loadQuestions(questionList);
        
        //randomises questions
        //Collections.shuffle(questionList.getQuestionsArray());
        
        PlayerDataWriter.loadPlayers(playerArray);
        
        Game game = new Game(playerArray,questionList,player);  
        game.StartGame(scanner);
    }
}
