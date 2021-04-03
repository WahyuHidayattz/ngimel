package wahyuapps.ngimel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import wahyuapps.ngimel.adapters.HelpAdapter;
import wahyuapps.ngimel.datas.HelpData;
import wahyuapps.ngimel.dialogs.HelpDialog;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mInputBodyEmail;
    private MaterialToolbar mToolbar;
    private ActionBar mActionBar;
    private TextView mToolbarTitle;
    private ImageView mImageHomeButton;
    private MaterialButton mButtonSimpan, mButtonReset;

    private String text_email;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEdit;

    private RecyclerView mRecyclerView;
    private String defaultText, modifiedText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mPref = getSharedPreferences(MainActivity.TAG_NGIMEL, Context.MODE_PRIVATE);
        mEdit = mPref.edit();

        defaultText = getIntent().getStringExtra(MainActivity.TAG_DEFAULT_TEXT_EMAIL);
        modifiedText = mPref.getString(MainActivity.TAG_MODIFIED_TEXT_EMAIL, "");

        mInputBodyEmail = findViewById(R.id.input_edit_text);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mImageHomeButton = findViewById(R.id.image_button_home);
        mButtonSimpan = findViewById(R.id.button_simpan_text);
        mButtonReset = findViewById(R.id.button_reset_text);
        mRecyclerView = findViewById(R.id.recycler_view_help);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);

        mImageHomeButton.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonSimpan.setOnClickListener(this);
        mToolbarTitle.setText("Edit Body Email");

        if (!modifiedText.isEmpty()){
            mInputBodyEmail.setText(modifiedText);
        }else {
            mInputBodyEmail.setText(defaultText);
        }

        mImageHomeButton.setImageResource(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                HelpDialog.display(getSupportFragmentManager());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_home:
                onBackPressed();
                break;
            case R.id.button_simpan_text:
                saveEmailText();
                Intent i = new Intent();
                setResult(2, i);
                finish();
                break;
            case R.id.button_reset_text:
                text_email = mPref.getString(MainActivity.TAG_DEFAULT_TEXT_EMAIL, "kosong");
                mInputBodyEmail.setText(text_email);
                break;
        }
    }

    private void saveEmailText() {
        text_email = mInputBodyEmail.getText().toString();
        mEdit.putString(MainActivity.TAG_MODIFIED_TEXT_EMAIL, text_email);
        mEdit.commit();
        mInputBodyEmail.setText(mPref.getString(MainActivity.TAG_MODIFIED_TEXT_EMAIL, ""));
    }

}
