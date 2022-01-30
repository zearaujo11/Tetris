package model;

import com.googlecode.lanterna.TextColor;

public abstract class AbstractShape {
    protected int[][][] positions;
    protected int[][] currentpos;
    protected int currentPosIndex = 0;
    protected int row = 0;
    protected int col = 9;
    protected TextColor.ANSI color;
    protected int colorValue;

    /**
     * Method to copy every attribute of current AbstractShape to other shape
     * @param shape
     */
    public void copy(AbstractShape shape){
        this.col = shape.col;
        this.row = shape.row;
        this.positions = shape.positions;
        this.currentpos = shape.currentpos;
        this.currentPosIndex = shape.currentPosIndex;
    }

    //Getters
    public TextColor.ANSI getColor() { return color; }

    public int getColorValue() { return colorValue; }

    public int getCentreCol() { return col; }

    public int getCentreRow() { return row; }

    public int[][] getCurrentPos() { return currentpos; }

    public int[][][] getPositions() { return positions; }

    public int getCurrentPosIndex() { return currentPosIndex; }

    //Setters
    public void setColor(TextColor.ANSI color) { this.color = color; }

    public void setColorValue(int colorValue) { this.colorValue = colorValue; }

    public void setCoordinates(int col, int row) {
        this.col = col;
        this.row = row;
    }

    //Methods
    /**
     * Method to rotate a shape in Map
     */
    public void rotate() {
        currentPosIndex += 1;

        if (currentPosIndex >= positions.length) { currentPosIndex = 0; }

        currentpos = positions[currentPosIndex];
    }

    /**
     * Method to move current shape left in Map
     */
    public void moveLeft() { this.col--; }

    /**
     * Method to move current shape right in Map
     */
    public void moveRight() { this.col++; }
}
