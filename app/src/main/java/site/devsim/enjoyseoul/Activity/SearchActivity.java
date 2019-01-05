package site.devsim.enjoyseoul.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import site.devsim.enjoyseoul.Activity.Dialog.GenrePickDialog;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.ColorGradientUtil;
import site.devsim.enjoyseoul.Util.SearchCondition;
import site.devsim.enjoyseoul.Util.SearchQueryBuilder;

public class SearchActivity extends AppCompatActivity {

    static final int PICK_GENRE_REQUEST = 1;

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.btn_pick_genre)
    Button btnPickGenre;
    @BindView(R.id.genre_list_box)
    FlexboxLayout genreListBox;

    @BindView(R.id.check_fee_all)
    Button checkFeeAll;
    @BindView(R.id.check_fee_not_free)
    Button checkFeeNotFree;
    @BindView(R.id.check_fee_free)
    Button checkFeeFree;

    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_search)
    Button btnSearch;

    private SearchCondition condition;

    private boolean isReSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        condition = new SearchCondition();
        initView();
        initIfReSearch();
    }

    private void initIfReSearch(){
        Intent i = getIntent();
        SearchCondition tmpCondition = (SearchCondition)i.getSerializableExtra("condition");
        if(tmpCondition == null) return;
        else{
            condition = tmpCondition;
            inputSearch.setText(condition.getSearchKeyword());

            genreListBox.removeAllViews();
            genreListBox.addView(createGenreListItem(condition.getSearchGenre()));

            String fee = condition.getSearchFee();
            if(fee.equals("요금무관")) feeItemOffWithout(checkFeeAll);
            else if(fee.equals("1")) feeItemOffWithout(checkFeeFree);
            else if(fee.equals("0")) feeItemOffWithout(checkFeeNotFree);
        }
        isReSearch = true;
    }

    private void initView() {
        ColorGradientUtil.applyViewColorGradient(this,btnReset, R.color.colorMainDark);
        ColorGradientUtil.applyViewColorGradient(this,btnSearch, R.color.prettyPink);

        checkFeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeItemOffWithout((Button) v);
            }
        });
        checkFeeFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeItemOffWithout((Button) v);
            }
        });
        checkFeeNotFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeItemOffWithout((Button) v);
            }
        });
        genreListBox.addView(createGenreListItem("전체"));
        feeItemOffWithout(checkFeeAll);
    }

    private void feeItemOffWithout(Button item) {
        checkFeeAll.setBackground(getResources().getDrawable(R.drawable.gray_rectangle));
        checkFeeFree.setBackground(getResources().getDrawable(R.drawable.gray_rectangle));
        checkFeeNotFree.setBackground(getResources().getDrawable(R.drawable.gray_rectangle));

        item.setBackground(getResources().getDrawable(R.drawable.pink_stroke_rectangle));
        switch (item.getId()) {
            case R.id.check_fee_all:
                condition.setSearchFee("요금무관");
                break;
            case R.id.check_fee_free:
                condition.setSearchFee("1");
                break;
            case R.id.check_fee_not_free:
                condition.setSearchFee("0");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_GENRE_REQUEST:
                    condition.setSearchGenre(data.getStringExtra("pick"));

                    genreListBox.removeAllViews();
                    genreListBox.addView(createGenreListItem(condition.getSearchGenre()));
                    break;
            }
        }
    }

    private TextView createGenreListItem(String genre) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(4 * dm.density);

        TextView item = new TextView(this);

        FlexboxLayout.LayoutParams lparam = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lparam.setMargins(0, 0, size, size);

        item.setLayoutParams(lparam);


        item.setBackground(getResources().getDrawable(R.drawable.no_stroke_rectangle));

        item.setPadding(size, size, size, size);
        item.setTextColor(Color.parseColor("#ffffff"));
        item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(genre);

        ColorGradientUtil.applyGenreColorGradient(this, genre, item);

        return item;
    }


    @OnClick(R.id.btn_search)
    void btnSearchClicked() {
        condition.setSearchKeyword(inputSearch.getText().toString());

        //Toast.makeText(getApplicationContext(), SearchQueryBuilder.getSearchQuery(getResources().getString(R.string.event_table), condition), Toast.LENGTH_LONG).show();

        Intent i = new Intent(this,SearchResultActivity.class);
        i.putExtra("condition", condition);
        if(isReSearch == false){
            startActivity(i);
        }else if(isReSearch){
            setResult(RESULT_OK, i);
        }
        finish();

    }

    @OnClick(R.id.btn_pick_genre)
    void btnPickGenreClicked() {
        Intent i = new Intent(this, GenrePickDialog.class);
        startActivityForResult(i, PICK_GENRE_REQUEST);
    }

    @OnClick(R.id.btn_back)
    void btnBackClicked(){
        finish();
    }

    @OnClick(R.id.btn_reset)
    void btnResetClicked(){
        condition = new SearchCondition();
        inputSearch.setText("");
        genreListBox.removeAllViews();
        genreListBox.addView(createGenreListItem("전체"));
        feeItemOffWithout(checkFeeAll);
    }
}
