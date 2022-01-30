package control

import spock.lang.Specification

class GameTests extends Specification {

    def "Verificar se o jogo pode ou nao continuar"() {
        given:
            Game game = new Game()
            game.map.mapMatrix[0] = [0,0,0,0,0,0,0,0,-2,0,0,0,0,0,0,0,0,0]

        when:
            game.canPlay()

        then:
            game.getGameEnded()
    }

    def "Verificar se isFalling() funciona"(){
        given:
            Game game = new Game()
            game.fall = 400

        expect:
            game.isFalling()
    }
}
