package site.devsim.enjoyseoul.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.devsim.enjoyseoul.DB.DBInitiator;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.DB.JobFinishListener;
import site.devsim.enjoyseoul.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    Context context;

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        ButterKnife.bind(this);
        initView();
        initDB();
    }

    private void initView(){
        Glide
                .with(this)
                .load(R.drawable.splash_logo_512px)
                .into(imgLogo);
        //타이틀 폰트
        Typeface typeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.font_path));
        txtTitle.setTypeface(typeface);
    }

    private void initDB(){
        DBManager dbManager = DBManager.getInstance(this);
        dbManager.clearEventTable();
        DBInitiator initiator = new DBInitiator(this, new JobFinishListener() {
            @Override
            public void doJob() {
                Intent i = new Intent(context,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        initiator.init();

    }
}
