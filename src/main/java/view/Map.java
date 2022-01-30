package view;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int col;
    private int row;
    private int levelCounter = 0;
    private final int topCenter = 9;
    private int[][] mapMatrix;
    private AbstractShape currShape;
    private AbstractShape nextShape;
    private AbstractShape ghostShape = new SquareShape();
    private AbstractShape holdShape;
    private int holdShapecount = 0;
    private AbstractShape[] tetrominos = new AbstractShape[] { new JShape(),
            new LineShape(), new LShape(), new SquareShape(),
            new SShape(), new TShape(), new ZShape() };
    private Score score = new Score();

    //Constructors
    public Map() {
        this.col = 19;
        this.row = 18;
        mapMatrix= new int[col][row];
    }

    //Setters
    public void setCol(int col) { this.col = col; }

    public void setRow(int row) { this.row = row; }

    public void setCurrShape(AbstractShape newShape) { currShape = newShape; }

    public void setLevelCounter(int levelCounter) { this.levelCounter = levelCounter; }

    //Getters
    public int getCol() { return this.col; }

    public int getRow() { return this.row; }

    public int getLevelCounter(){return this.levelCounter;}

    public int getScore(){return this.score.getScore();}

    public AbstractShape getCurrShape() { return currShape; }

    public int[][] getMapMatrix() { return mapMatrix; }

    //Methods
    /**
     * Method to spawn shape in Map
     * @return true if shape was spwaned, false otherwise
     */
    public boolean spawnShape(){
        int spawnPoint = topCenter;

        if(currShape.getCurrentPos()[0].length ==3 || currShape.getCurrentPos()[0].length ==4 ) {
            spawnPoint -=1;
            currShape.setCoordinates(spawnPoint, currShape.getCentreRow());
        }

        for (int i = 0; i< currShape.getCurrentPos().length; i++) {
            for (int j = 0; j<currShape.getCurrentPos()[0].length; j++) {
                if(mapMatrix[i][j+spawnPoint] < 0){
                    return false;
                }

                if(mapMatrix[i][j+spawnPoint] == 0 && currShape.getCurrentPos()[i][j]== 1) {
                    mapMatrix[i][j+spawnPoint] = currShape.getCurrentPos()[i][j];
                }
            }
        }

        ghostPiece();
        return true;
    }

    /**
     * Method which updates Map
     */
    public void updateMap(){

        for(int i = 0; i< mapMatrix.length; i++) {
            for(int j = 0; j < mapMatrix[0].length; j++) {
                if(mapMatrix[i][j] == 1){
                    mapMatrix[i][j] = 0;
                }
            }
        }

        for(int i = 0; i< currShape.getCurrentPos().length; i++) {
            for (int j = 0; j<currShape.getCurrentPos()[0].length; j++) {
                if((mapMatrix[i+currShape.getCentreRow()][j+currShape.getCentreCol()] == 0 || mapMatrix[i+currShape.getCentreRow()][j+currShape.getCentreCol()] == 2) && currShape.getCurrentPos()[i][j]== 1) {
                    mapMatrix[i+currShape.getCentreRow()][j+currShape.getCentreCol()] = currShape.getCurrentPos()[i][j];
                }
            }
        }
    }

    /**
     * Method which chooses randomly next shape
     */
    public void chooseNextShape(){
        if(nextShape == null){
            nextShape = tetrominos[(int) (Math.random()*tetrominos.length)];
        }

        setCurrShape(nextShape);

        tetrominos = new AbstractShape[] { new JShape(),
                new LineShape(), new LShape(), new SquareShape(),
                new SShape(), new TShape(), new ZShape() };

        nextShape = tetrominos[(int) (Math.random()*tetrominos.length)];

        tetrominos = new AbstractShape[] { new JShape(),
                new LineShape(), new LShape(), new SquareShape(),
                new SShape(), new TShape(), new ZShape() };
    }

    /**
     * Method which adds a Shape to Map
     */
    public void addShapeMap() {
        holdShapecount = 0;

        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[0].length; j++) {
                if (mapMatrix[i][j] == 1) {
                    mapMatrix[i][j] = currShape.getColorValue();
                }
            }
        }

        for (int i = 0; i< mapMatrix.length; i++) {
            for(int j = 0; j < mapMatrix[0].length; j++) {
                if(mapMatrix[i][j] == 2) {
                    mapMatrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * Method that clears a line in Map
     */
    public void clearLine(){
        List<Integer> list = new ArrayList<Integer>();
        boolean lineFull;

        for (int i = 0; i < mapMatrix.length; i++) {
            lineFull = true;

            for (int j = 0; j < mapMatrix[0].length; j++) {
                if (mapMatrix[i][j] == 0){
                    lineFull = false;
                    break;
                }
            }

            if (lineFull) {
                list.add(i);

                for (int x = i; x > 0; x--) {
                    mapMatrix[x] = mapMatrix[x-1];
                }

                mapMatrix[0]= new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            }
        }

        levelCounter += list.size();
        score.clearLineScore(list.size());
    }

    /**
     * Method that moves a shape down
     */
    public void moveShapeDown(){ currShape.setCoordinates(currShape.getCentreCol(), currShape.getCentreRow()+1); }

    /**
     * Test collision with bottom of Map
     * @return true if hit bottom
     */
    public boolean collisionBottom(AbstractShape shape) {
        int lastOneIndex = 0;

        for (int i = 0; i < shape.getCurrentPos().length; i++) {
            for (int j = 0; j < shape.getCurrentPos()[i].length; j++) {
                if (shape.getCurrentPos()[i][j] == 1) {
                    if(i>lastOneIndex) {
                        lastOneIndex = i;
                    }
                }
            }
        }

        if(shape.getCentreRow()+lastOneIndex == getRow()){
            return true;
        }

        return false;
    }

    /**
     * Test collision with right boundary of Map
     * @return true if hit right side
     */
    public boolean collisionRight() {
        int indexOne = 0;

        for (int i = 0; i < currShape.getCurrentPos().length; i++) {
            for (int j = currShape.getCurrentPos()[0].length-1; j >= 0; j--) {
                if (currShape.getCurrentPos()[i][j] == 1 && j > indexOne) {
                    indexOne = j;
                }
            }
        }

        if (currShape.getCentreCol() + indexOne == getMapMatrix()[0].length-1) { return true; }
        else return false;
    }

    /**
     * Test collision with left boundary of Map
     * @return true if hit left side
     */
    public boolean collisionLeft() { return currShape.getCentreCol() == 0; }

    /**
     * Test collision with another Shape
     * @return true if hit another Shape
     */
    public boolean collisionShapes(int choice, AbstractShape shape) {
        boolean collisionBelow = false;
        boolean collisionLeft = false;
        boolean collisionRight= false;

        for (int i = 0; i < shape.getCurrentPos().length; i++) {
            for (int j = 0; j < shape.getCurrentPos()[i].length; j++) {
                if (shape.getCurrentPos()[i][j] == 1) {
                    //Collisions below
                    if(!collisionBottom(shape)) {
                        if (mapMatrix[shape.getCentreRow() + i + 1][shape.getCentreCol() + j] < 0) {
                            collisionBelow = true;
                        }
                    }

                    //Collision sideways
                    if(!collisionLeft() && !collisionRight()) {
                        if (mapMatrix[0].length - 1 != shape.getCentreCol() + j) {
                            if (mapMatrix[shape.getCentreRow() + i][shape.getCentreCol() + j + 1] < 0) {
                                collisionRight = true;
                            }

                            if(mapMatrix[shape.getCentreRow() + i][shape.getCentreCol() + j - 1] < 0) {
                                collisionLeft = true;
                            }
                        }
                    }
                }
            }
        }

        //Return whatever we wanted to know based on parameter
        return switch (choice) {
            case 1 -> collisionBelow;
            case 2 -> collisionLeft;
            case 3 -> collisionRight;
            default -> false;
        };
    }

    /**
     * Test if we can rotate without collision
     */
    public void collisionRotate() {
        currShape.rotate();
        int highestBlock = mapMatrix.length;

        if(currShape.getCurrentPos()[0].length + currShape.getCentreCol() > mapMatrix[0].length) {
            int posCorrection = currShape.getCurrentPos()[0].length + currShape.getCentreCol() - mapMatrix[0].length;
            currShape.setCoordinates(currShape.getCentreCol()-posCorrection, currShape.getCentreRow());
        }
        else if(currShape.getCentreCol() < 0) {
            currShape.setCoordinates(0, currShape.getCentreRow());
        }
        else if(currShape.getCurrentPos().length + currShape.getCentreRow() > mapMatrix.length) {
            int posCorrection = currShape.getCurrentPos().length + currShape.getCentreRow() - mapMatrix.length;
            currShape.setCoordinates(currShape.getCentreCol(), currShape.getCentreRow() -posCorrection);
        }

        for (int m = 0;m< mapMatrix.length; m++ ) {
            for (int n = currShape.getCentreCol(); n < currShape.getCurrentPos()[0].length+currShape.getCentreCol(); n++) {
                if(mapMatrix[m][n] < 0 && m<highestBlock){
                    highestBlock=m;
                }
            }
        }

        for(int i = currShape.getCentreRow(); i < currShape.getCurrentPos().length+currShape.getCentreRow(); i++) {
            for(int j = currShape.getCentreCol(); j < currShape.getCurrentPos()[0].length+currShape.getCentreCol(); j++) {
                if(mapMatrix[i][j] <0 && currShape.getCurrentPos()[i- currShape.getCentreRow()][j- currShape.getCentreCol()] == 1) {
                    int posCorrection = currShape.getCentreRow() + currShape.getCurrentPos().length - highestBlock;
                    currShape.setCoordinates(currShape.getCentreCol(), currShape.getCentreRow() -posCorrection);
                    break;
                }
            }
        }
    }

    /**
     * Method which manages shape to be held
     */
    public void holdPiece(){
        holdShapecount++;

        if(holdShape == null) {
            holdShape = currShape;
            holdShape.setCoordinates(9, 0);
            currShape = nextShape; spawnShape();
            chooseNextShape();
        }
        else {
            AbstractShape auxShape = holdShape;
            holdShape = currShape;
            holdShape.setCoordinates(9, 0);
            currShape = auxShape; spawnShape();
        }
    }

    /**
     * Method which allows instant drop feature
     */
    public void instantDrop(){
        int distance = ghostShape.getCentreRow() -currShape.getCentreRow();

        currShape.setCoordinates(ghostShape.getCentreCol(), ghostShape.getCentreRow());
        score.hardDrop(distance);
    }

    /**
     * Method which manages which key was pressed on keyboard input
     * @param key - key pressed on keyboard
     */
    public void processKey(KeyStroke key){
        switch (key.getKeyType()) {
            case ArrowLeft -> {
                if(!collisionLeft() && !collisionShapes(2, currShape)) {
                    currShape.moveLeft();
                    ghostPiece();
                    updateMap();
                }
            }
            case ArrowRight -> {
                if(!collisionRight() && !collisionShapes(3, currShape)) {
                    currShape.moveRight();
                    ghostPiece();
                    updateMap();
                }

            }
            case ArrowUp -> {
                if(currShape.getCentreRow() != 0) {
                    collisionRotate();
                    ghostPiece();
                    updateMap();
                }
            }
            case ArrowDown -> {
                if(!collisionShapes(1, currShape) && !collisionBottom(currShape)) {
                    moveShapeDown();
                    score.softDrop(1);
                    updateMap();
                }
            }
            case Character -> {
                if(key.getCharacter() == ' ') {
                    instantDrop();
                }
                else if(key.getCharacter() == 'c' && holdShapecount == 0) {
                    holdPiece();
                    updateMap();
                }
            }
        }
    }

    /**
     * Method that creates ghost piece (shadow of current shape)
     */
    public void ghostPiece() {
        int highestBlockRow = mapMatrix.length;
        boolean collided = true;

        ghostShape.copy(currShape);
        ghostShape.setColorValue(2);
        ghostShape.setColor(TextColor.ANSI.BLACK_BRIGHT);

        for (int i = 0; i< mapMatrix.length; i++) {
            for(int j = 0; j < mapMatrix[0].length; j++) {
                if(mapMatrix[i][j] == 2) {
                    mapMatrix[i][j] = 0;
                }
            }
        }


        for (int i = currShape.getCentreRow(); i < mapMatrix.length; i++) {
            for (int j = currShape.getCentreCol(); j< currShape.getCentreCol()+currShape.getCurrentPos()[0].length; j++) {
                if(mapMatrix[i][j] < 0 && i<highestBlockRow) {
                    highestBlockRow = i;
                }
            }
        }

        if(highestBlockRow <=1) { return; }

        if((highestBlockRow != mapMatrix.length) || currShape.getCentreRow()>highestBlockRow) {
            ghostShape.setCoordinates(ghostShape.getCentreCol(), highestBlockRow);

            for (int i= currShape.getCentreRow(); i<=ghostShape.getCentreRow(); i++) {
                for (int j = 0; j<currShape.getCurrentPos()[0].length; j++) {
                    if(mapMatrix[i][j+currShape.getCentreCol()] < 0) {
                        ghostShape.setCoordinates(ghostShape.getCentreCol(), ghostShape.getCentreRow()-1);
                        break;
                    }
                }
            }

            while(collided) {
                collided = false;

                for (int i = 0; i < currShape.getCurrentPos().length; i++ ) {
                    for (int j = 0; j< currShape.getCurrentPos()[0].length; j++) {

                        if(ghostShape.getCentreRow()+i > 18) {
                            int outOfBounds = (ghostShape.getCentreRow()+i)-18;
                            ghostShape.setCoordinates(ghostShape.getCentreCol(), ghostShape.getCentreRow()-outOfBounds);
                        }

                        if(mapMatrix[i+ghostShape.getCentreRow()][j+ghostShape.getCentreCol()] <0 && currShape.getCurrentPos()[i][j] == 1) {
                            ghostShape.setCoordinates(ghostShape.getCentreCol(), ghostShape.getCentreRow()-1);
                            collided = true;
                        }
                    }
                }
            }
        }
        else { ghostShape.setCoordinates(ghostShape.getCentreCol(), mapMatrix.length-currShape.getCurrentPos().length); }

        for (int i = 0; i< ghostShape.getCurrentPos().length; i++) {
            for (int j = 0; j<ghostShape.getCurrentPos()[0].length; j++) {
                if(mapMatrix[i+ghostShape.getCentreRow()][j+ghostShape.getCentreCol()] == 0 && ghostShape.getCurrentPos()[i][j] == 1) {
                    mapMatrix[i+ghostShape.getCentreRow()][j+ghostShape.getCentreCol()] = 2;
                }
            }
        }
    }

    /**
     * Method which displays everything according to Map on screen
     * @param tg
     */
    public void Draw(TextGraphics tg) {
        //Displaying Tetromino
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[0].length; j++) {
                if(mapMatrix[i][j] == 2) {
                    tg.setBackgroundColor(ghostShape.getColor());
                    tg.putString(3 + j, 4 + i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if (mapMatrix[i][j] == 1) {
                    tg.setBackgroundColor(currShape.getColor());
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -1) {
                    tg.setBackgroundColor(TextColor.ANSI.RED);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -2) {
                    tg.setBackgroundColor(TextColor.ANSI.YELLOW);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -3) {
                    tg.setBackgroundColor(TextColor.ANSI.BLUE);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -4) {
                    tg.setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -5) {
                    tg.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -6) {
                    tg.setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
                else if(mapMatrix[i][j] == -7) {
                    tg.setBackgroundColor(TextColor.ANSI.MAGENTA);
                    tg.putString(3+j, 4+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
            }
        }

        //Write control.Game Name
        tg.setBackgroundColor(TextColor.ANSI.RED);
        tg.putString(9, 2, "TETRIS");
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);

        //Draw view.Map
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.drawRectangle(new TerminalPosition(2, 4),
                         new TerminalSize(20, 20),
                         Symbols.BLOCK_SOLID);

        //Write Next Shape
        tg.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.setForegroundColor(TextColor.ANSI.BLACK);
        tg.putString(29, 2, "NEXT TETROMINO");
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        //Draw Next Shape
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.drawRectangle(new TerminalPosition(col + 10, 4),
                         new TerminalSize(14, 7),
                         Symbols.BOLD_FROM_NORMAL_SINGLE_LINE_HORIZONTAL);
        for (int i = 0; i < nextShape.getCurrentPos().length; i++) {
            for (int j = 0; j < nextShape.getCurrentPos()[0].length; j++) {
                if(nextShape.getCurrentPos()[i][j] == 1) {
                    tg.setBackgroundColor(nextShape.getColor());
                    tg.putString(35+j, 7+i, " ");
                    tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                }
            }
        }

        //Display Current Score
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.drawRectangle(new TerminalPosition(col + 10, 15),
                         new TerminalSize(14, 5),
                         Symbols.BOLD_FROM_NORMAL_SINGLE_LINE_VERTICAL);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.setBackgroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(33, 14, "SCORE");
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(35, 17, String.valueOf(score.getScore()));

        //Holdshape box
        tg.setBackgroundColor(TextColor.ANSI.MAGENTA);
        tg.setForegroundColor(TextColor.ANSI.BLACK);
        tg.putString(63, 2, "HOLDED SHAPE");
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.drawRectangle(new TerminalPosition(col + 40, 4),
                         new TerminalSize(20, 7),
                         Symbols.BOLD_TO_NORMAL_SINGLE_LINE_VERTICAL);
        if(holdShape != null) {
            for (int i = 0; i < holdShape.getCurrentPos().length; i++) {
                for (int j = 0; j < holdShape.getCurrentPos()[0].length; j++) {
                    if(holdShape.getCurrentPos()[i][j] == 1) {
                        tg.setBackgroundColor(holdShape.getColor());
                        tg.putString(68+j, 6+i, " ");
                        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    }
                }
            }
        }
    }
}
