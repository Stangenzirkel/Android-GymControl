package stangenzirkel.gymcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import stangenzirkel.gymcontrol.databinding.ActivityMainBinding;
import stangenzirkel.gymcontrol.ui.exercises.Exercise;

public class ExerciseActivity extends AppCompatActivity {
    int id = -1;
    EditText editTextName;
    EditText editTextGoal;
    Spinner iconSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        if (getIntent().getExtras() != null) {

        }

        editTextName = findViewById(R.id.et_name);
        editTextGoal = findViewById(R.id.et_goal);

        String[] icons = getResources().getStringArray(R.array.icons);
        iconSpinner = findViewById(R.id.icon_spinner);
        // Подключаем свой шаблон с значками
        MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.icon_row, icons);

        iconSpinner.setAdapter(adapter);
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {
        private String[] icons;
        public MyCustomAdapter(Context context, int textViewResourceId, String[] icons) {
            super(context, textViewResourceId, icons);
            this.icons = icons;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.icon_row, parent, false);
            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(getResources().getIdentifier(icons[position], "drawable", getContext().getPackageName()));

            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exercise_activity_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_check:
                if (id == -1) {
                    Exercise exercise = new Exercise(id,
                            editTextName.getText().toString(),
                            Integer.parseInt(editTextGoal.getText().toString()),
                            iconSpinner.getSelectedItem().toString());

                    new ExerciseDBHelper(this).addExercise(exercise);
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}