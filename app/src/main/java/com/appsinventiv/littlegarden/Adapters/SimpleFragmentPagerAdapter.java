package com.appsinventiv.littlegarden.Adapters;

import android.content.Context;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * Created by AliAh on 02/03/2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    ArrayList<String> categoryList;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, ArrayList<String> categoryList) {
        super(fm);
        mContext = context;
        this.categoryList = categoryList;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {


        return new MainFragment(mContext,categoryList.get(position));


    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
       return categoryList.get(position);
    }

}
