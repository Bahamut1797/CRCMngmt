package com.bohemiamates.crcmngmt.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.adapters.BattleLogAdapter;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.viewModels.PlayerViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayerInfoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public BattleLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_player_info);

        final ImageView imageView = findViewById(R.id.inf_iconclan);
        final TextView txtName = findViewById(R.id.inf_playername);
        final TextView txtTag = findViewById(R.id.inf_playertag);
        final TextView txtRole = findViewById(R.id.inf_playerrole);
        final ImageView imageViewArena = findViewById(R.id.inf_iconarena);
        final TextView txtNameArena = findViewById(R.id.inf_arena);
        final TextView txtDonations = findViewById(R.id.inf_playerDonations);
        final TextView txtReceived = findViewById(R.id.inf_playerReceived);
        final TextView txtPercentage = findViewById(R.id.inf_playerPercentage);
        final TextView txtWinsMonth = findViewById(R.id.inf_playerWinsMonth);
        final TextView txtTotalWins = findViewById(R.id.inf_playerTotalWins);
        final TextView txtLossesMonth = findViewById(R.id.inf_playerLossesMonth);
        final TextView txtTotalLosses = findViewById(R.id.inf_playerTotalLosses);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenHeigth = (int) (metrics.heightPixels * 0.90);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, screenHeigth);

        final String playerTag = getIntent().getStringExtra("TAG").substring(1);

        // RecycleView with Adapter
        recyclerView = findViewById(R.id.recyclerViewBattles);
        adapter = new BattleLogAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlayerViewModel mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerViewModel.getPlayer(playerTag).observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable final Player player) {
                if (player != null) {
                    Glide.with(getApplicationContext())
                            .load(player.getClanBadgeUri())
                            .thumbnail(0.5f)
                            .into(imageView);

                    String tag = "#" + player.getTag();

                    txtName.setText(player.getName());

                    if (player.getName().length() > 14)
                        txtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16) ;
                    else if (player.getName().length() > 12)
                        txtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                    txtTag.setText(tag);

                    switch (player.getRole()) {
                        case "leader":
                            txtRole.setText(R.string.leader);
                            break;
                        case "coLeader":
                            txtRole.setText(R.string.coLeader);
                            break;
                        case "elder":
                            txtRole.setText(R.string.elder);
                            break;
                        case "member":
                            txtRole.setText(R.string.member);
                            break;
                    }

                    switch (player.getArena().getId()) {
                        case 0:
                            imageViewArena.setImageResource(R.drawable.arena0);
                            break;
                        case 1:
                            imageViewArena.setImageResource(R.drawable.arena1);
                            break;
                        case 2:
                            imageViewArena.setImageResource(R.drawable.arena2);
                            break;
                        case 3:
                            imageViewArena.setImageResource(R.drawable.arena3);
                            break;
                        case 4:
                            imageViewArena.setImageResource(R.drawable.arena4);
                            break;
                        case 5:
                            imageViewArena.setImageResource(R.drawable.arena5);
                            break;
                        case 6:
                            imageViewArena.setImageResource(R.drawable.arena6);
                            break;
                        case 7:
                            imageViewArena.setImageResource(R.drawable.arena7);
                            break;
                        case 8:
                            imageViewArena.setImageResource(R.drawable.arena8);
                            break;
                        case 9:
                            imageViewArena.setImageResource(R.drawable.arena9);
                            break;
                        case 10:
                            imageViewArena.setImageResource(R.drawable.arena10);
                            break;
                        case 11:
                            imageViewArena.setImageResource(R.drawable.arena11);
                            break;
                        case 12:
                            imageViewArena.setImageResource(R.drawable.arena12);
                            break;
                        case 13:
                            imageViewArena.setImageResource(R.drawable.arena13);
                            break;
                        case 14:
                            imageViewArena.setImageResource(R.drawable.arena14);
                            break;
                        case 15:
                            imageViewArena.setImageResource(R.drawable.arena15);
                            break;
                        case 16:
                            imageViewArena.setImageResource(R.drawable.arena16);
                            break;
                        case 17:
                            imageViewArena.setImageResource(R.drawable.arena17);
                            break;
                        case 18:
                            imageViewArena.setImageResource(R.drawable.arena18);
                            break;
                        case 19:
                            imageViewArena.setImageResource(R.drawable.arena19);
                            break;
                        case 20:
                            imageViewArena.setImageResource(R.drawable.arena20);
                            break;
                        case 21:
                            imageViewArena.setImageResource(R.drawable.arena21);
                            break;
                        case 22:
                            imageViewArena.setImageResource(R.drawable.arena22);
                            break;
                    }

                    txtNameArena.setText(player.getArena().getName());

                    txtDonations.setText(String.valueOf(player.getDonations()));
                    txtReceived.setText(String.valueOf(player.getDonationsReceived()));

                    String percent = "%" + player.getDonationsPercent();
                    txtPercentage.setText(percent);

                    txtWinsMonth.setText(String.valueOf(player.getTotalWinsMonth()));
                    txtTotalWins.setText(String.valueOf(player.getTotalWins()));
                    txtLossesMonth.setText(String.valueOf(player.getTotalFailsMonth()));
                    txtTotalLosses.setText(String.valueOf(player.getTotalFails()));

                    ArrayList<String> list = new ArrayList<>(Collections.singletonList("N/A:N/A:N/A"));

                    if (!player.getBattleLog().equals("")) {
                        list = new ArrayList<>(Arrays.asList(player.getBattleLog().split("[|]")));
                    }

                    adapter.setBattles(list);
                }
            }
        });

        ImageButton btnClose = findViewById(R.id.btnCloseInfo);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
