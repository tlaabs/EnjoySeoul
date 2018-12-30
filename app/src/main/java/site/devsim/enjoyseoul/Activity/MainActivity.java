package site.devsim.enjoyseoul.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.devsim.enjoyseoul.Adapter.MainPagerAdapter;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Context context;

    @BindView(R.id.txt_app_title)
    TextView txtAppTitle;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTab;

    DBManager dbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        dbManager = new DBManager(this);
        initView();
    }

    private void initView(){
        //앱 타이틀 폰트 적용
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/dancing_bold.ttf");
        txtAppTitle.setTypeface(typeface);

        //ViewPager + TabLayout
        ArrayList<String> genreList = dbManager.getGenreNames();
        MainPagerAdapter mTestPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),genreList);
        mViewPager.setAdapter(mTestPagerAdapter);

        mTab.setupWithViewPager(mViewPager);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
