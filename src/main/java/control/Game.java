package control;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import view.Draw;
import view.Map;
import java.io.IOException;

public class Game {
    private final Map map;
    private boolean gameEnded = false;
    private Draw draw = new Draw();

    long fall;
    public long FALL_INTERVAL = 700;

    /**
     * @return gameEnded
     */
    public boolean getGameEnded() { return gameEnded; }

    /**
     * Method which initializes screen and creates Map
     * @throws IOException
     */
    public Game() throws IOException {
        map = new Map();
        draw.drawInitialize();

    }

    /**
     * Method which controls the flow of game and calls all necessary functions for it to work
     * @throws IOException
     */
    public void Run() throws IOException {
        draw.drawMenu();

        map.chooseNextShape();
        gameEnded = !map.spawnShape();
        map.ghostPiece();
        fall = System.currentTimeMillis();

        draw.DrawMap(map);
        while (!gameEnded) {
            canPlay();
            update();
            draw.DrawMap(map);
        }

        draw.drawGameOver(map.getScore());
    }

    /**
    * False if all conditions are met for the game to continue
    * True if the game has reached its end
    */
    public void canPlay() {
        for (int i = 0; i < map.getMapMatrix()[0].length; i++) {
            if (map.getMapMatrix()[0][i] < 0) {
                gameEnded = true;
            }
        }
    }

    /**
     * Function to check if the piece can fall or not according to time elapsed
     * @return true if can fall, false otherwise
     */
    public boolean isFalling() {
        long now = System.currentTimeMillis();

        if ((now - fall) > FALL_INTERVAL) {
            fall = now;
            return true;
        }
        return false;
    }

    /**
     * Updates game related variables
     * @throws IOException
     */
    public void update() throws IOException {

       KeyStroke key = draw.pollInput();

        if (key != null) {
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                System.out.println("Closed");

            }

            processKey(key);
        }

        if(isFalling()) {
            if(!map.collisionBottom(map.getCurrShape()) && !map.collisionShapes(1, map.getCurrShape()) ){
                map.moveShapeDown();
            }

            else {
                map.addShapeMap();
                map.clearLine();

                key = null;

                map.chooseNextShape();
                gameEnded = !map.spawnShape();
            }
        }

        if(map.getLevelCounter() >= 2) {
            FALL_INTERVAL = FALL_INTERVAL / 3 ;
            map.setLevelCounter(0);
        }

        map.updateMap();
    }

    /**
     * Method to make changes in Map according to keyboard input
     * @param key - input from keyboard
     */
    private void processKey(KeyStroke key) { map.processKey(key); }


}
