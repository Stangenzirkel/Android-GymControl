package stangenzirkel.gymcontrol.ui.exercises;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import stangenzirkel.gymcontrol.ExerciseActivity;
import stangenzirkel.gymcontrol.ExerciseDBHelper;
import stangenzirkel.gymcontrol.R;

public class ExercisesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    public ExercisesFragment() {
    }
    private ArrayList<Exercise> exercises = new ArrayList<>();
    public static ExercisesFragment newInstance(int columnCount) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        // updateExercises();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            initializeData();
            initializeAdapter();

        }
        return view;
    }


    private void initializeData(){
        exercises = ExerciseDBHelper.getInstance().getExercises();
    }

    private void initializeAdapter(){
        recyclerView.setAdapter(new ExercisesRecyclerViewAdapter(exercises, this));
    }

    public void update() {
        initializeData();
        initializeAdapter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.exercises_fragment_appbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_add_exercise) {
            Intent intent = new Intent(getActivity(), ExerciseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editExercise(Exercise exercise) {
        Intent intent = new Intent(getActivity(), ExerciseActivity.class);
        intent.putExtra("id", exercise.id);
        intent.putExtra("name", exercise.name);
        intent.putExtra("goal", exercise.goal);
        intent.putExtra("icon", exercise.icon);
        startActivity(intent);
    }
}