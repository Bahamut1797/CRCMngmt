package com.bohemiamates.crcmngmt.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bohemiamates.crcmngmt.R;
import com.bohemiamates.crcmngmt.entities.Player;
import com.bohemiamates.crcmngmt.viewModels.PlayerViewModel;

public class PlayerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_player_info);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final String playerTag = getIntent().getStringExtra("TAG").substring(1);

        PlayerViewModel mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerViewModel.getPlayer(playerTag).observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable final Player player) {
                if (player != null) {
                    Toast.makeText(getApplicationContext(), player.toString(), Toast.LENGTH_SHORT).show();
                    TextView txtName = findViewById(R.id.inf_playername);
                    txtName.setText(player.getName());
                }
            }
        });
    }
}
