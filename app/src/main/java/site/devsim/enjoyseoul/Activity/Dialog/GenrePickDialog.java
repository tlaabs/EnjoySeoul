package site.devsim.enjoyseoul.Activity.Dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.R;

public class GenrePickDialog extends AppCompatActivity {
    private static final String TAG = "GenrePickDialog";
    private static final int ITEM_PER_ROW = 3;

    @BindView(R.id.rows_item_box)
    LinearLayout rowsItemBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_genre);
        ButterKnife.bind(this);
        createListItems();
    }

    private void createListItems() {
        DBManager dbManager = DBManager.getInstance(this);
        final ArrayList<String> genreList = dbManager.getGenreNames();
        genreList.add(0, "전체");

        int genreCount = genreList.size();
        int rowboxCount = getRowBoxCount(genreCount);

            //Create Interface
            for (int i = 0; i <= rowboxCount; i++) {
                LinearLayout itemRow = createItemRow();
                rowsItemBox.addView(itemRow);
                for (int k = 0; k < ITEM_PER_ROW; k++) {
                    final Button btnElement;
                    //out of range
                    final int idx = i * ITEM_PER_ROW + k;
                    if (idx> genreCount-1) {
                        btnElement = createEmptyItem();
                        itemRow.addView(btnElement);
                    } else {
                        btnElement = createItem(genreList.get(idx));
                        btnElement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent();
                                i.putExtra("pick",genreList.get(idx));
                                setResult(RESULT_OK, i);
                                finish();
                            }
                        });
                        itemRow.addView(btnElement);
                    }
                }

        }
    }

    private int getRowBoxCount(int genreCount) {
        int quot = genreCount / ITEM_PER_ROW; //몫
        if (ITEM_PER_ROW * quot != genreCount)
            quot++;
        return quot;
    }

    private LinearLayout createItemRow() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(3 * dm.density);

        LinearLayout itemRow = new LinearLayout(this);

        LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparam.setMargins(0, 0, 0, size);
        lparam.gravity = Gravity.CENTER;
        itemRow.setLayoutParams(lparam);
        itemRow.setOrientation(LinearLayout.HORIZONTAL);

        return itemRow;
    }

    private Button createEmptyItem() {
        Button tmp = createItem("");
        tmp.setBackgroundDrawable(null);
        return tmp;
    }

    private Button createItem(String title) {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(3 * dm.density);

        Button item = new Button(this);

        LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparam.setMargins(0, 0, size, 0);
        lparam.weight = 1.0f;

        item.setLayoutParams(lparam);
        item.setTextColor(getResources().getColor(R.color.colorMainDark));
        item.setBackground(getResources().getDrawable(R.drawable.button_event));
        item.setText(title);
        return item;
    }
}
