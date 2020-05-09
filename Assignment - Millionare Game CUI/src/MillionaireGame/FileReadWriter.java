package MillionaireGame;


import MillionaireGame.Player;
import MillionaireGame.PlayerArray;
import MillionaireGame.Question;
import MillionaireGame.QuestionsArray;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Paul Vu 17981406, Peter Ho 17978553
 */
public class FileReadWriter {
    //purpose of this class is to Read from the questions.txt file create new question class instaces and add these questions to the question arraylist

    public static final int NUM_OF_ANSWERS = 5;
    
    public static void loadQuestions(QuestionsArray questions) throws IOException {
        try {
            String[] answers = new String[NUM_OF_ANSWERS];
            String question;
            String correctAnswer;
            BufferedReader bufferReader;
            Scanner scanner;
            
            try (FileReader fileScan = new FileReader("src/MillionaireGame/Questions.txt")) {
                
                bufferReader = new BufferedReader(fileScan);
                scanner = new Scanner(bufferReader);
                
                do {
                    //questions saved in the question.txt file are ordered in the a certain way
                    //first line is the question itself
                    //second line is the correct answer to the question
                    //following next 4 lines are the possible answer for the player to guess from and repeating
                    
                    question = scanner.nextLine();
                    correctAnswer = scanner.nextLine();
                    int cAnswer = Integer.parseInt(correctAnswer);
                    
                    //loop reads 4 possible answers of question 
                    for (int four_ans = 0; four_ans < answers.length-1 ; four_ans++) {
                        answers[four_ans] = scanner.nextLine();
                    }
                    //sets the final string of the array to the lifeline option
                    answers[4] = "5) View Lifelines";
                    
                    //creates new question
                    Question q = new Question(question, answers, cAnswer);
                    questions.addToQuestionArray(q);
                    
                    answers = new String[NUM_OF_ANSWERS];
                } while (scanner.hasNext());
            }
            bufferReader.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadPlayers(PlayerArray players) throws IOException {
        try {
            BufferedReader bufferReader;
            Scanner scan;
            try (FileReader fileScan = new FileReader("src/MillionaireGame/PlayerData.txt")) {
                bufferReader = new BufferedReader(fileScan);
                scan = new Scanner(bufferReader);

                while (scan.hasNext()) {
                    String name = scan.nextLine();
                    int money = Integer.parseInt(scan.nextLine());

                    Player aPlayer = new Player(name, money);
                    players.addPlayer(aPlayer);
                }
                fileScan.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
        
        public static void savePlayers(PlayerArray players) throws IOException {
        try {
            try (PrintWriter fileWriter = new PrintWriter(new FileWriter("src/MillionaireGame/PlayerData.txt"))) {
                for (Player player : players.getPlayerArray()) {
                    fileWriter.println(player.getPlayerName());
                    fileWriter.println(player.getTotalMoney());
                }
                fileWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
