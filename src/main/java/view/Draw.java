package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Draw {
    private Screen screen;
    private TextGraphics tg;

    /**
     * Poll input
     * @return key
     * @throws IOException
     */
    public KeyStroke pollInput() throws IOException {
        KeyStroke key = screen.pollInput();
        return key;
    }

    /**
     * Draw map
     * @param map
     * @throws IOException
     */
    public void DrawMap(Map map) throws IOException {

        screen.clear();
        map.Draw(tg);
        screen.refresh(Screen.RefreshType.DELTA);
    }

    /**
     * Draw initialization
     */
    public void drawInitialize(){
        try{
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
            tg = screen.newTextGraphics();

        } catch(IOException e){
            e.printStackTrace();

        }
    }

    /**
     * Method to display GameOver screen at the end
     * @throws IOException
     */
    public void drawGameOver(int score) throws IOException {
        screen.clear();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(10, 10, "GAME OVER");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(10, 14, String.valueOf(score) + " POINTS");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        screen.refresh();

        KeyStroke keyStroke = screen.readInput();
        if (keyStroke.getKeyType() == KeyType.Character && (keyStroke.getCharacter() == 'q' || keyStroke.getCharacter() == ' ')) {
            screen.close();
        }

        screen.refresh();
    }

    /**
     * Method which draws menu
     * @throws IOException
     */
    public void drawMenu() throws IOException {
        screen.clear();
        int option = 0;
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(10, 4, "MAIN MENU");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(10, 8, "NEW GAME");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(10, 12, "INSTRUCTIONS");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(10, 16, "EXIT");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(6, 8, "->");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(27, 12, "<Press Space key to choose>");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        screen.refresh();

        KeyStroke keyStroke = screen.readInput();


        while(!(keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ')){
            screen.clear();
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(10, 4, "MAIN MENU");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);

            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(10, 8, "NEW GAME");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);

            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(10, 12, "INSTRUCTIONS");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);

            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            tg.putString(10, 16, "EXIT");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);

            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(27, 12, "<Press Space key to choose>");
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);


            switch (keyStroke.getKeyType()) {
                case ArrowUp -> {
                    if(option != 0){
                        option--;
                    }
                }
                case ArrowDown -> {
                    if(option!=2){
                        option++;
                    }
                }
            }

            switch (option){
                case 0 -> {
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(6, 8, "->");
                    tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                }
                case 1 ->{
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(6, 12, "->");
                    tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                }
                case 2 ->{

                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(6, 16, "->");
                    tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                }
            }
            screen.refresh(Screen.RefreshType.DELTA);
            keyStroke = screen.readInput();

        }
        switch (option) {
            case 0 -> {
                return;
            }
            case 1 -> {
                drawInstructions();
            }
            case 2 -> {
                screen.close();
            }
        }
    }

    /**
     * Method which displays the instructions of Tetris game
     * @throws IOException
     */
    public void drawInstructions() throws IOException {
        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(10, 4, "Instructions");
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(5, 8, "Use the left and right arrow keys to move the falling piece left or right");
        tg.putString(5, 10, "Use the up arrow key to rotate the falling piece.");
        tg.putString(5, 12, "Use the down arrow key to make the piece fall faster");
        tg.putString(5, 14, "Use the spacebar to drop the piece instantly.");
        tg.putString(5, 16, "Use the 'c' key to hold the current piece for later use.");
        tg.putString(5, 18, "Press the 'q' to close the game.");
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(10, 20, "<Space key to continue>");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        screen.refresh();
        KeyStroke keyStroke = screen.readInput();
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') {
            screen.close();
        } else if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' '){
            screen.clear();
            drawMenu();
        }
    }
}
