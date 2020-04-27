
package MillionaireGame;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Paul
 */
public class Game {
    
    PlayerArray playerArray;
    QuestionsArray questionsArray;
    Player player;
    
    //condition for looping game
    boolean gameActive = true;
    
    boolean isValid;
    int lifeLineCounter = 3;
    
    //keeps track of question and prize number
    int qCounter = 0;
    

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
    public void StartGame(Scanner scan) throws IOException {
        System.out.println("Please enter your name(or if you are a returning player enter 'returning'): "); 
        player.setPlayerName(scan.nextLine().trim());
        
        
        if(player.getPlayerName().equals("returning")){
            //display players to choose from
            for(int i = 0; i < playerArray.getPlayerArray().size(); i ++){
                System.out.println((i + 1) +") " + playerArray.getPlayerArray().get(i).toString() + " Money Won by player:  " + playerArray.getPlayerArray().get(i).getTotalMoney());
            }
            int userAns;
            try {
                System.out.println("Select your player/save slot or enter '0' to go enter as a new player");
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
            System.out.println("\n");
            System.out.println("\n$" + player.getPrizes()[this.getqCounter() + 1] + " dollar question");
            System.out.println("LifeLines remaining: " + this.getLifeLineCounter());
            System.out.println("Input '0' to quit and leave with the money you have won so far ($:" + player.getPrizes()[this.getqCounter()] + ")\n");
            System.out.println("Q" + (this.getqCounter() + 1) + ": " + questionsArray.getQuestionsArray().get(this.getqCounter()));
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

    //method updates players prize winnings and continues game
    public void correctFinalAnswer(Player player, int counter) throws IOException {
        playerArray.removeExistingPlayer(player);
        player.setTotalMoney(player.getTotalMoney() + player.getPrizes()[counter + 1]);
        System.out.println("Correct Answer");
        System.out.println("==================================================================================");
        System.out.println("CONGRATULATIONS! that was the final question you now have $:" + player.getPrizes()[counter + 1]);
        System.out.println("Player: " + player.toString());
        playerArray.getPlayerArray().add(new Player(player.getPlayerName(), player.getTotalMoney()));
        PlayerDataWriter.savePlayers(playerArray);
        exit(0);
    }

    
    public void incorrectAnswer(Player player, int counter) throws IOException {
        playerArray.removeExistingPlayer(player);        
        System.out.println("Incorrect Answer");
        System.out.println("==================================================================================");
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


    public void checkAnswer(Player player, int qcounter, int userAns) throws IOException {
        if (questionsArray.getQuestionsArray().get(qcounter).getCorrectAnswer() == userAns) {
            if (qcounter == questionsArray.getQuestionsArray().size() - 1) {
                
                this.correctFinalAnswer(player, qcounter);
            } else {
                
                System.out.println("You answered correctly, you now have a total of " + player.getTotalMoney());
                //
                this.setqCounter(this.getqCounter() + 1);
            }
        } else {
            this.incorrectAnswer(player, qcounter);
            this.setGameActive(false);
        }
    }

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

    public Question FiftyFifty(int counter) {
        ArrayList<String> newAns = new ArrayList();
        ArrayList<String> falseAns = new ArrayList();
        Random rand = new Random();
        String correctAns = "";
        for (int i = 0; i < questionsArray.getQuestionsArray().get(counter).getAnswers().length - 1; i++) {
            if (questionsArray.getQuestionsArray().get(counter).getAnswers()[i].contains(questionsArray.getQuestionsArray().get(counter).getCorrectAnswer() + ")")) {
                correctAns = questionsArray.getQuestionsArray().get(counter).getAnswers()[i];
            } else {
                falseAns.add(questionsArray.getQuestionsArray().get(counter).getAnswers()[i]);
            }
        }
        String[] stringfalseAns = falseAns.toArray(new String[3]);
        newAns.add(correctAns);
        newAns.add(stringfalseAns[rand.nextInt(falseAns.size())]);
        Collections.sort(newAns);

        String[] stringNewAns = newAns.toArray(new String[2]);

        Question q = new Question(questionsArray.getQuestionsArray().get(counter).getQuestion(), stringNewAns, questionsArray.getQuestionsArray().get(counter).getCorrectAnswer());
        questionsArray.getQuestionsArray().set(counter, q);

        return q;
    }

    public void chooseLifeline() {

        Scanner scan = new Scanner(System.in);

        boolean usedLifeline = false;
        boolean terminated = false;
        do {
            System.out.println("==================================================================================\n");
            System.out.println("Lifelines: ");
            System.out.println("1) " + Lifeline.FiftyFity.getlifeLineName());
            System.out.println("2) Quit lifeline menu");

            System.out.println("Answer: ");
            try {
                int userAns = scan.nextInt();
                if (userAns == 1 || userAns == 2) {
                    switch (userAns) {
                        case 1:
                            if (this.getLifeLineCounter() > 0) {
                                this.FiftyFifty(this.getqCounter());
                                this.setLifeLineCounter(this.getLifeLineCounter() - 1);
                                usedLifeline = true;
                            } else {
                                System.out.println("\n No More LifeLines either risk it all or walk away with what you have");
                            }
                        case 2:
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

        } while (!usedLifeline);

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

    public int getLifeLineCounter() {
        return lifeLineCounter;
    }

    public void setLifeLineCounter(int lifeLineCounter) {
        this.lifeLineCounter = lifeLineCounter;
    }
    
}
