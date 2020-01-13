package com.bohemiamates.crcmngmt.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.activities.MainActivity;
import com.bohemiamates.crcmngmt.entities.Clan;
import com.bohemiamates.crcmngmt.other.PrefManager;
import com.bohemiamates.crcmngmt.repositories.ClanRepository;
import com.bumptech.glide.Glide;

import java.util.List;

public class ClanListAdapter extends RecyclerView.Adapter<ClanListAdapter.ClanViewHolder> {

    class ClanViewHolder extends RecyclerView.ViewHolder {
        private final TextView mClanName;
        private final TextView mClanTag;
        private final TextView mClanWarTime;
        private final ImageView mImageView;
        private final LinearLayout mLinearLayout;
        private final CardView mCardView;

        private ClanViewHolder(View itemView) {
            super(itemView);

            mClanName = itemView.findViewById(R.id.rv_clanname);
            mClanTag = itemView.findViewById(R.id.rv_clantag);
            mClanWarTime = itemView.findViewById(R.id.rv_clanwartime);
            mImageView = itemView.findViewById(R.id.rv_iconclansearch);
            mLinearLayout = itemView.findViewById(R.id.rv_clanlayout);
            mCardView = itemView.findViewById(R.id.rv_clancard);
        }
    }

    private final LayoutInflater mInflater;
    private List<Clan> mClans; // Cached copy of clans
    private Context mContext;

    public ClanListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ClanViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.recyclerview_item_clans, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mClanName = v.findViewById(R.id.rv_clanname);
                TextView mClanTag = v.findViewById(R.id.rv_clantag);
                TextView mClanWarTime = v.findViewById(R.id.rv_clanwartime);

                final long warTime = Long.valueOf(mClanWarTime.getText().toString());
                final String tag = mClanTag.getText().toString().substring(1);

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(mContext.getString(R.string.loadClan) + mClanName.getText() + "\"?");

                alert.setPositiveButton(Html.fromHtml("<font color='#2E7D32'>" + mContext.getString(R.string.load) + "</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        PrefManager prefManager = new PrefManager(mContext);
                        prefManager.setClanTag(tag);
                        prefManager.setClanWarTime(warTime);

                        Intent intent = new Intent(mContext, MainActivity.class);

                        mContext.startActivity(intent);
                    }
                });

                alert.setNegativeButton(mContext.getString(R.string.loadCancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView mClanName = v.findViewById(R.id.rv_clanname);
                TextView mClanTag = v.findViewById(R.id.rv_clantag);
                final String tag = mClanTag.getText().toString().substring(1);

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(mContext.getString(R.string.deleteClan) + mClanName.getText() + "\"?");

                alert.setPositiveButton(Html.fromHtml("<font color='#D50000'>" + mContext.getString(R.string.delete) + "</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new ClanRepository(mContext).delete(tag);
                    }
                });

                alert.setNegativeButton(mContext.getString(R.string.loadCancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
                return false;
            }
        });

        return new ClanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClanViewHolder holder, int position) {
        if (mClans != null) {
            final Clan current = mClans.get(position);
            PrefManager prefManager = new PrefManager(mContext);

            if (current.getTag().equals(prefManager.getClanTag())) {
                holder.itemView.setEnabled(false);
                holder.mLinearLayout.setBackgroundResource(R.drawable.selection_grad);
                holder.mCardView.setCardElevation(1);
            }

            Glide.with(mContext)
                    .load(current.getBadge().getImage())
                    .thumbnail(0.5f)
                    .into(holder.mImageView);

            String clanTag = "#" + current.getTag();

            holder.mClanName.setText(current.getName());
            holder.mClanTag.setText(clanTag);
            holder.mClanWarTime.setText(String.valueOf(current.getLastWarTime()));

        } else {
            // Covers the case of data not being ready yet.
            holder.mClanName.setText(R.string.noPlayers);
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mPlayers has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mClans != null)
            return mClans.size();
        else
            return 0;
    }

    public void setClans(List<Clan> clans) {
        mClans = clans;
        notifyDataSetChanged();
    }
}
