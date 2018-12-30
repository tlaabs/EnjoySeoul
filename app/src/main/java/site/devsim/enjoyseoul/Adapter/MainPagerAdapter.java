package site.devsim.enjoyseoul.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import site.devsim.enjoyseoul.Fragment.PageFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainPagerAdapter";
    private static int PAGE_NUMBER = 5;

    private ArrayList<String> genreList;

    public MainPagerAdapter(FragmentManager fm, ArrayList<String> genreList) {
        super(fm);
        this.genreList = genreList;
    }

    @Override
    public Fragment getItem(int position) {
        return new PageFragment();
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
