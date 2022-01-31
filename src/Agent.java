package src;

public class Agent implements Comparable<Agent> {
    Board board;
    private Coord currLoc;
    private Direction currDir;
    private int score, turnScore;
    public int heuristic;
    public Agent(Board b, int x, int y) {
        board = b;
        currLoc = new Coord(x, y);
        currDir = Direction.UP;
        score = 0;
        turnScore = 0;
        heuristic = 0;
    }

    public Agent(Board b, int x, int y, int score, int turnScore) {
        board = b;
        currLoc = new Coord(x, y);
        currDir = Direction.UP;
        this.score = score;
        this.turnScore = turnScore;
        heuristic = 0;
    }

    /**
     * Creates a cloned agent with a different memory value.
     * @return an Agent with the exact same properties as the current Agent
     */
    public Agent clone(){
        return new Agent(board, currLoc.getX(), currLoc.getY(), score, turnScore);
    }

    public void moveForward() {
        int x = currLoc.getX(), y = currLoc.getY();
        switch(currDir) {
            case UP: // x stays the same
                y = currLoc.getY() - 1;
                break;
            case DOWN: // x stays the same
                y = currLoc.getY() + 1;
                break;
            case LEFT: // y stays the same
                x = currLoc.getX() - 1;
                break;
            case RIGHT: // y stays the same
                x = currLoc.getX() + 1;
                break;
        }
        currLoc.setX(x);
        currLoc.setY(y);
        if(outOfBounds() == false)
            score -= board.getVal(currLoc);
        else
            score = Integer.MAX_VALUE;
    }
    //turn --> this could probably be optimized, just didn't want to do it right now
    public void turn(Turn t) {
        int currLocVal = board.getVal(currLoc);
        if(t == Turn.CLOCKWISE) {
            switch(currDir) {
                case UP:
                    currDir = Direction.RIGHT;
                    break;
                case DOWN:
                    currDir = Direction.LEFT;
                    break;
                case LEFT:
                    currDir = Direction.UP;
                    break;
                case RIGHT:
                    currDir = Direction.DOWN;
                    break;
            }
        }
        else { //counterclockwise
            switch(currDir) {
                case UP:
                    currDir = Direction.LEFT;
                    break;
                case DOWN:
                    currDir = Direction.RIGHT;
                    break;
                case LEFT:
                    currDir = Direction.DOWN;
                    break;
                case RIGHT:
                    currDir = Direction.UP;
                    break;
            }
        }
        turnScore -= (int)Math.ceil(currLocVal / 2.0);
    }
    //bash
    public void bash() {
        int x, y;
        switch(currDir) {
            case UP: // x stays the same
                y = currLoc.getY() - 1;
                currLoc.setY(y);
                break;
            case DOWN: // x stays the same
                y = currLoc.getY() + 1;
                currLoc.setY(y);
                break;
            case LEFT: // y stays the same
                x = currLoc.getX() - 1;
                currLoc.setX(x);
                break;
            case RIGHT: // y stays the same
                x = currLoc.getX() + 1;
                currLoc.setX(x);
                break;
        }
        score -= 3;
        moveForward();
    }

    public Coord getCurrLoc() {
        return currLoc;
    }

    public Direction getCurrDir() {
        return currDir;
    }

    public int getScore() {
        return score;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public int getFinalScore() {
        return score + (turnScore*2);
    }

    /**
     * Checks if the agent is still in the board or not
     * @return True if off the board, false if not
     */
    public boolean outOfBounds(){
        if(currLoc.getX()>=board.getBoard()[0].length || currLoc.getY()>=board.getBoard().length || currLoc.getX() <0 || currLoc.getY() <0)
            return true;
        else
            return false;
    }

    /**
     * Compares the heuristic values of two agents
     * @param o second agent to compare to.
     * @return
     */
    @Override
    public int compareTo(Agent o) {
        return o.heuristic-heuristic;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "currLoc=" + currLoc +
                ", currDir=" + currDir +
                ", score=" + score +
                ", turnScore=" + turnScore +
                '}';
    }

//Extra Credit: demolish
}
