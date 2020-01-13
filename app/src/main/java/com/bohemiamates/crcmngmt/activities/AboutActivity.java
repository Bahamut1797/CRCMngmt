package com.bohemiamates.crcmngmt.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bohemiamates.crcmngmt.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_about);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtDevel = findViewById(R.id.txtDeveloper);
        txtDevel.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtGlideGit = findViewById(R.id.glide_author);
        txtGlideGit.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtTooltipGit = findViewById(R.id.tooltip_author);
        txtTooltipGit.setMovementMethod(LinkMovementMethod.getInstance());

        ImageButton btnClose = findViewById(R.id.btn_close_about);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
