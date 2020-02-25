package com.appsinventiv.littlegarden.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import com.appsinventiv.littlegarden.Adapters.HomeCategoryAdapter;
import com.appsinventiv.littlegarden.Adapters.SimpleFragmentPagerAdapter;
import com.appsinventiv.littlegarden.Models.NewCategoryModel;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.appsinventiv.littlegarden.Activities.CartManagement.CartActivity;
import com.appsinventiv.littlegarden.Adapters.HomePageCategoryAdapter;
import com.appsinventiv.littlegarden.Adapters.MainSliderAdapter;
import com.appsinventiv.littlegarden.Models.Category;
import com.appsinventiv.littlegarden.Models.Product;
import com.appsinventiv.littlegarden.NetworkResponses.CategoryResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ProductResponse;
import com.appsinventiv.littlegarden.R;
import com.appsinventiv.littlegarden.Utils.AppConfig;
import com.appsinventiv.littlegarden.Utils.CommonUtils;
import com.appsinventiv.littlegarden.Utils.SharedPrefs;
import com.appsinventiv.littlegarden.Utils.UserClient;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager slider;
    private Toolbar toolbar;
    ArrayList<String> pics = new ArrayList<>();
    int currentPic = 0;
    DotsIndicator dots_indicator;
    MainSliderAdapter mViewPagerAdapter;
    RecyclerView recyclerview;
    HomeCategoryAdapter adaptera;
    private List<NewCategoryModel> itemList = new ArrayList<>();
    private ArrayList<Category> categorylist = new ArrayList<>();
    public static List<Product> additionalItems = new ArrayList<>();
    public static HashMap<String, Integer> categoryMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerview = findViewById(R.id.recyclerview);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        this.setTitle("Little Garden");
        initViewPager();
        initDrawer();
        getCategoryDataFromDB();

        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        itemList.add(new NewCategoryModel("Pizza", R.drawable.pizza));
        itemList.add(new NewCategoryModel("Chinese", R.drawable.chinese));
        itemList.add(new NewCategoryModel("Taco", R.drawable.taco));
        itemList.add(new NewCategoryModel("Burger", R.drawable.burger));
        itemList.add(new NewCategoryModel("Coffee", R.drawable.coffee));

        adaptera = new HomeCategoryAdapter(this, itemList);

        recyclerview.setAdapter(adaptera);


    }

    private void getCategoryDataFromDB() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
        Call<CategoryResponse> call = getResponse.getCategories(
                SharedPrefs.getToken()
        );
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    itemList.clear();
                    CategoryResponse object = response.body();
                    if (object != null && object.getData() != null) {
                        if (object.getData().size() > 0) {

                            for (Category category : object.getData()) {
                                categoryMap.put(category.getName(), category.getId());
                                if (!category.getName().equalsIgnoreCase("Additional Items")) {
                                    categorylist.add(category);
                                } else {

                                }
                            }
                            ArrayList<String> listt = new ArrayList<>();

                            for (Category category : categorylist) {
                                listt.add(category.getName());
                            }
                            SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(MainActivity.this, getSupportFragmentManager(), listt);
                            ViewPager viewpager = findViewById(R.id.viewpager);
                            viewpager.setAdapter(adapter);

                            // Give the TabLayout the ViewPager
                            TabLayout tabLayout = findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewpager);
//                            adapter.setItemList(itemList);
                        } else {
                            CommonUtils.showToast("No category Data");
                        }
                    } else {
                        CommonUtils.showToast("There is some error");
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                CommonUtils.showToast(t.getMessage());
            }
        });
    }


    private void initViewPager() {
        pics.add("https://d6prv7be4nrvy.cloudfront.net/wp-content/uploads/1000X500.jpg");
        pics.add("https://greatperformersacademy.com/images/images/Articles_images/food-brain-improve-cognitive-function.jpg");
        pics.add("http://marypurdy.co/wp-content/uploads/2018/08/51-healthy-food.png");
        pics.add("https://d6prv7be4nrvy.cloudfront.net/wp-content/uploads/HERO.jpg");
        pics.add("https://www.epilepsysociety.org.uk/sites/default/files/diet.jpg");
        slider = findViewById(R.id.slider);
        dots_indicator = findViewById(R.id.dots_indicator);
        mViewPagerAdapter = new MainSliderAdapter(this, pics);
        slider.setAdapter(mViewPagerAdapter);
        mViewPagerAdapter.notifyDataSetChanged();
        dots_indicator.setViewPager(slider);
        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPic = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Magic here
                if (pics != null) {
                    if (currentPic >= pics.size()) {
                        currentPic = 0;
                        slider.setCurrentItem(currentPic);
                    } else {
                        slider.setCurrentItem(currentPic);
                        currentPic++;
                    }
                }
                new Handler().postDelayed(this, 4000);
            }
        }, 3000); // Millisecond 1000 = 1 sec

    }

    private void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        CircleImageView image = headerView.findViewById(R.id.imageView);
        TextView navUsername = (TextView) headerView.findViewById(R.id.name_drawer);
        TextView navSubtitle = (TextView) headerView.findViewById(R.id.subtitle_drawer);
        if (SharedPrefs.getUserModel().getImage() != null) {
            Glide.with(MainActivity.this).load(AppConfig.BASE_URL_Image + SharedPrefs.getUserModel().getImage()).into(image);
        } else {
            Glide.with(MainActivity.this).load(R.drawable.logo).into(image);

        }

        navUsername.setText("Welcome, " + SharedPrefs.getUserModel().getName());
        navSubtitle.setText(SharedPrefs.getUserModel().getCell1());
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_my_profile) {
            startActivity(new Intent(MainActivity.this, MyProfile.class));

        } else if (id == R.id.nav_my_orders) {
            startActivity(new Intent(MainActivity.this, MyOrders.class));

        } else if (id == R.id.nav_logout) {
            SharedPrefs.logout();
            Intent i = new Intent(MainActivity.this, Splash.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
