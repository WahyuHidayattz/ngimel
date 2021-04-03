package wahyuapps.ngimel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wahyuapps.ngimel.adapters.AboutAdapter;
import wahyuapps.ngimel.datas.AboutData;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageButtonClose;
    private RecyclerView mRecyclerView;
    private ImageView mImageGithub, mImageInstagram, mImageEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mImageButtonClose = findViewById(R.id.image_button_close);
        mRecyclerView = findViewById(R.id.recycler_view_about);
        mImageGithub = findViewById(R.id.image_button_guithub);
        mImageInstagram = findViewById(R.id.image_button_instagram);
        mImageEmail = findViewById(R.id.image_button_email);

        mImageButtonClose.setOnClickListener(this);
        mImageInstagram.setOnClickListener(this);
        mImageGithub.setOnClickListener(this);
        mImageEmail.setOnClickListener(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<AboutData> data = new ArrayList<>();

        String[] titles = {"Nama Aplikasi", "Versi Aplikasi", "Developer", "Love"};
        String[] subtitles = {"Ngimel", "2.0", "Wahyu Hidayat", "Made with Love and Cup of Milk"};
        int[] images = {R.drawable.ic_outline_alternate_email_24, R.drawable.ic_baseline_notes_24, R.drawable.ic_outline_account_circle_24, R.drawable.ic_outline_favorite_border_24};

        for (int i = 0; i < titles.length; i++) {
            AboutData item = new AboutData();
            item.image = images[i];
            item.title = titles[i];
            item.subtitle = subtitles[i];
            data.add(item);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AboutActivity.this));
        mRecyclerView.setAdapter(new AboutAdapter(AboutActivity.this, data));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_close:
                finish();
                break;
            case R.id.image_button_guithub:
                link("https://github.com/WahyuHidayattz");
                break;
            case R.id.image_button_instagram:
                link("https://instagram.com/wahyu.h135");
                break;
            case R.id.image_button_email:
                try {
                    link("mailto:wahyuhidayatsmk2@gmail.com");
                } catch (Exception e) {
                    Toast.makeText(AboutActivity.this, "Aplikasi Email tidak ditemukan", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void link(String link) {
        Uri url = Uri.parse(link);
        Intent i = new Intent(Intent.ACTION_VIEW, url);
        startActivity(i);
    }

}
