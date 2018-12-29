package site.devsim.enjoyseoul.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import site.devsim.enjoyseoul.Fragment.PageFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainPagerAdapter";
    private static int PAGE_NUMBER = 5;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PageFragment();
            case 1:
                return new PageFragment();
            case 2:
                return new PageFragment();
            case 3:
                return new PageFragment();
            case 4:
                return new PageFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "일고오모올";
            case 1:
                return "이ㅁㄴㅇㄻㄴㅇㄹ";
            case 2:
                return "삼ㅁㄴㅇㄹ";
            case 3:
                return "사ㅁㄴㅇㄹ";
            case 4:
                return "오ㅁㄴㅇㄹ";
            default:
                return null;
        }
    }
}
