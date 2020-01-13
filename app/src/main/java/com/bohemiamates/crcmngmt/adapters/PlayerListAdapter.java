package com.bohemiamates.crcmngmt.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPlayerName;
        private final TextView mPlayerTag;
        private final TextView mPlayerRole;
        private final TextView mPlayerTrophies;
        private final TextView mPlayerDonations;
        private final ImageView mImageView;
        private final ImageView mFail1;
        private final ImageView mFail2;
        private final ImageView mFail3;
        private final TextView mRank;
        private final LinearLayout mLinearLayout;

        private PlayerViewHolder(View itemView) {
            super(itemView);

            mPlayerName = itemView.findViewById(R.id.rv_playername);
            mPlayerTag = itemView.findViewById(R.id.rv_playertag);
            mImageView = itemView.findViewById(R.id.rv_iconclan);
            mPlayerRole = itemView.findViewById(R.id.rv_playerrole);
            mPlayerTrophies = itemView.findViewById(R.id.rv_playertrophies);
            mPlayerDonations = itemView.findViewById(R.id.rv_playerdonations);
            mFail1 = itemView.findViewById(R.id.fail_1);
            mFail2 = itemView.findViewById(R.id.fail_2);
            mFail3 = itemView.findViewById(R.id.fail_3);
            mRank = itemView.findViewById(R.id.rv_rank);
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

                Context c = v.getContext();
                Intent i = new Intent(c, PlayerInfoActivity.class);
                i.putExtra("TAG", mPlayerTag.getText().toString());
                c.startActivity(i);
            }
        });
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder holder, int position) {
        if (mPlayers != null) {
            final Player current = mPlayers.get(position);

            Glide.with(mContext)
                    .load(current.getClanBadgeUri())
                    .thumbnail(0.5f)
                    .into(holder.mImageView);

            String playerTag = "#" + current.getTag();

            holder.mPlayerName.setText(current.getName());

            if (current.getName().length() > 12)
                holder.mPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);

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

            holder.mPlayerTrophies.setText(String.valueOf(current.getTrophies()));
            holder.mPlayerDonations.setText(String.valueOf(current.getDonations()));

            holder.mFail1.setImageResource(0);
            holder.mFail2.setImageResource(0);
            holder.mFail3.setImageResource(0);

            holder.mFail1.setOnClickListener(null);
            holder.mFail2.setOnClickListener(null);
            holder.mFail3.setOnClickListener(null);

            switch (current.getClanFails()) {
                case 0:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.normal_grad);
                    break;
                case 1:
                    holder.mLinearLayout.setBackgroundResource(R.drawable.precaution_grad);
                    holder.mFail1.setImageResource(R.drawable.ic_close_24dp_red);

                    holder.mFail1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail1());

                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail1)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });

                    break;
                case 2:
                    //holder.mLinearLayout.setBackgroundResource(R.drawable.warning_grad);
                    holder.mLinearLayout.setBackgroundResource(R.drawable.precaution_grad);
                    holder.mFail1.setImageResource(R.drawable.ic_close_24dp_red);
                    holder.mFail2.setImageResource(R.drawable.ic_close_24dp_red);

                    holder.mFail1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail1());
                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail1)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });

                    holder.mFail2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail2());

                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail2)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });
                    break;
                default:
                    //holder.mLinearLayout.setBackgroundResource(R.drawable.danger_grad);
                    holder.mLinearLayout.setBackgroundResource(R.drawable.danger_grad);
                    holder.mFail1.setImageResource(R.drawable.ic_close_24dp_red);
                    holder.mFail2.setImageResource(R.drawable.ic_close_24dp_red);
                    holder.mFail3.setImageResource(R.drawable.ic_close_24dp_red);

                    holder.mFail1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail1());

                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail1)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });

                    holder.mFail2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail2());

                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail2)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });

                    holder.mFail3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(current.getDateFail3());

                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                            String formatted = format1.format(c.getTime());

                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(holder.mFail3)
                                    .text(formatted)
                                    .gravity(Gravity.TOP)
                                    .transparentOverlay(true)
                                    .build()
                                    .show();
                        }
                    });
            }

            holder.mRank.setText(String.valueOf(current.getRank()));
            holder.mRank.setOnClickListener(null);
            holder.mPlayerTrophies.setOnClickListener(null);
            holder.mPlayerDonations.setOnClickListener(null);

            switch (current.getRank()) {
                case 1:
                    holder.mRank.setBackgroundResource(R.drawable.first_place);
                    break;
                case 2:
                    holder.mRank.setBackgroundResource(R.drawable.second_place);
                    break;
                case 3:
                    holder.mRank.setBackgroundResource(R.drawable.third_place);
                    break;
                default:
                    holder.mRank.setBackgroundResource(R.drawable.nth_place);
                    break;
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
        else
            return 0;
    }

    public void setPlayers(List<Player> players) {
        mPlayers = players;
        notifyDataSetChanged();
    }
}
