package model;

import static com.googlecode.lanterna.TextColor.ANSI.BLUE_BRIGHT;

public class LineShape extends AbstractShape {
    /*
                    * * * *
     */

    public LineShape() {
        this.positions = new int[][][] {
                {
                    { 1,1,1,1 }
                },

                {
                    { 1 },
                    { 1 },
                    { 1 },
                    { 1 }
                }
        };

        this.currentpos = positions[0];
        this.currentPosIndex = 0;
        this.colorValue = -4;
        this.color = BLUE_BRIGHT;
    }

    @Override
    public void rotate(){
        currentPosIndex += 1;

        if (currentPosIndex >= positions.length) { currentPosIndex = 0; }

        currentpos = positions[currentPosIndex];

        if(currentPosIndex == 1){
            col++;
            row--;
        } else if (currentPosIndex == 0) {
            col--;
            row++;
        }
    }
}
