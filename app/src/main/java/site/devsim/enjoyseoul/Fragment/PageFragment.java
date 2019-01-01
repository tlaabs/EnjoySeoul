package site.devsim.enjoyseoul.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import site.devsim.enjoyseoul.Adapter.ListViewAdapter;
import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;

public class PageFragment extends Fragment {
    private static final String TAG = "PageFragment";

    private String genre;
    private ArrayList<EventItem> eventList;

    private RecyclerView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.genre = getArguments().getString("genre");
            this.eventList = (ArrayList<EventItem>)getArguments().getSerializable("eventList");
            eventList = findEventByGenre(eventList, this.genre);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page,container, false);
        listView = v.findViewById(R.id.listView);
        initListView();
        return v;
    }

    private void initListView(){
        ListViewAdapter mAdapter = new ListViewAdapter(getContext(),eventList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(mAdapter);
    }

    private ArrayList<EventItem> findEventByGenre(ArrayList<EventItem> src, String genre){
        if(genre.equals("전체")){
            return src;
        }
        ArrayList<EventItem> res = new ArrayList<EventItem>();
        for(EventItem i : src){
            if(i.getGenre().equals(genre)){
                res.add(i);
            }
        }
        return res;
    }
}
