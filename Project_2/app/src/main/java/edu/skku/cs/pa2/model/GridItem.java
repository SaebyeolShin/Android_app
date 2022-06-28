package edu.skku.cs.pa2.model;

public class GridItem {
    private boolean top;
    private boolean bottom;
    private boolean left;
    private boolean right;

    private boolean isInPeople;
    private boolean isInGoal;
    private boolean isInHint;

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isInPeople() {
        return isInPeople;
    }

    public void setInPeople(boolean inPeople) {
        isInPeople = inPeople;
    }

    public boolean isInGoal() {
        return isInGoal;
    }

    public void setInGoal(boolean inGoal) {
        isInGoal = inGoal;
    }

    public boolean isInHint() {
        return isInHint;
    }

    public void setInHint(boolean inHint) {
        isInHint = inHint;
    }

    @Override
    public String toString() {
        return "GridItem{" +
                "top=" + top +
                ", bottom=" + bottom +
                ", left=" + left +
                ", right=" + right +
                ", isInPeople=" + isInPeople +
                ", isInGoal=" + isInGoal +
                ", isInHint=" + isInHint +
                '}';
    }
}
