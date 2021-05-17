package ihk.io;

import ihk.logik.Puzzle;

public class IOWrapper {
    private String comment;
    private Puzzle puzzle;

    public IOWrapper(String comment, Puzzle puzzle) {
        this.comment = comment;
        this.puzzle = puzzle;
    }

    public String getComment() {
        return comment;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
}
