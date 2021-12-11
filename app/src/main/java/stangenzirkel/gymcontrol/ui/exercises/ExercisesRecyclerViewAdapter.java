package stangenzirkel.gymcontrol.ui.exercises;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stangenzirkel.gymcontrol.ExerciseDBHelper;
import stangenzirkel.gymcontrol.R;

public class ExercisesRecyclerViewAdapter extends RecyclerView.Adapter<ExercisesRecyclerViewAdapter.ExerciseViewHolder> {
    private final ExercisesFragment exercisesFragment;
    String tag = "ExercisesRecyclerViewAdapterTag";

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            AdapterView.OnClickListener,
            MenuItem.OnMenuItemClickListener {
        String tag = "ExerciseViewHolderTag";
        final int DELETE = 1, EDIT = 2;

        CardView cv;
        ImageView icon;
        TextView headerTV;
        TextView bodyTV;

        Exercise exercise;
        ExercisesRecyclerViewAdapter exercisesRecyclerViewAdapter;

        ExerciseViewHolder(View itemView, ExercisesRecyclerViewAdapter exercisesRecyclerViewAdapter) {
            super(itemView);
            this.exercisesRecyclerViewAdapter = exercisesRecyclerViewAdapter;
            cv = itemView.findViewById(R.id.cv_exercise);
            icon = itemView.findViewById(R.id.cv_icon);
            headerTV = itemView.findViewById(R.id.cv_header);
            bodyTV = itemView.findViewById(R.id.cv_target);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
            Log.d(tag, itemView.toString());
            // setExercise(exercise);
        }

        private void setData(Exercise data) {
            this.exercise = data;
            icon.setImageResource(
                            itemView.getResources().getIdentifier(
                                    data.icon,
                                    "drawable",
                                    itemView.getContext().getPackageName()));
            headerTV.setText(data.name);
            bodyTV.setText(Integer.toString(data.goal));
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d(tag, "onMenuItemClick exercise = " + exercise.toString());
            switch (item.getItemId()) {
                case (DELETE):
                    ExerciseDBHelper.getInstance().deleteExercise(exercise.id);
                    exercisesRecyclerViewAdapter.exercisesFragment.update();
                    break;
                case (EDIT):
                    exercisesRecyclerViewAdapter.exercisesFragment.editExercise(exercise);
                    exercisesRecyclerViewAdapter.exercisesFragment.update();
                    break;
            }

            return false;
        }

        @Override
        public void onClick(View v) {
            Log.d(tag, "onClick exercise = " + exercise.toString() + ", v = " + v.toString());
            exercisesRecyclerViewAdapter.exercisesFragment.editExercise(exercise);
            exercisesRecyclerViewAdapter.exercisesFragment.update();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Log.d(tag, "onCreateContextMenu exercise = " + exercise.toString());
            menu.add(0, DELETE, 0, "Delete").setOnMenuItemClickListener(this);
            menu.add(0, EDIT, 0, "Edit").setOnMenuItemClickListener(this);
        }
    }

    List<Exercise> exercises;

    ExercisesRecyclerViewAdapter(List<Exercise> exercises, ExercisesFragment exercisesFragment){
        this.exercises = exercises;
        this.exercisesFragment = exercisesFragment;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_exercises_list_item, viewGroup, false);
        return new ExerciseViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.setData(exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}