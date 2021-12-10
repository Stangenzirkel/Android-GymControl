package stangenzirkel.gymcontrol.ui.home;

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
import stangenzirkel.gymcontrol.ui.exercises.Exercise;
import stangenzirkel.gymcontrol.ui.exercises.ExercisesFragment;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    private final HomeFragment homeFragment;
    String tag = "HomeRecyclerViewAdapter";

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        String tag = "ExerciseViewHolderTag";

        CardView cv;
        HomeRecyclerViewAdapter homeRecyclerViewAdapters;

        HomeViewHolder(View itemView, HomeRecyclerViewAdapter homeRecyclerViewAdapters) {
            super(itemView);
            this.homeRecyclerViewAdapters = homeRecyclerViewAdapters;
            Log.d(tag, itemView.toString());
            // setExercise(exercise);
        }
    }

    List<String> strings;

    HomeRecyclerViewAdapter(List<String> strings, HomeFragment homeFragment){
        this.strings = strings;
        this.homeFragment = homeFragment;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragent_home_list_item, viewGroup, false);
        return new HomeViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder exerciseViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
}