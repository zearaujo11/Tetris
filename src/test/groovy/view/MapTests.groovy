package view

import model.JShape
import model.LShape
import model.LineShape
import model.SShape
import model.SquareShape
import model.TShape
import model.ZShape
import spock.lang.Specification

class MapTests extends Specification {

    def "Verificar se a ghostPiece esta correta"() {
        given:
            Map map = new Map()
            LShape lShape = new LShape()
            map.setCurrShape(lShape)
            map.getCurrShape().setCoordinates(8, 0)
            map.spawnShape()
            map.mapMatrix = [[0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]]
        when:
            map.ghostPiece()

        then:
            map.getMapMatrix()[17] == [0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0]
            map.getMapMatrix()[18] == [0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0]
    }

    def "Verificar se limpou linha"(){
        given:
            Map map = new Map()
            map.mapMatrix[1] = [-2,-1,-3,-4,-5,-6,-5,-4,-6,-2,-3,-1,-2,-3,-3,-2,-1,-1]

        when:
            map.clearLine()

        then:
            map.mapMatrix[1] == [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    }

    def "Verificar se se fez Tetris"(){
        given:
            Map map = new Map()
            map.mapMatrix[1] = [-2,-1,-3,-4,-5,-6,-5,-4,-6,-2,-3,-1,-2,-3,-3,-2,-1,-1]
            map.mapMatrix[2] = [-1,-1,-3,-4,-5,-6,-5,-4,-6,-2,-3,-1,-2,-3,-4,-2,-1,-1]
            map.mapMatrix[3] = [-2,-1,-3,-4,-5,-6,-5,-4,-6,-2,-3,-1,-2,-3,-4,-1,-3,-1]
            map.mapMatrix[4] = [-1,-1,-3,-4,-5,-6,-5,-4,-6,-2,-3,-1,-2,-3,-4,-2,-1,-1]

        when:
            map.clearLine()

        then:
            map.getMapMatrix()[1] == new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            map.getMapMatrix()[2] == new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            map.getMapMatrix()[3] == new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            map.getMapMatrix()[4] == new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    }


    def "Verificar se colidimos com outra shape"() {
        given:
            Map map = new Map()
            SShape sShape = new SShape()
            map.setCurrShape(sShape)
            map.spawnShape()
            map.getCurrShape().setCoordinates(0,0)
            map.updateMap()
            map.addShapeMap()
            TShape tShape = new TShape()
            map.setCurrShape(tShape)
            map.spawnShape()
            map.getCurrShape().setCoordinates(2,0)
            map.updateMap()

        expect:
            map.collisionShapes(2, map.getCurrShape())
    }

    def "Verificar se colidimos com outra shape ao rodar a peca"() {
        given:
            Map map = new Map()
            LineShape lineShape = new LineShape()
            map.setCurrShape(lineShape)
            map.spawnShape()
            lineShape.setCoordinates(17, 1)
            map.mapMatrix = [[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
                             [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]]

        when:
            map.collisionRotate()
            map.updateMap()

        then:
            map.mapMatrix[1] == [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]
    }

    def "Verificar se colidimos com a esquerda"() {
        given:
            Map map = new Map()
            SShape sShape = new SShape()
            map.setCurrShape(sShape)
            map.spawnShape()
            map.getCurrShape().setCoordinates(0,0)
            map.updateMap()

        expect:
            map.collisionLeft()
    }

    def "Verificar se colidimos direita"() {
        given:
            Map map = new Map()
            SShape sShape = new SShape()
            map.setCurrShape(sShape)
            map.spawnShape()
            map.getCurrShape().setCoordinates(map.getCol()-map.getCurrShape().getCurrentPos()[0].length-1 ,0)
            map.updateMap()

        expect:
            map.collisionRight()
    }

    def "Verificar se colidimos com o bottom"() {
        given:
            Map map = new Map()
            JShape jShape = new JShape()
            map.setCurrShape(jShape)
            map.spawnShape()
            jShape.setCoordinates(map.getCol()-9, map.getRow()-1)
            map.updateMap()

        expect:
            map.collisionBottom(jShape)
    }

    def "Verificar se alteramos a shape"() {
        given:
            Map map = new Map()
            LShape lShape = new LShape()
            LineShape lineShape = new LineShape()
            map.setCurrShape(lShape)
        when:
            map.setCurrShape(lineShape)

        then:
            map.getCurrShape() == lineShape
    }

    def "Verificar se movemos peca para baixo"() {
        given:
            Map map = new Map()
            ZShape zShape = new ZShape()
            map.setCurrShape(zShape)
            map.spawnShape()
            int initialRow = map.getCurrShape().getCentreRow()

        when:
            map.moveShapeDown()

        then:
            map.getCurrShape().getCentreRow() - initialRow == 1
    }

    def "Verificar se adicionamos Shape"() {
        given:
            Map map = new Map()
            SShape sShape = new SShape()
            map.setCurrShape(sShape)
            map.spawnShape()
            map.getCurrShape().setCoordinates(0,0)
            map.updateMap()

        when:
            map.addShapeMap()

        then:
            map.getMapMatrix()[0][1] < 0
            map.getMapMatrix()[0][2] < 0
            map.getMapMatrix()[1][1] < 0
            map.getMapMatrix()[1][0] < 0
    }


    def "Verificar se spawnamos a shape no sitio correto"() {
        given:
            Map map = new Map()
            SquareShape squareShape = new SquareShape()
            map.setCurrShape(squareShape)
            int[][] right = [[0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0],
                             [0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0]]

        when:
            map.spawnShape()

        then:
            map.mapMatrix[0][8] == right[0][8]
            map.mapMatrix[1][8] == right[1][8]
            map.mapMatrix[0][9] == right[0][9]
            map.mapMatrix[1][9] == right[1][9]
    }

    def "Verificar se alteramos a row"() {
        given:
            Map map = new Map()
            int initialRow = map.getRow()

        when:
            map.setRow(initialRow+1)

        then:
            initialRow != map.getRow()
    }

    def "Verificar se alteramos a coluna"() {
        given:
            Map map = new Map()
            int initialCol = map.getCol()

        when:
            map.setCol(initialCol+1)
        
        then:
            initialCol != map.getCol()
    }
}
