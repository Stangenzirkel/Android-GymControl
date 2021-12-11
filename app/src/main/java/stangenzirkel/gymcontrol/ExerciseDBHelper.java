package stangenzirkel.gymcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import stangenzirkel.gymcontrol.ui.exercises.Exercise;
import stangenzirkel.gymcontrol.ui.home.ExerciseProgress;

public class ExerciseDBHelper extends SQLiteOpenHelper {
    String tag = "ExerciseDBHelper";
    private static ExerciseDBHelper instance;

    public static void init(Context context) {
        instance = new ExerciseDBHelper(context);
    }

    public static ExerciseDBHelper getInstance() {
        return instance;
    }

    private ExerciseDBHelper(Context context) {
        super(context, "exerciseDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag, "onCreate database");
        // создаем таблицу с полями
        db.execSQL("create table exercises ("
                + "id integer primary key autoincrement,"
                + "name string,"
                + "goal integer,"
                + "icon string"
                + ");");

        db.execSQL("create table exercises_history ("
                + "id integer primary key autoincrement,"
                + "exercise_id integer,"
                + "date integer,"
                + "quantity integer,"
                + "foreign key ([exercise_id]) references \"exercises\" ([id]) on delete no action on update no action"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public ArrayList<Exercise> getExercises() {
        Log.d(tag, "getting exercises from db:");
        Cursor c =  getWritableDatabase().query("exercises", null, null, null, null, null, null);
        ArrayList<Exercise> exercises = new ArrayList<>();
        Log.d(tag, "Length = " + c.getCount());
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int targetColIndex = c.getColumnIndex("goal");
            int iconColIndex = c.getColumnIndex("icon");

            do {
                exercises.add(new Exercise(
                        c.getInt(idColIndex),
                        c.getString(nameColIndex),
                        c.getInt(targetColIndex),
                        c.getString(iconColIndex)));
            } while (c.moveToNext());
        }
        c.close();

        for (Exercise exercise: exercises) {
            Log.d(tag, exercise.toString());
        }

        return exercises;
    }

    public Exercise getExercise(int id) {
        Log.d(tag, "getExercises id = " + id);
        Cursor c =  getWritableDatabase().query("exercises", null, "id = " + id, null, null, null, null);
        Exercise exercise;
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int targetColIndex = c.getColumnIndex("goal");
            int iconColIndex = c.getColumnIndex("icon");

            exercise = new Exercise(
                        c.getInt(idColIndex),
                        c.getString(nameColIndex),
                        c.getInt(targetColIndex),
                        c.getString(iconColIndex));

            return exercise;
        }
        c.close();
        throw new SQLException("row not found");
    }

    public void addExercise(Exercise exercise) {
        Log.d(tag, "addExercise exercise = " + exercise.toString());
        ContentValues cv = new ContentValues();
        cv.put("name", exercise.name);
        cv.put("goal", exercise.goal);
        cv.put("icon", exercise.icon);
        getWritableDatabase().insert("exercises", null, cv);
    }

    public void deleteExercise(int id) {
        Log.d(tag, "deleteExercise id = " + id);
        getWritableDatabase().delete("exercises", "id = " + id, null);
        getWritableDatabase().delete("exercises_history", "exercise_id = " + id, null);
    }

    public void updateExercise(Exercise exercise) {
        Log.d(tag, "updateExercise exercise = " + exercise.toString());
        ContentValues cv = new ContentValues();
        cv.put("name", exercise.name);
        cv.put("goal", exercise.goal);
        cv.put("icon", exercise.icon);
        getWritableDatabase().update("exercises", cv, "id = " + exercise.id, null);
    }

    public ExerciseProgress getExerciseProgress(int exercise_id, int date, boolean create_empty) {
        Log.d(tag, "getExerciseProgress date = " + date + ", exercise_id = " + exercise_id);
        ExerciseProgress exerciseProgress;
        Exercise exercise = getExercise(exercise_id);
        Cursor c = getWritableDatabase().query("exercises_history", null, "date = " + date + " and exercise_id = " + exercise_id, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex("id");
                int quantityColIndex = c.getColumnIndex("quantity");

                exerciseProgress = new ExerciseProgress(c.getInt(idColIndex), exercise, date, c.getInt(quantityColIndex));
            } else if (create_empty) {
                ContentValues cv = new ContentValues();
                cv.put("exercise_id", exercise_id);
                cv.put("date", date);
                cv.put("quantity", 0);
                long id = getWritableDatabase().insert("exercises_history", null, cv);
                exerciseProgress = new ExerciseProgress((int) id, exercise, date, 0);
            } else {
                exerciseProgress = new ExerciseProgress(0, exercise, date, 0);
            }
            c.close();
            return exerciseProgress;
        }

    public ArrayList<ExerciseProgress> getAllExerciseProgresses(int date, boolean create_empty) {
        Log.d(tag, "getAllExerciseProgresses date = " + date);
        ArrayList<ExerciseProgress> exerciseProgresses = new ArrayList<>();
        ArrayList<Exercise> exercises = getExercises();
        for (Exercise exercise: exercises) {
            Cursor c = getWritableDatabase().query("exercises_history", null, "date = " + date + " and exercise_id = " + exercise.id, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex("id");
                int quantityColIndex = c.getColumnIndex("quantity");

                exerciseProgresses.add(new ExerciseProgress(c.getInt(idColIndex), exercise, date, c.getInt(quantityColIndex)));
            } else if (create_empty){
                ContentValues cv = new ContentValues();
                cv.put("exercise_id", exercise.id);
                cv.put("date", date);
                cv.put("quantity", 0);
                long id = getWritableDatabase().insert("exercises_history", null, cv);
                exerciseProgresses.add(new ExerciseProgress((int) id, exercise, date, 0));
            } else {
                exerciseProgresses.add(new ExerciseProgress(0, exercise, date, 0));
            }
            c.close();
        }
        return exerciseProgresses;
    }

    public void setProgress(ExerciseProgress exerciseProgress, int progress) {
        Log.d(tag, "setProgress exerciseProgress = " + exerciseProgress.toString() + ", progress = " + progress);
        ContentValues cv = new ContentValues();
        cv.put("quantity", progress);
        getWritableDatabase().update("exercises_history", cv, "id = " + exerciseProgress.id, null);
    }
}