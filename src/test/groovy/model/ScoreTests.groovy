package model

import spock.lang.Specification

class ScoreTests extends Specification {

    def "Verificar se alteramos o Score" () {
        given:
            Score score = new Score()
            int initialScore = score.getScore()

        when:
            score.setScore(155)

        then:
            initialScore != score.getScore()
    }

    def "Verificar se adicionamos corretamente de acordo com o nr de linhas apagada" () {
        given:
            Score score = new Score()

        when:
            score.clearLineScore(1) // 0 + 100 = 100
            score.clearLineScore(2) // 100 + 300 = 400
            score.clearLineScore(3) // 400 + 500 = 900
            score.clearLineScore(4) // 900 + 800 = 1700

        then:
            score.getScore() == 1700
    }

    def "Verificar se softDrop esta a adicionar corretamente" () {
        given:
            Score score = new Score()

        when:
            score.softDrop(10)

        then:
            score.getScore() == 10
    }

    def "Verificar se hardDrop esta a adicionar corretamente" () {
        given:
            Score score = new Score()

        when:
            score.hardDrop(10)

        then:
            score.getScore() == 20
    }
}
