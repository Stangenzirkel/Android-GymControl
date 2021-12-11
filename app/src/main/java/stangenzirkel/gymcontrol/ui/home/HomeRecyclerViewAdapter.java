package stangenzirkel.gymcontrol.ui.home;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stangenzirkel.gymcontrol.ExerciseDBHelper;
import stangenzirkel.gymcontrol.ExerciseProgress;
import stangenzirkel.gymcontrol.R;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    private final HomeFragment homeFragment;
    String tag = "HomeRecyclerViewAdapterTag";

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String tag = "HomeViewHolderTag";

        CardView cv;
        TextView name;
        TextView percent;
        TextView goal;
        TextView progress;
        ImageView icon;
        ProgressBar progressBar;
        Button btnMinus;
        Button btnAdd;
        Button btnPlus;
        HomeRecyclerViewAdapter homeRecyclerViewAdapters;
        ExerciseProgress exerciseProgress;

        HomeViewHolder(View itemView, HomeRecyclerViewAdapter homeRecyclerViewAdapters) {
            super(itemView);
            this.homeRecyclerViewAdapters = homeRecyclerViewAdapters;
            cv = itemView.findViewById(R.id.cv_home);
            name = itemView.findViewById(R.id.tv_name);
            percent = itemView.findViewById(R.id.tv_progress_percent);
            goal = itemView.findViewById(R.id.tv_goal);
            progress = itemView.findViewById(R.id.tv_progress);
            icon = itemView.findViewById(R.id.cv_home_icon);
            progressBar = itemView.findViewById(R.id.cv_home_progressbar);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            Log.d(tag, itemView.toString());
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setData(ExerciseProgress data) {
            exerciseProgress = data;
            updateView();
        }

        public void updateView() {
            name.setText(exerciseProgress.exercise.name);
            percent.setText(exerciseProgress.progress * 100 / exerciseProgress.exercise.goal + "%");
            goal.setText(Integer.toString(exerciseProgress.exercise.goal));
            progress.setText(Integer.toString(exerciseProgress.progress));
            icon.setImageResource(
                    itemView.getResources().getIdentifier(
                            exerciseProgress.exercise.icon,
                            "drawable",
                            itemView.getContext().getPackageName()));
            progressBar.setMax(exerciseProgress.exercise.goal);
            progressBar.setProgress(exerciseProgress.progress);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_minus:
                    ExerciseDBHelper.getInstance().setProgress(exerciseProgress, exerciseProgress.progress - 10);
                    exerciseProgress.progress -= 10;
                    break;

                case R.id.btn_plus:
                    ExerciseDBHelper.getInstance().setProgress(exerciseProgress, exerciseProgress.progress + 10);
                    exerciseProgress.progress += 10;
                    break;

                case R.id.btn_add:
                    ExerciseDBHelper.getInstance().setProgress(exerciseProgress, exerciseProgress.progress + 10);
                    break;
            }
            homeRecyclerViewAdapters.homeFragment.initializeData();
            updateView();
        }
    }

    List<ExerciseProgress> elements;

    HomeRecyclerViewAdapter(List<ExerciseProgress> elements, HomeFragment homeFragment){
        this.elements = elements;
        this.homeFragment = homeFragment;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_home_list_item, viewGroup, false);
        return new HomeViewHolder(v, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(HomeViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.setData(elements.get(i));
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }
}