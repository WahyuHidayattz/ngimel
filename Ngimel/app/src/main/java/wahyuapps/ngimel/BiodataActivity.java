package wahyuapps.ngimel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class BiodataActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mInputNama, mInputAlamat, mInputUmur, mInputPendidikan, mInputNoHp, mInputEmail;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private MaterialButton mBtnSimpan;
    private String nama, alamat, umur, pendidikan, nomor_hp, email;
    private Boolean mLaunchFrist;

    private TextView mTvErrorMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_biodata);

        mInputNama = findViewById(R.id.input_nama);
        mInputAlamat = findViewById(R.id.input_alamat);
        mInputUmur = findViewById(R.id.input_umur);
        mInputPendidikan = findViewById(R.id.input_pendidikan);
        mInputNoHp = findViewById(R.id.input_nomor_hp);
        mInputEmail = findViewById(R.id.input_email);
        mBtnSimpan = findViewById(R.id.button_simpan_biodata);
        mTvErrorMessage = findViewById(R.id.text_error_message);

        mPref = getSharedPreferences(MainActivity.TAG_NGIMEL, Context.MODE_PRIVATE);
        mEditor = mPref.edit();

        mBtnSimpan.setOnClickListener(this);
        loadBiodata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_simpan_biodata:
                simpanBiodata();
                break;
        }
    }

    private void simpanBiodata() {
        nama = mInputNama.getText().toString();
        alamat = mInputAlamat.getText().toString();
        umur = mInputUmur.getText().toString();
        pendidikan = mInputPendidikan.getText().toString();
        nomor_hp = mInputNoHp.getText().toString();
        email = mInputEmail.getText().toString();


        if (nama.isEmpty() || alamat.isEmpty() || umur.isEmpty() || pendidikan.isEmpty() || nomor_hp.isEmpty() || email.isEmpty()) {
            mTvErrorMessage.setVisibility(View.VISIBLE);
            mTvErrorMessage.setText("* Harap lengkapi biodata");
        } else {
            if (!email.contains("@") || !email.contains(".")) {
                mTvErrorMessage.setVisibility(View.VISIBLE);
                mTvErrorMessage.setText("* Format Email Salah");
            } else {
                mTvErrorMessage.setVisibility(View.INVISIBLE);

                mEditor.putString(MainActivity.TAG_DATA_NAMA, nama);
                mEditor.putString(MainActivity.TAG_DATA_ALAMAT, alamat);
                mEditor.putString(MainActivity.TAG_DATA_UMUR, umur);
                mEditor.putString(MainActivity.TAG_DATA_PENDIDIKAN, pendidikan);
                mEditor.putString(MainActivity.TAG_DATA_NOMOR_HP, nomor_hp);
                mEditor.putString(MainActivity.TAG_DATA_EMAIL, email);
                mEditor.commit();

                mLaunchFrist = mPref.getBoolean(MainActivity.TAG_LAUNCH_MAIN, false);
                if (mLaunchFrist == true) {
                    Intent i2 = new Intent();
                    setResult(1, i2);
                    finish();
                } else {
                    Intent i = new Intent(BiodataActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }
    }

    private void loadBiodata() {
        nama = mPref.getString(MainActivity.TAG_DATA_NAMA, "");
        alamat = mPref.getString(MainActivity.TAG_DATA_ALAMAT, "");
        umur = mPref.getString(MainActivity.TAG_DATA_UMUR, "");
        pendidikan = mPref.getString(MainActivity.TAG_DATA_PENDIDIKAN, "");
        nomor_hp = mPref.getString(MainActivity.TAG_DATA_NOMOR_HP, "");
        email = mPref.getString(MainActivity.TAG_DATA_EMAIL, "");

        mInputNama.setText(nama);
        mInputAlamat.setText(alamat);
        mInputUmur.setText(umur);
        mInputPendidikan.setText(pendidikan);
        mInputNoHp.setText(nomor_hp);
        mInputEmail.setText(email);
    }
}
