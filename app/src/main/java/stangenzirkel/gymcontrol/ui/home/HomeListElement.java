package stangenzirkel.gymcontrol.ui.home;

import stangenzirkel.gymcontrol.ui.exercises.Exercise;

public class HomeListElement {
    public Exercise exercise;
    public int date;
    public int progress;

    public float getPercent() {
        return progress / exercise.goal;
    }

    public HomeListElement(Exercise exercise, int date, int progress) {
        this.exercise = exercise;
        this.date = date;
        this.progress = progress;
    }
}
