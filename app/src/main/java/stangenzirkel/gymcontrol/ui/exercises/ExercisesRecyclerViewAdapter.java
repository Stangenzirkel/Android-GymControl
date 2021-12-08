package stangenzirkel.gymcontrol.ui.exercises;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import stangenzirkel.gymcontrol.R;

public class ExercisesRecyclerViewAdapter extends RecyclerView.Adapter<ExercisesRecyclerViewAdapter.ExerciseViewHolder> {
    String tag = "ExercisesRecyclerViewAdapterTag";

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder{
        String tag = "ExerciseViewHolderTag";

        CardView cv;
        ImageView icon;
        TextView headerTV;
        TextView bodyTV;

        Exercise exercise;

        ExerciseViewHolder(View itemView, Exercise exercise) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_exercise);
            icon = itemView.findViewById(R.id.cv_icon);
            headerTV = itemView.findViewById(R.id.cv_header);
            bodyTV = itemView.findViewById(R.id.cv_target);
            this.exercise = exercise;
        }

        public void setExercise(Exercise exercise) {
            icon.setImageResource(
                            itemView.getResources().getIdentifier(
                                    exercise.icon,
                                    "drawable",
                                    itemView.getContext().getPackageName()));
            headerTV.setText(exercise.name);
            bodyTV.setText(Integer.toString(exercise.target));
        }
    }

    List<Exercise> exercises;

    ExercisesRecyclerViewAdapter(List<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_exercises_list_item, viewGroup, false);
        return new ExerciseViewHolder(v, exercises.get(i));
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.setExercise(exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}