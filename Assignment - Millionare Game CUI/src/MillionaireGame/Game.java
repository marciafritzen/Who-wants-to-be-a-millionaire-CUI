package MillionaireGame;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Paul Vu 17981406, Peter Ho 
 * 
 */
public class Game {
    // this class handles playerArrays and questionArray classes to create millionaire game
    
    PlayerArray playerArray;
    QuestionsArray questionsArray;
    Player player;
    
    //condition for looping game
    boolean gameActive = true;
    
    boolean isValid;
    //life line bools
    boolean fiftyFiftyUsed = false;
    boolean PassUsed = false;
    boolean CallUsed = false;
    
    //keeps track of question and prize number
    int qCounter = 0;
    int prizeCount = 0;
    
    boolean isCorrect = true;
    
    //constructor
    public Game(PlayerArray playerList, QuestionsArray questionList, Player player) {
        this.playerArray = playerList;
        this.questionsArray = questionList;
        this.player = player;
    }

    /**
     * Starts the game
     */
    public void StartGame(Scanner scan) throws IOException, InterruptedException {
        System.out.println("Please enter your name(or if you are a returning player enter 'returning'): "); 
        player.setPlayerName(scan.nextLine().trim());
        
        //gets returning player
        if(player.getPlayerName().equals("returning")){
            //display players to choose from
            for(int i = 0; i < playerArray.getPlayerArray().size(); i ++){
                System.out.println((i + 1) +") " + playerArray.getPlayerArray().get(i).getPlayerName() + " Money Won by player:  $" + playerArray.getPlayerArray().get(i).getTotalMoney());
            }
            int userAns;
            try {
                System.out.println("\n Select your player save slot or enter '0' to go enter as a new player");
                userAns = scan.nextInt();
                
                if (this.isInputValid(userAns) == true && (userAns < playerArray.getPlayerArray().size())) {
                    switch (userAns) {
                        case 0:
                                System.out.println("Please enter your name: "); 
                                player.setPlayerName(scan.nextLine().trim());
                                playerArray.newPlayer(player, player.getPlayerName());
                            break;
                        default:
                           player = playerArray.getPlayerArray().get(userAns - 1);
                            break;
                    }
                } else {
                    System.out.println("Invalid Input, please input the  integer option corresponding to your answer");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please input a integer answer.");
                scan.next();
            }

        }
        
        //questionaire game loop
        do {
            System.out.println("This is a $" + player.getPrizes()[prizeCount + 1] + " dollar question");
           
            System.out.println("Input '0' to quit and leave with the money you have won so far ($:" + player.getPrizes()[this.getpCount()] + ")\n");
            System.out.println("Question : " + questionsArray.getQuestionsArray().get(this.getqCounter()));
            int userAns;
            try {
                userAns = scan.nextInt();
                if (this.isInputValid(userAns) == true) {
                    switch (userAns) {
                        case 5:
                            this.chooseLifeline();
                            break;
                        case 0:
                            walkAway(player, this.getqCounter());
                            break;
                        default:
                            this.checkAnswer(player, this.getqCounter(), userAns);
                            break;
                    }

                } else {
                    System.out.println("Invalid Input, please input the  integer option corresponding to your answer carefully");

                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please input a integer answer.");
                scan.next();
            }

        } while (this.getGameActive());
    }

    //walk away method ends the game and writes player data to playerdata.txt
    public void walkAway(Player player, int counter) throws IOException {
        playerArray.removeExistingPlayer(player);
        player.setTotalMoney(player.getTotalMoney() + player.getPrizes()[counter]);
        System.out.println("You decided to walk away with $:" + player.getPrizes()[counter]);
        System.out.println("Player: " + player.toString());
        playerArray.getPlayerArray().add(new Player(player.getPlayerName(), player.getTotalMoney()));
        PlayerDataWriter.savePlayers(playerArray);
        exit(0);
    }

    //method is only for the final question of the game and total prize of 1million dollars
    public void correctFinalAnswer(Player player, int counter) throws IOException {
        playerArray.removeExistingPlayer(player);
        player.setTotalMoney(player.getTotalMoney() + player.getPrizes()[counter + 1]);
        System.out.println("Correct Answer");
        System.out.println("------------------------------------");
        System.out.println("CONGRATULATIONS! that was the final question you now have $:" + player.getPrizes()[counter + 1]);
        System.out.println("Player: " + player.toString());
        playerArray.getPlayerArray().add(new Player(player.getPlayerName(), player.getTotalMoney()));
        PlayerDataWriter.savePlayers(playerArray);
        exit(0);
    }

    //incorrect answer ends the game and saves player array
    public void incorrectAnswer(Player player, int counter) throws IOException {
        playerArray.removeExistingPlayer(player);        
        System.out.println("Incorrect Answer");
        System.out.println("------------------------------------");
        System.out.println("Game over you lose all of your winnings $" + player.getPrizes()[counter]);
        
        if(counter > 4 ){
            System.out.println("Game over you lose but you did reach the first safe haven therefore you walk away with $1000");
            player.setTotalMoney(1000);
        }
        if(counter > 9 ){
            System.out.println("Game over you lose but you did reach the first safe haven therefore you walk away with $32,000");
            player.setTotalMoney(32000);
        }
        
        System.out.println("Player: " + player.toString());
        playerArray.getPlayerArray().add(new Player(player.getPlayerName(), player.getTotalMoney()));
        PlayerDataWriter.savePlayers(playerArray);
    }

    //check if answer is correct
    public void checkAnswer(Player player, int qcounter, int userAns) throws IOException {
        if (questionsArray.getQuestionsArray().get(qcounter).getCorrectAnswer() == userAns) {
            if (qcounter == questionsArray.getQuestionsArray().size() - 1) {
                
                this.correctFinalAnswer(player, qcounter);
            } else {
                
                System.out.println("You answered correctly, you now have a total of " + player.getTotalMoney());
                //updates question and prizes
                this.setqCounter(this.getqCounter() + 1);
                this.setpCount(this.getpCount() + 1);
            }
        } else {
            this.incorrectAnswer(player, qcounter);
            this.setGameActive(false);
        }
    }

    //check for valid input returns a boolean
    public boolean isInputValid(int userAns) {
        if (this.questionsArray.getQuestionsArray().get(this.getqCounter()).getAnswers().length == 2) {
            int optionOne = Character.getNumericValue(this.questionsArray.getQuestionsArray().get(this.getqCounter()).getAnswers()[0].charAt(0));
            int optionTwo = Character.getNumericValue(this.questionsArray.getQuestionsArray().get(this.getqCounter()).getAnswers()[1].charAt(0));
            if (userAns == optionOne || userAns == optionTwo || userAns == 0) {
                this.setIsValid(true);
            } else {
                this.setIsValid(false);
            }
        } else {
            if (userAns > this.questionsArray.getQuestionsArray().get(this.getqCounter()).getAnswers().length || userAns < 0) {
                this.setIsValid(false);
            } else {
                this.setIsValid(true);
            }
        }

        return this.IsValid();

    }

    //fifty fifty life line removes 2 incorrect options off the current question leaving remaining correct answer and 1 incorrect answer
    public Question FiftyFifty(int qcounter) {
        ArrayList<String> newAns = new ArrayList();
        ArrayList<String> falseAns = new ArrayList();
        Random rand = new Random();
        String correctAns = "";
        //sets correct answer from question and wrong answers
        for (int i = 0; i < questionsArray.getQuestionsArray().get(qcounter).getAnswers().length - 1; i++) {       
            if (questionsArray.getQuestionsArray().get(qcounter).getAnswers()[i].contains(questionsArray.getQuestionsArray().get(qcounter).getCorrectAnswer() + ")")) {
                //iterating through question array if the question answer contains the same string as the correct asnwer it is the correct answer
                correctAns = questionsArray.getQuestionsArray().get(qcounter).getAnswers()[i];
            } else {
                falseAns.add(questionsArray.getQuestionsArray().get(qcounter).getAnswers()[i]);
            }
        }
                
        String[] stringfalseAns = falseAns.toArray(new String[3]);
        newAns.add(correctAns);
        newAns.add(stringfalseAns[rand.nextInt(falseAns.size())]);
        Collections.sort(newAns);

        String[] stringNewAns = newAns.toArray(new String[2]);

        Question q = new Question(questionsArray.getQuestionsArray().get(qcounter).getQuestion(), stringNewAns, questionsArray.getQuestionsArray().get(qcounter).getCorrectAnswer());
        questionsArray.getQuestionsArray().set(qcounter, q);

        return q;
    }
    
    //pass question life line gives a new question but cannot go back to previous question or get a pass question, also does not effect prize money
    public void PassQuestion(int qcounter){
        this.setqCounter(this.getqCounter() + 1);
        
    }
    //call a friend life line, gives friends suggested answer player can choose to believe suggested answer or not
    public void CallAFriend(int qcounter) throws InterruptedException{
        String answerString  = questionsArray.getQuestionsArray().get(qcounter).getCorrectAnswer() + "";
        //1 in 5 chance friend is giving a random answer
        Random rand = new Random();
        int randNum = rand.nextInt(6) + 1;
        if(randNum == 1){
            answerString = rand.nextInt(4) + 1 + "";
        }
        
        //prints out friends answer statement 
        System.out.println("*Calling your friend* ");
        for(int x = 0; x < 3; x++){
            TimeUnit.SECONDS.sleep(1);
            System.out.print(" .");
        }
        answerString = " I think that the answer is " + answerString + ". Good Luck :D \n";  
        for(int count = 0;count < answerString.length(); count++){
            TimeUnit.MILLISECONDS.sleep(250);
            System.out.print(answerString.charAt(count));
        }
        TimeUnit.SECONDS.sleep(1);
    }

    //selects lifeline and updates life line variables
    public void chooseLifeline() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        boolean lifelineUsed = false;
        boolean terminated = false;
        do {
            System.out.println("****\n");
            System.out.println("Lifelines: ");
            System.out.println("1) " + Lifeline.FiftyFity.getlifeLineName());
            System.out.println("2) " + Lifeline.PassQuestion.getlifeLineName());   
            System.out.println("3) " + Lifeline.CallAFriend.getlifeLineName());
         
            System.out.println("4) Quit lifeline menu");

            System.out.println("Answer: ");
            try {
                int userAns = scan.nextInt();
                if (userAns == 1 || userAns == 2|| userAns == 3|| userAns == 4) {
                    switch (userAns) {
                        case 1:
                            if (this.getffused() == false) {
                                this.FiftyFifty(this.getqCounter());
                                lifelineUsed = true;
                                this.setffused(true);                                
                            } else {
                                System.out.println("\n You cannot use Fifty Fifty LifeLine again sorry.");
                            }
                        case 2:
                            if (this.getPassUsed() == false) {
                                this.PassQuestion(this.getqCounter());
                                lifelineUsed = true;
                                this.setPassUsed(true);                                
                            } else {
                                System.out.println("\n You cannot use Pass Question LifeLine again sorry.");
                            }
                        case 3:
                            if (this.getCallUsed() == false) {
                                this.CallAFriend(this.getqCounter());
                                lifelineUsed = true;
                                this.setCallused(true);                                
                            } else {
                                System.out.println("\n You cannot use Call a Friend LifeLine again sorry.");
                            }
                        case 4:
                            terminated = true;
                    }
                } else {
                    System.out.println("Invalid Input, please input the  integer option corresponding to your 'lifeline' carefully");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please input a integer answer.");
                System.out.println("\n");
                scan.next();
            }
            if (terminated) {
                break;
            }
        } while (!lifelineUsed);
    }
    
    
    //get and set methods    
    public boolean getGameActive() {
        return this.gameActive;
    }

    public void setGameActive(boolean gameActive) {
        this.gameActive = gameActive;
    }

    public boolean IsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getqCounter() {
        return qCounter;
    }

    public void setqCounter(int counter) {
        this.qCounter = counter;
    }
    
    public int getpCount() {
        return prizeCount;
    }

    public void setpCount(int counter) {
        this.prizeCount = counter;
    }

    public boolean getffused() {
        return fiftyFiftyUsed;
    }

    public void setffused(boolean ff) {
        this.fiftyFiftyUsed = ff;
    }
    
    public boolean getPassUsed() {
        return PassUsed;
    }

    public void setPassUsed(boolean pu) {
        this.PassUsed = pu;
    }
    
    public boolean getCallUsed() {
        return CallUsed;
    }

    public void setCallused(boolean cu) {
        this.CallUsed = cu;
    }
    
}
