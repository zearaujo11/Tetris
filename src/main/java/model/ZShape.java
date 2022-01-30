package model;

import static com.googlecode.lanterna.TextColor.ANSI.RED;

public class ZShape extends AbstractShape {
    /*
                       * *
                         * *
     */

    public ZShape() {
        this.positions = new int[][][] {
                {   { 1, 1, 0 },
                    { 0, 1, 1 } },

                {   {  0, 1 },
                    {  1, 1 },
                    {  1, 0 } }
        };

        this.currentpos = positions[0];
        this.currentPosIndex = 0;
        this.colorValue = -1;
        this.color = RED;
    }
}
