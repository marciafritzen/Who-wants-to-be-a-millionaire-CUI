/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MillionaireGame;

/**
 *
 * @author Paul
 */
public class Player {
    private String name;
    private int totalMoney;
    int[] prizes = {0,100,200,300,500,1000,2000,4000,8000,16000,32000,64000,125000,250000,500000,1000000};

    public Player(String PlayerName, int totalMoney){
        this.setPlayerName(PlayerName);
        this.setTotalMoney(totalMoney);
    }
    
    public int[] getPrizes() {
        return prizes;
    }

    public String getPlayerName() {
        return name;
    }

    public void setPlayerName(String PlayerName) {
        this.name = PlayerName;
    }

    public int getTotalMoney() {
        return this.totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
       
    @Override
    public String toString(){
        return this.getPlayerName()+"\n";
    }
}
