package model;

import static com.googlecode.lanterna.TextColor.ANSI.GREEN_BRIGHT;

public class SShape extends AbstractShape {
    /*
                         * *
                       * *
     */

    public SShape() {
        this.positions = new int[][][] {
                {   { 0, 1, 1 },
                    { 1, 1, 0 } },

                {   { 1, 0 },
                    { 1, 1 },
                    { 0, 1 } }
        };

        this.currentpos = positions[0];
        this.currentPosIndex = 0;
        this.colorValue = -6;
        this.color = GREEN_BRIGHT;
    }
}
