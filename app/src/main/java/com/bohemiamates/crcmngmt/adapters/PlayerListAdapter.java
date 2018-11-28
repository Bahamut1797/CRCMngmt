package com.bohemiamates.crcmngmt.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.activities.PlayerInfoActivity;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bumptech.glide.Glide;

import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPlayerName;
        private final TextView mPlayerTag;
        private final TextView mPlayerRole;
        private final TextView mPlayerTrophies;
        private final TextView mPlayerDonations;
        private final TextView mPlayerFails;
        private final ImageView mImageView;
        private final LinearLayout mLinearLayout;

        private PlayerViewHolder(View itemView) {
            super(itemView);

            mPlayerName = itemView.findViewById(R.id.rv_playername);
            mPlayerTag = itemView.findViewById(R.id.rv_playertag);
            mImageView = itemView.findViewById(R.id.rv_iconclan);
            mPlayerRole = itemView.findViewById(R.id.rv_playerrole);
            mPlayerTrophies = itemView.findViewById(R.id.rv_playertrophies);
            mPlayerDonations = itemView.findViewById(R.id.rv_playerdonations);
            mPlayerFails = itemView.findViewById(R.id.rv_playerfails);
            mLinearLayout = itemView.findViewById(R.id.rc_background);
        }
    }

    private final LayoutInflater mInflater;
    private List<Player> mPlayers; // Cached copy of players
    private Context mContext;

    public PlayerListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mPlayerTag = v.findViewById(R.id.rv_playertag);
                Log.i("onClick", mPlayerTag.getText().toString());

                Context c = v.getContext();
                Intent i = new Intent(c, PlayerInfoActivity.class);
                i.putExtra("TAG", mPlayerTag.getText().toString());
                c.startActivity(i);
            }
        });
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        if (mPlayers != null) {
            Player current = mPlayers.get(position);

            Glide.with(mContext)
                    .load(current.getClanBadgeUri())
                    .thumbnail(0.5f)
                    .into(holder.mImageView);

            String playerTag = "#" + current.getTag();

            holder.mPlayerName.setText(current.getName());
            holder.mPlayerTag.setText(playerTag);

            switch (current.getRole()) {
                case "leader":
                    holder.mPlayerRole.setText(R.string.leader);
                    break;
                case "coLeader":
                    holder.mPlayerRole.setText(R.string.coLeader);
                    break;
                case "elder":
                    holder.mPlayerRole.setText(R.string.elder);
                    break;
                case "member":
                    holder.mPlayerRole.setText(R.string.member);
                    break;
            }

            String trophies = mContext.getResources().getString(R.string.txtTrophies) + " " + current.getTrophies();
            String donations = mContext.getResources().getString(R.string.txtDonations) + " " + current.getDonations();
            String fails = mContext.getResources().getString(R.string.txtFails) + " " + current.getClanFails();

            holder.mPlayerTrophies.setText(trophies);
            holder.mPlayerDonations.setText(donations);
            holder.mPlayerFails.setText(fails);

            switch (current.getClanFails()) {
                case 0:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.normal_grad);
                    break;
                case 1:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.precaution_grad);
                    break;
                case 2:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.warning_grad);
                    break;
                default:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.danger_grad);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.mPlayerName.setText(R.string.noPlayers);
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mPlayers has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPlayers != null)
            return mPlayers.size();
        else return 0;
    }

    public void setPlayers(List<Player> players){
        mPlayers = players;
        notifyDataSetChanged();
    }
}
