package site.devsim.enjoyseoul.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import site.devsim.enjoyseoul.Adapter.ListViewAdapter;
import site.devsim.enjoyseoul.Adapter.MainPagerAdapter;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.SearchCondition;
import site.devsim.enjoyseoul.Util.SearchQueryBuilder;

public class SearchResultActivity extends AppCompatActivity {

    private static final int RE_SEARCH_REQUEST = 2;

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.btn_re_search)
    TextView btnReSearch;
    @BindView(R.id.listView)
    RecyclerView listView;

    SearchCondition condition;
    ArrayList<EventItem> eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        unpackIntent();
        initListView();
        initView();
    }

    private void unpackIntent(){
        Intent i = getIntent();
        condition = (SearchCondition)i.getSerializableExtra("condition");
    }

    private void initListView(){
        DBManager dbManager = new DBManager(this);
        String sql = SearchQueryBuilder.getSearchQuery(getResources().getString(R.string.event_table), condition);
        eventList = dbManager.runSql(sql);
        dbManager.close();
    }

    private void initView(){
        txtTotal.setText("총 " + eventList.size() + "건");

        ListViewAdapter mAdapter = new ListViewAdapter(this,eventList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case RE_SEARCH_REQUEST:
                    condition = (SearchCondition)data.getSerializableExtra("condition");
                    initListView();
                    initView();
                    break;
            }
        }
    }

    @OnClick(R.id.btn_re_search)
    void reSearchClicked(){
        Intent i = new Intent(this,SearchActivity.class);
        i.putExtra("condition", condition);
        startActivityForResult(i,RE_SEARCH_REQUEST);
    }

}
