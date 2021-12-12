package stangenzirkel.gymcontrol.ui.statistic;

import com.softmoore.android.graphlib.Function;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import stangenzirkel.gymcontrol.Exercise;
import stangenzirkel.gymcontrol.ExerciseDBHelper;
import stangenzirkel.gymcontrol.R;

public class StatisticFragment extends Fragment {
    private GraphView graphView;

    public StatisticFragment() {
    }

    public static StatisticFragment newInstance() {
        StatisticFragment fragment = new StatisticFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        graphView = view.findViewById(R.id.graph_view);
        updateGraph();
        return view;
    }

    public void updateGraph() {
        Graph.Builder graphBuilder = new Graph.Builder()
                .setWorldCoordinates(-3, 33, -20, 220)
                .setAxes(0, 0)
                .setBackgroundColor(0xFF2B2B2B)
                .setAxesColor(Color.WHITE)
                .setXTicks(new double[] {5, 10, 15, 20, 25, 30})
                .setYTicks(new double[] {20, 40, 60, 80, 100, 120, 140, 160, 180, 200})
                .addFunction(x -> 100, Color.GREEN);
        int todayDate = (int) (Calendar.getInstance().getTimeInMillis() / 86400000);
        for (Exercise exercise: ExerciseDBHelper.getInstance().getExercises()) {
            Point[] points = new Point[30];
            for (int i = 0; i < 30; i++) {
                points[i] = new Point(i + 1,
                        ExerciseDBHelper.getInstance().getExerciseProgress(exercise.id, todayDate - 30 + i, false).progress * 100 / exercise.goal);
            }
            graphBuilder.addLineGraph(points, Color.RED);
        }
        graphView.setGraph(graphBuilder.build());
    }
}