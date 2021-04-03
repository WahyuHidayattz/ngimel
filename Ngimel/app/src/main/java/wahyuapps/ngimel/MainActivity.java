package wahyuapps.ngimel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String emailText;
    private TextView mTextPreview;
    private MaterialToolbar mToolbar;
    private ActionBar mActionBar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private int position;
    private ImageView mImageButtonHome, mImageRefresh, mImageButtonClose;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private BottomSheetBehavior mBottomSheet;
    private LinearLayout mBottomSheetLayout;

    private MaterialButton mButtonEmail, mbuttonEditText;

    private String email, subject, perusahaan;
    private String data_nama, data_umur, data_alamat, data_pendidikan, data_nomor_hp, data_email;
    private TextInputEditText mInputEmail, mInputSubject, mInputPerusahaan;

    public static final String TAG_NGIMEL = "ngimel_data";
    public static final String TAG_LAUNCH_MAIN = "launch_main_activity";


    public static final String TAG_DATA_NAMA = "data_nama";
    public static final String TAG_DATA_UMUR = "data_umur";
    public static final String TAG_DATA_ALAMAT = "data_alamat";
    public static final String TAG_DATA_PENDIDIKAN = "data_pendidikan";
    public static final String TAG_DATA_NOMOR_HP = "data_nomor_hp";
    public static final String TAG_DATA_EMAIL = "data_email";

    public static final String TAG_INPUT_EMAIL = "input_email";
    public static final String TAG_INPUT_SUBJECT = "input_subject";
    public static final String TAG_INPUT_PERUSAHAAN = "input_perusahaan";

    public static final String TAG_DEFAULT_TEXT_EMAIL = "default_text_email";
    public static final String TAG_MODIFIED_TEXT_EMAIL = "modified_text_email";

    private String defaultText, modifiedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // membuat shared pref datad
        mPref = getSharedPreferences(TAG_NGIMEL, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        mEditor.putBoolean(TAG_LAUNCH_MAIN, true);
        mEditor.commit();


        // default text email body
        defaultText = "Kepada Yth,\n" +
                "Bapak / Ibu HRD Recruitment.\n" +
                "Dengan Hormat,\n\n" +
                "Dengan Adanya Informasi Lowongan Pekerjaan di " + "$perusahaan" + ", Maka Saya berminat " +
                "untuk mengajukan lamaran. Berikut biodata singkat saya :\n\n" +
                "Nama : " + "$nama" + "\n" +
                "Umur : " + "$umur" + "\n" +
                "Alamat : " + "$alamat" + "\n" +
                "Pendidikan : " + "$pendidikan" + "\n" +
                "No HP : " + "$nomor_hp" + "\n" +
                "Email : " + "$email" + "\n\n" +
                "Sekian biodata singkat saya, besar harapan dapat di seleksi ke tahap berikutnya. " +
                "Sebagai bahan pertimbangan, Saya melampirkan CV dan Lamaran berformat Pdf. " +
                "Atas waktu dan perhatiannya, Saya ucapkan terima kasih.\n\n" +
                "Hormat Saya\n" +
                "$nama";

        // cek jika default email text sudah berubah

        mEditor.putString(TAG_DEFAULT_TEXT_EMAIL, defaultText);
        mEditor.commit();


        // content dan id
        mTextPreview = findViewById(R.id.text_preview);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigation_vview);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mImageButtonHome = findViewById(R.id.image_button_home);
        mImageRefresh = findViewById(R.id.image_button_refresh);
        mInputEmail = findViewById(R.id.input_email_tujuan);
        mInputSubject = findViewById(R.id.input_subject);
        mInputPerusahaan = findViewById(R.id.input_nama_operusahaan);
        mBottomSheetLayout = findViewById(R.id.bottom_sheet_bantuan);
        mImageButtonClose = findViewById(R.id.image_button_close);
        mButtonEmail = findViewById(R.id.button_email);
        mbuttonEditText = findViewById(R.id.button_edit_text);

        mButtonEmail.setOnClickListener(this);
        mbuttonEditText.setOnClickListener(this);

        mBottomSheet = BottomSheetBehavior.from(mBottomSheetLayout);
        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mImageButtonHome.setImageResource(R.drawable.ic_baseline_menu_24);

        // on click listener
        mImageButtonHome.setOnClickListener(this);
        mImageRefresh.setOnClickListener(this);
        mImageButtonClose.setOnClickListener(this);

        loadData();
        navigationDrawer();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_button_refresh:
                simpanData();
                break;
            case R.id.image_button_close:
                mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.button_email:
                kirimEmail();
                break;
            case R.id.button_edit_text:
                simpanData();
                Intent iEdit = new Intent(MainActivity.this, EditActivity.class);
                iEdit.putExtra(TAG_DEFAULT_TEXT_EMAIL, defaultText);
                startActivityForResult(iEdit, 2);
                break;
        }
    }


    private void simpanData() {
        email = mInputEmail.getText().toString();
        subject = mInputSubject.getText().toString();
        perusahaan = mInputPerusahaan.getText().toString();

        if (email.isEmpty() || subject.isEmpty() || perusahaan.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_LONG).show();
        } else {
            if (email.contains("@") || email.contains(".")) {
                mEditor.putString(TAG_INPUT_EMAIL, email);
                mEditor.putString(TAG_INPUT_SUBJECT, subject);
                mEditor.putString(TAG_INPUT_PERUSAHAAN, perusahaan);
                mEditor.commit();
            } else {
                Toast.makeText(MainActivity.this, "Harap masukan alamat email yang valid", Toast.LENGTH_LONG).show();
            }
        }
        loadData();
    }

    private void loadData() {
        email = mPref.getString(TAG_INPUT_EMAIL, "");
        subject = mPref.getString(TAG_INPUT_SUBJECT, "");
        perusahaan = mPref.getString(TAG_INPUT_PERUSAHAAN, "");

        data_nama = mPref.getString(TAG_DATA_NAMA, "Nama Lengkap");
        data_umur = mPref.getString(TAG_DATA_UMUR, "Umur");
        data_pendidikan = mPref.getString(TAG_DATA_PENDIDIKAN, "Pendidikan Sekolah");
        data_alamat = mPref.getString(TAG_DATA_ALAMAT, "Alamat Lengkap");
        data_nomor_hp = mPref.getString(TAG_DATA_NOMOR_HP, "Nomor HP");
        data_email = mPref.getString(TAG_DATA_EMAIL, "Alamat Email");

        mInputEmail.setText(email);
        mInputSubject.setText(subject);
        mInputPerusahaan.setText(perusahaan);

        emailText = "Kepada Yth,\n" +
                "Bapak / Ibu HRD Recruitment.\n" +
                "Dengan Hormat,\n\n" +
                "Dengan Adanya Informasi Lowongan Pekerjaan di " + perusahaan + ", Maka Saya berminat " +
                "untuk mengajukan lamaran. Berikut biodata singkat saya :\n\n" +
                "Nama : " + data_nama + "\n" +
                "Umur : " + data_umur + "\n" +
                "Alamat : " + data_alamat + "\n" +
                "Pendidikan : " + data_pendidikan + "\n" +
                "No HP : " + data_nomor_hp + "\n" +
                "Email : " + data_email + "\n\n" +
                "Sekian biodata singkat saya, besar harapan dapat di seleksi ke tahap berikutnya. " +
                "Sebagai bahan pertimbangan, Saya melampirkan CV dan Lamaran berformat Pdf. " +
                "Atas waktu dan perhatiannya, Saya ucapkan terima kasih.\n\n" +
                "Hormat Saya\n" +
                data_nama;
        modifiedText = mPref.getString(TAG_MODIFIED_TEXT_EMAIL, "");
        if (modifiedText != "") {
            mTextPreview.setText(modifiedText.replace("$nama", data_nama).replace("$umur", data_umur).replace("$alamat", data_alamat).replace("$pendidikan", data_pendidikan).replace("$nomor_hp", data_nomor_hp).replace("$email", data_email).replace("$perusahaan", perusahaan));
        } else {
            mTextPreview.setText(emailText);
        }
    }

    private void kirimEmail() {
        simpanData();
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, email);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, emailText);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Aplikasi Email Client tidak ditemukan", Toast.LENGTH_LONG).show();
        }
    }

    private void navigationDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_biodata:
                        position = 1;
                        break;
                    case R.id.menu_help:
                        position = 2;
                        break;
                    case R.id.menu_about:
                        position = 3;
                        break;
                    case R.id.menu_exit:
                        position = 4;
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                selectPosition(position);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerLayout.closeDrawers();
    }

    private void selectPosition(int pos) {
        position = 0;
        switch (pos) {
            case 1:
                Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
                startActivityForResult(intent, 1);
                break;
            case 2:
                mBottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                switch (mBottomSheet.getState()) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mBottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        break;
                }
                break;
            case 3:
                Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case 4:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED || mBottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED ||
                mBottomSheet.getState() == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                loadData();
                break;
            case 2:
                loadData();
                break;
        }
    }
}