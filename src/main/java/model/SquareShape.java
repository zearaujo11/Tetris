package model;

import static com.googlecode.lanterna.TextColor.ANSI.YELLOW_BRIGHT;

public class SquareShape extends AbstractShape {
    /*
                       * *
                       * *
     */

    public SquareShape() {
        this.positions = new int[][][] {
                {       { 1, 1 },
                        { 1, 1 },
                }
        };

        this.currentpos = positions[0];
        this.currentPosIndex = 0;
        this.colorValue = -5;
        this.color = YELLOW_BRIGHT;
    }
}
