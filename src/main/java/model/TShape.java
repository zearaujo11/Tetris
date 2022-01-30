package model;

import static com.googlecode.lanterna.TextColor.ANSI.*;

public class TShape extends AbstractShape {
    /*
                         *
                       * * *
     */

    public TShape() {
        this.positions = new int[][][]{
                {       { 0, 1, 0 },
                        { 1, 1, 1 } },

                {       { 1, 0 },
                        { 1, 1 },
                        { 1, 0 } },

                {       { 1, 1, 1 },
                        { 0, 1, 0 } },

                {       { 0, 1 },
                        { 1, 1 },
                        { 0, 1 } }
        };

        this.currentpos = positions[0];
        this.currentPosIndex = 0;
        this.colorValue = -7;
        this.color = MAGENTA;


    }

    @Override
    public void rotate(){
        currentPosIndex += 1;

        if (currentPosIndex >= positions.length) { currentPosIndex = 0; }

        currentpos = positions[currentPosIndex];

        if(currentPosIndex == 1){
            col++;
        } else if (currentPosIndex == 2) {
            col--;
            row++;
        }else if(currentPosIndex == 3){
            row--;
        }
    }
}
