package com.appsinventiv.littlegarden.Activities.ReservationManagement;

import com.appsinventiv.littlegarden.Activities.ReservationManagement.Fragments.ChooseMenuFragment;
import com.appsinventiv.littlegarden.Activities.ReservationManagement.Fragments.ChooseTableFragment;
import com.appsinventiv.littlegarden.Activities.ReservationManagement.Fragments.ConfirmOrderFragment;
import com.appsinventiv.littlegarden.Activities.ReservationManagement.Fragments.FindTableFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapterr extends FragmentPagerAdapter {

    BookTable bookTable;

    public PagerAdapterr(FragmentManager fm, BookTable bookTable) {
        super(fm);
        this.bookTable = bookTable;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FindTableFragment(bookTable);
        } else if (position == 1) {
            return new ChooseTableFragment(bookTable);
        } else if (position == 2) {
            return new ChooseMenuFragment(bookTable);
        } else {
            return new ConfirmOrderFragment(bookTable);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
