
package com.project.mayin.adapter;

//import static androidx.core.content.ContextCompat.getString;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mayin.R;
import com.project.mayin.model.Score;

import java.util.List;

public class Score_RecycleViewAdapter extends RecyclerView.Adapter<Score_RecycleViewAdapter.ViewHolder> {

    Context context;
    List<Score> scores;

   /* private ActionMode actionMode;
    private List<Score> selectedScores = new ArrayList<>();
    private ScoreDao scoreDao;
    private AppDatabase db;*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listScore;
        TextView listDifficulty;
        TextView listPlayerName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listScore = itemView.findViewById(R.id.listScore);
            listDifficulty = itemView.findViewById(R.id.listDifficulty);
            listPlayerName = itemView.findViewById(R.id.listPlayerName);
        }
    }
    public Score_RecycleViewAdapter(Context context, List<Score> scores){
        this.context = context;
        this.scores = scores;
    }

    @NonNull
    @Override
    public Score_RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view, parent, false);
        return new Score_RecycleViewAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull Score_RecycleViewAdapter.ViewHolder holder, int position) {

        Score score = scores.get(position);

        int scoreValue = score.score; // Puanın bir tam sayı olduğunu varsayıyoz
        holder.listPlayerName.setText(scores.get(position).playerName);
        holder.listDifficulty.setText(String.valueOf(scores.get(position).difficulty));
        String formattedScore = String.format("%,d", scoreValue);
        holder.listScore.setText(formattedScore);

    }

    @Override
    public int getItemCount() {
        return scores.size();
    }
}