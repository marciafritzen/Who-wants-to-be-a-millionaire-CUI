/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireGame;

import java.util.ArrayList;
/**
 *
 * @author Paul
 */
public class PlayerArray {
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
    
    //
    
    
    public void removeExistingPlayer(Player player) {
        for (int i = 0; i < this.getPlayerArray().size(); i++) {
            if (this.getPlayerArray().get(i).getPlayerName().trim().toLowerCase().equals(player.getPlayerName().trim().toLowerCase())) {
                this.getPlayerArray().remove(i);
            }
        }

    }
    
        /**
     * Initializes Player class with the name user has input in the console
     * and sets their Total money to 0 as they are a new player.
     * @param player
     * @param playerName
     * @return player object
     */
    public Player newPlayer(Player player, String playerName) {
        player = new Player(playerName, 0);
        return player;

    }
    /**
     * Initializes player class with a name which the user has input in the console and 
     * if it was also contained in the playerList array and also gets their total money earned when they
     * previously played the game
     * @param player 
     * @param i
     * @return player object
     */

    public Player existingPlayer(Player player, int i) {
        player = new Player(this.getPlayerArray().get(i).getPlayerName().trim(), this.getPlayerArray().get(i).getTotalMoney());
        return player;

    }

//this method check over array for players with the same name
    public Player checkForExistingPlayer(Player player, String playerName) {
        if (this.getPlayerArray().isEmpty()) {
            player = this.newPlayer(player, playerName);
        } else {
            for (int i = 0; i <= this.getPlayerArray().size() - 1; i++) {
                if (this.getPlayerArray().get(i).getPlayerName().trim().toLowerCase().equals(playerName.toLowerCase())) {
                    player = this.existingPlayer(player, i);
                    break;
                } else if (i == this.getPlayerArray().size() - 1) {
                    player = this.newPlayer(player, playerName);
                    break;
                }
            }
        }
        return player;
    }
    
    
}
