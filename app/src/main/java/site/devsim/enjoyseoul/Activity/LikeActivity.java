package site.devsim.enjoyseoul.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import site.devsim.enjoyseoul.Adapter.ListViewAdapter;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.SearchQueryBuilder;

public class LikeActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.listView)
    RecyclerView listView;

    ArrayList<EventItem> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.bind(this);

        initListView();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListView();
        initView();
    }

    private void initListView(){
        DBManager dbManager = DBManager.getInstance(this);
        eventList = dbManager.getLikes();
    }

    private void initView(){
        ListViewAdapter mAdapter = new ListViewAdapter(this,eventList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_back)
    void btnBackClicked(){
        finish();
    }
}
