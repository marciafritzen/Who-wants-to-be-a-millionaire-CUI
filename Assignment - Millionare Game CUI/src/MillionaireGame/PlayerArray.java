package MillionaireGame;

import java.util.ArrayList;
/**
 *
 * @author Paul
 */
public class PlayerArray {
    //playerArray is a arraylist of the player class, acts a data storage to the playerData.txt file with methods which manage contents
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
    
    //removes a player from array
    public void removeExistingPlayer(Player player) {
        for (int i = 0; i < this.getPlayerArray().size(); i++) {
            if (this.getPlayerArray().get(i).getPlayerName().trim().toLowerCase().equals(player.getPlayerName().trim().toLowerCase())) {
                this.getPlayerArray().remove(i);
            }
        }
    }
    
    //creates new player instance
    public Player newPlayer(Player player, String playerName) {
        player = new Player(playerName, 0);

        return player;
    }

}
