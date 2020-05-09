package MillionaireGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 *
 * @author Paul
 */
public class PlayerDataWriter {
    
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
            // TODO Auto-generated catch block
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
