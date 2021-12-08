package stangenzirkel.gymcontrol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import stangenzirkel.gymcontrol.ui.exercises.Exercise;

public class ExerciseDBHelper extends SQLiteOpenHelper {
    String tag = "ExerciseDBHelper";

    public ExerciseDBHelper(Context context) {
        super(context, "exerciseDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag, "onCreate database");
        // создаем таблицу с полями
        db.execSQL("create table exercises ("
                + "id integer primary key autoincrement,"
                + "name string,"
                + "everyday_target integer,"
                + "icon string"
                + ");");

        db.execSQL("create table exercises_history ("
                + "id integer primary key autoincrement,"
                + "exercise_id integer,"
                + "date integer,"
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
            int targetColIndex = c.getColumnIndex("everyday_target");
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
}