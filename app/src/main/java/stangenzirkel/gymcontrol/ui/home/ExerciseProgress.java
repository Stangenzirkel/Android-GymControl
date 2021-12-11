package stangenzirkel.gymcontrol.ui.home;

import stangenzirkel.gymcontrol.ui.exercises.Exercise;

public class ExerciseProgress {
    public Exercise exercise;
    public int id;
    public int date;
    public int progress;

    public ExerciseProgress(int id, Exercise exercise, int date, int progress) {
        this.exercise = exercise;
        this.date = date;
        this.progress = progress;
    }
}
