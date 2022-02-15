package SaveLoad;

import Moves.Board;
import Game.Clock;
import Game.Main;
import Moves.Move;
import View.Figure;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SaveLoad.SaveLoadGame represents method for saving and loading JSON file.
 *
 * @author TimotejBarbus
 */
public class SaveLoadGame {

    /**
     * Checks if all letters are in required format.
     *
     * @param input represents controlled word
     * @return returns true if they are in required format, false if they are not
     */
    public static boolean checkLetters(String input) {
        if(input.matches("^[a-zA-Z0-9,.;:_'-]+$")) {
            return true;
        }
        System.out.println("Name of the file contains unsupported letters! ");
        return false;
    }

    /**
     * Saves the game to JSON file.
     */
    public static void saveGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type name of the file:");

        String fileName = sc.nextLine();
        while(!checkLetters(fileName)) {
            fileName = sc.nextLine();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonLoaderFigures[][] jsonLoaderFigures = new JsonLoaderFigures[8][8];
        JsonLoaderVariables jsonLoaderVariables;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                jsonLoaderFigures[i][j] = new JsonLoaderFigures(Board.board[i][j].type, Board.board[i][j].color);
            }
        }

        jsonLoaderVariables = new JsonLoaderVariables(Clock.whiteTime, Clock.blackTime, Move.playerColor, Move.whiteKingMoved, Move.blackKingMoved,
                Move.leftWhiteRookMoved, Move.rightWhiteRookMoved, Move.leftBlackRookMoved, Move.rightBlackRookMoved);

        try {
            objectMapper.writeValue(new File("src/savedgames/" + fileName + "Figures.json"), jsonLoaderFigures);
            objectMapper.writeValue(new File("src/savedgames/" + fileName + "Variables.json"), jsonLoaderVariables);

            System.out.println("Game was saved.");
        }
        catch (IOException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
            System.out.println("Game could not be saved!");
        }
    }

    /**
     * Loads the game from JSON file.
     */
    public static void loadGame() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Type name of the file:");

        String fileName = sc.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonLoaderFigures[][] jsonLoaderFigures = new JsonLoaderFigures[8][8];
            JsonLoaderVariables jsonLoaderVariables;

            jsonLoaderFigures = objectMapper.readValue(new File("src/savedgames/" + fileName + "Figures.json"), JsonLoaderFigures[][].class);

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Board.board[i][j] = jsonLoaderFigures[i][j].setLoadedGameFigures();
                }
            }

            Figure.removeLoadFiguresGUI();
            Figure.generateFiguresAfterLoad();

            jsonLoaderVariables = objectMapper.readValue(new File("src/savedgames/" + fileName + "Variables.json"), JsonLoaderVariables.class);

            JsonLoaderVariables.setLoadedGameVariables(jsonLoaderVariables);
            Clock.generateClock();
        } catch (IOException error) {
            System.out.println("Name does not exist!");
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        }

    }
}
