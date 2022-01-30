package model;

public class Score {
    private int score;

    //Constructor
    public Score() { this.score = 0; }

    //Getters
    public int getScore() { return this.score; }

    //Setters
    public void setScore(int score) { this.score = score; }

    //Methods
    /**
     * Method to add to score variable according to cleared lines
     * @param linesCleared
     */
    public void clearLineScore(int linesCleared) {
        switch(linesCleared) {
            case 1 -> this.score += 100;
            case 2 -> this.score += 300;
            case 3 -> this.score += 500;
            case 4 -> this.score += 800;
        }
    }

    /**
     * Method to add to score according to softDrop points
     * @param distance
     */
    public void softDrop(int distance) { this.score += (distance); }

    /**
     * Method to add to score according to hardDrop points
     * @param distance
     */
    public void hardDrop(int distance) { this.score += (2 * distance); }
}
