package site.devsim.enjoyseoul.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.ColorGradientUtil;
import site.devsim.enjoyseoul.Util.PureImgSrcUtil;

public class DetailEventActivity extends AppCompatActivity {
    @BindView(R.id.img_main)
    ImageView imgMain;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.alert_genre)
    TextView alertGenre;
    @BindView(R.id.alert_fee)
    TextView alertFee;

    //detail
    @BindView(R.id.period)
    TextView period;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.target)
    TextView target;
    @BindView(R.id.fee)
    TextView fee;
    @BindView(R.id.inquiry)
    TextView inquiry;

    //menu
    @BindView(R.id.btn_link)
    LinearLayout btnLink;
    @BindView(R.id.btn_like)
    LinearLayout btnLike;

    @BindView(R.id.favor_img)
    ImageView favorImg;


    private EventItem eventItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        eventItem = (EventItem)getIntent().getSerializableExtra("event");
        initView();
    }

    private void initView(){
        txtTitle.setText(eventItem.getTitle());
        alertGenre.setText(eventItem.getGenre());
        String isFree = eventItem.getIsFree();
        if(isFree.equals("0")) {//유료
            alertFee.setText("유료");
        }else if(isFree.equals("1")){//무료
            alertFee.setText("무료");
        }
        Glide
             .with(this)
             .load(eventItem.getMainImg())
             .into(imgMain);
        ColorGradientUtil.applyGenreColorGradient(this,eventItem.getGenre(),alertGenre);
        ColorGradientUtil.applyFeeColorGradient(this,eventItem.getIsFree(),alertFee);

        initDetail();
        initMenu();
    }

    private void initDetail(){
        period.setText(eventItem.getStartDate() + " ~ " + eventItem.getEndDate());
        time.setText(eventItem.getTime());
        place.setText(eventItem.getPlace());
        target.setText(eventItem.getTarget());
        fee.setText(eventItem.getFee());
        inquiry.setText(eventItem.getInquiry());
    }

    private void initMenu(){
        DBManager dbManager = new DBManager(this);
        if (dbManager.isLikeExist(eventItem.getId())) {
            int color = ContextCompat.getColor(this, R.color.isfavor);
            favorImg.setColorFilter(color);
        } else {
            favorImg.setColorFilter(Color.parseColor("#ffffff"));
        }
        dbManager.close();
    }

    @OnClick(R.id.btn_link)
    void linkClicked(){
        String link;
        if(eventItem.getOrgLink().contains("http")) {
            link = eventItem.getOrgLink();
        }else{
            link = "http://"+eventItem.getOrgLink();
        }
        Uri uri = Uri.parse(link);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    @OnClick(R.id.btn_like)
    void likeClicked(){
        DBManager dbManager = new DBManager(this);
        dbManager.addToggleLike(eventItem.getId());
        dbManager.close();
        initMenu();
    }
}
