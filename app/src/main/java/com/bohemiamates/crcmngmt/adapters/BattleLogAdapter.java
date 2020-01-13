package com.bohemiamates.crcmngmt.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohemiamates.crcmngmt.R;

import java.util.List;

public class BattleLogAdapter extends RecyclerView.Adapter<BattleLogAdapter.BattleLogViewHolder> {

    class BattleLogViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDate;
        private final TextView mBattles;
        private final TextView mWins;
        private final TextView mLosses;
        private final LinearLayout mLayout;

        private BattleLogViewHolder(View itemView) {
            super(itemView);

            mDate = itemView.findViewById(R.id.rv_date);
            mBattles = itemView.findViewById(R.id.rv_battles);
            mWins = itemView.findViewById(R.id.rv_wins);
            mLosses = itemView.findViewById(R.id.rv_losses);
            mLayout = itemView.findViewById(R.id.rv_infoPlayerLayout);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> mBattles; // Cached copy of clans

    public BattleLogAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BattleLogViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.recyclerview_item_battles, parent, false);

        return new BattleLogViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final BattleLogViewHolder holder, int position) {
        if (mBattles != null) {
            final String current = mBattles.get(position);

            if (!current.split(":")[0].equals("N/A")) {
                String date = current.split(":")[0];
                int battles = Integer.parseInt(current.split(":")[1]);
                int wins = Integer.parseInt(current.split(":")[2]);
                int losses = battles - wins;

                holder.mLayout.setBackgroundResource(R.drawable.normal_grad);
                if (battles == 0) {
                    holder.mLayout.setBackgroundResource(R.drawable.precaution_grad);
                }

                holder.mDate.setText(date);
                holder.mBattles.setText(String.valueOf(battles));
                holder.mWins.setText(String.valueOf(wins));
                holder.mLosses.setText(String.valueOf(losses));
            } else {
                holder.mDate.setText(current.split(":")[0]);
                holder.mBattles.setText(current.split(":")[0]);
                holder.mWins.setText(current.split(":")[0]);
                holder.mLosses.setText(current.split(":")[0]);
            }


        } else {
            // Covers the case of data not being ready yet.
            holder.mDate.setText("No Data");
            holder.mBattles.setText("No Data");
            holder.mWins.setText("No Data");
            holder.mLosses.setText("No Data");
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mBattles has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mBattles != null)
            return mBattles.size();
        else
            return 0;
    }

    public void setBattles(List<String> battles) {
        mBattles = battles;
        notifyDataSetChanged();
    }
}
