package site.devsim.enjoyseoul.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.EventLog;

import java.util.ArrayList;

import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.Fragment.PageFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainPagerAdapter";

    private ArrayList<String> genreList;
    private ArrayList<EventItem> eventList;

    public MainPagerAdapter(FragmentManager fm, ArrayList<String> genreList, ArrayList<EventItem> eventList) {
        super(fm);
        this.genreList = genreList;
        this.eventList = eventList;
    }

    @Override
    public Fragment getItem(int position) {
        PageFragment page = new PageFragment();
        Bundle args = new Bundle();
        args.putString("genre", genreList.get(position));
        args.putSerializable("eventList", eventList);
        page.setArguments(args);
        return page;
    }

    @Override
    public int getCount() {
        return genreList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return genreList.get(position);
    }
}
