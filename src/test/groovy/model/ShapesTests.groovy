package model

import com.googlecode.lanterna.TextColor
import spock.lang.Specification

class ShapesTests extends Specification {

    def "Verificar se altera a cor da Shape"() {
        given:
            LShape lShape = new LShape()
            TextColor.ANSI initialColorL = lShape.getColor()
            SquareShape squareShape = new SquareShape()
            TextColor.ANSI initialColorSquare = lShape.getColor()

        when:
            lShape.setColor(TextColor.ANSI.BLUE_BRIGHT)
            squareShape.setColor(TextColor.ANSI.BLUE_BRIGHT)

        then:
            initialColorL != lShape.getColor()
            initialColorSquare != squareShape.getColor()
    }

    def "Verificar se alteramos colorValue"() {
        given:
            JShape jShape = new JShape()
            int initialValueJ = jShape.getColorValue()
            SShape sShape = new SShape()
            int initialValueS = sShape.getColorValue()

        when:
            jShape.setColorValue(initialValueJ-1)
            sShape.setColorValue(initialValueS-1)

        then:
            initialValueJ != jShape.getColorValue()
            initialValueS != sShape.getColorValue()
    }

    def "Verificar se alteramos coordenadas"() {
        given:
            LineShape lineShape = new LineShape()
            int initialColLine = lineShape.getCentreCol()
            int initialRowLine = lineShape.getCentreRow()
            ZShape zShape = new ZShape()
            int initialColZ = zShape.getCentreCol()
            int initialRowZ = zShape.getCentreRow()

        when:
            lineShape.setCoordinates(initialColLine+1, initialRowLine+1)
            zShape.setCoordinates(initialColZ+1, initialRowZ+1)

        then:
            initialColLine != lineShape.getCentreCol()
            initialRowLine != lineShape.getCentreRow()
            initialColZ != zShape.getCentreCol()
            initialRowZ != zShape.getCentreRow()
    }

    def "Verificar se rotate retorna nova pos da currShape"() {
        given:
            TShape tShape = new TShape()
            int currPos1 = tShape.getCurrentPosIndex()

        when:
            tShape.rotate()

        then:
            1 == tShape.getCurrentPosIndex() - currPos1
    }
}