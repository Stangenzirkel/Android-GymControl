package stangenzirkel.gymcontrol.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import stangenzirkel.gymcontrol.R;
import stangenzirkel.gymcontrol.ui.exercises.Exercise;

public class HomeFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private ArrayList<HomeListElement> elements;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
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
        }

        initializeData();
        initializeAdapter();
        return view;
    }

    private void initializeData(){
        elements = new ArrayList<>();
        elements.add(new HomeListElement(new Exercise(0, "name 2", 50, "ic_baseline_fitness_center_24"), 100, 13));
        elements.add(new HomeListElement(new Exercise(0, "name 1", 100, "ic_baseline_fitness_center_24"), 100, 58));
    }

    private void initializeAdapter(){
        recyclerView.setAdapter(new HomeRecyclerViewAdapter(elements, this));
    }
}