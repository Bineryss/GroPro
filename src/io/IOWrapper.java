package io;

import logik.Puzzle;

public class IOWrapper {
    private String comment;
    private Puzzle puzzle;
    private String name;

    public IOWrapper(String comment, Puzzle puzzle, String name) {
        this.comment = comment;
        this.puzzle = puzzle;

        if (name.contains(".txt")) {
            this.name = name.replaceFirst("\\.txt", "");
        } else {
            this.name = name;
        }
    }

    public String getComment() {
        return comment;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public String getName() {
        return name;
    }
}
