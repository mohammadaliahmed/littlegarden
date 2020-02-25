package com.appsinventiv.littlegarden.Activities.ReservationManagement;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appsinventiv.littlegarden.Activities.ReservationManagement.Fragments.ChooseMenuFragment;
import com.appsinventiv.littlegarden.R;
import com.appsinventiv.littlegarden.Utils.CommonUtils;
import com.appsinventiv.littlegarden.Utils.Constants;
import com.appsinventiv.littlegarden.Utils.StepperIndicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class BookTable extends AppCompatActivity {

    PagerAdapterr adapterr;
    public static ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }
        this.setTitle("Make a reservation");
        pager = findViewById(R.id.pager);
        assert pager != null;
        adapterr = new PagerAdapterr(getSupportFragmentManager(), this);
        pager.setAdapter(adapterr);

        final StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);
        indicator.setStepCount(3);

        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                pager.setCurrentItem(step, true);
            }
        });
//        pager.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return true;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {


            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        int item = pager.getCurrentItem();
        if (Constants.MENU_STEP == 2) {
            Constants.MENU_STEP = 1;
            try {
                ChooseMenuFragment favoritesFragment = (ChooseMenuFragment) getSupportFragmentManager()
                        .getFragments()
                        .get(1);
//                favoritesFragment.getDataaFromServer();
            }catch (Exception e){
                CommonUtils.showToast(e.getMessage());
            }
        } else {
            if (item > 0) {
                item--;
                pager.setCurrentItem(item);
            } else {
                finish();
            }
        }
    }
}
