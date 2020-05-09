package MillionaireGame;

import java.util.ArrayList;
/**
 * @author Paul Vu 17981406, Peter Ho 17978553
 */
public class PlayerArray {
    //playerArray is a arraylist of the player class, acts a data storage to the playerData.txt file
    ArrayList<Player> playerArray;
    
    public PlayerArray(){
        this.playerArray = new ArrayList();
    }
    
    public void addPlayer(Player p){
        this.playerArray.add(p);
    }
    
    public ArrayList<Player> getPlayerArray(){
        return this.playerArray;
    }
    
    
    //creates new player instance
    public Player newPlayer(Player player, String playerName) {
        player = new Player(playerName, 0);

        return player;
    }

}
