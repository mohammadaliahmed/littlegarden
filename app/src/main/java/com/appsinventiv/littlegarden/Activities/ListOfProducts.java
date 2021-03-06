package com.appsinventiv.littlegarden.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.appsinventiv.littlegarden.Activities.Callbacks.AddToCartCallback;
import com.appsinventiv.littlegarden.Activities.CartManagement.CartActivity;
import com.appsinventiv.littlegarden.Adapters.ExtrasProductAdapter;
import com.appsinventiv.littlegarden.Adapters.VariationListAdapter;
import com.appsinventiv.littlegarden.Adapters.ProductListAdapter;
import com.appsinventiv.littlegarden.Models.Extra;
import com.appsinventiv.littlegarden.Models.Product;
import com.appsinventiv.littlegarden.NetworkResponses.AddToCartResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ProductResponse;
import com.appsinventiv.littlegarden.R;
import com.appsinventiv.littlegarden.Utils.AppConfig;
import com.appsinventiv.littlegarden.Utils.CommonUtils;
import com.appsinventiv.littlegarden.Utils.SharedPrefs;
import com.appsinventiv.littlegarden.Utils.UserClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListOfProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductListAdapter adapter;
    List<Product> itemList = new ArrayList<>();
    EditText search;
    int categoryId;
    private ArrayList productIdList = new ArrayList();
    RelativeLayout wholeLayout;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }
//
//        categoryId = getIntent().getIntExtra("categoryId", 1);
//        categoryName = getIntent().getStringExtra("categoryName");
//        this.setTitle(categoryName);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        wholeLayout = findViewById(R.id.wholeLayout);
//        search = findViewById(R.id.search);
//        initProducts();
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                adapter.filter(s.toString());
//            }
//        });
//
//    }
//
//    private void initProducts() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
////        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        getCartids();
//
//        adapter = new ProductListAdapter(this, itemList, productIdList, new AddToCartCallback() {
//            @Override
//            public void onAddToCart(Product product) {
//                showExtrasAlert(product);
//            }
//
//            @Override
//            public void onRemoveFromCart(Product product) {
//                showRemoveAlert(product);
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        getProductsDataFromDB();
//    }
//
//    private void showRemoveAlert(final Product product1) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ListOfProducts.this);
//        builder.setTitle("Alert");
//        builder.setMessage("Do you want to delete this food item? ");
//
//        // add the buttons
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                removeFromCartProduct(product1);
//
//            }
//        });
//        builder.setNegativeButton("Cancel", null);
//
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void showExtrasAlert(final Product product) {
//        final Dialog dialog = new Dialog(this);
//        final int[] eid = {0};
//        final ArrayList<Integer> list = new ArrayList<>();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View layout = layoutInflater.inflate(R.layout.alert_extras_layout, null);
//
//        dialog.setContentView(layout);
//        CircleImageView picture = layout.findViewById(R.id.picture);
//        TextView title = layout.findViewById(R.id.title);
//        LinearLayout variationLayout = layout.findViewById(R.id.variationLayout);
//        Button addToCart = layout.findViewById(R.id.addToCart);
//        ImageView close = layout.findViewById(R.id.close);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        RecyclerView variation = layout.findViewById(R.id.variationRecycler);
//        RecyclerView extrasRecycler = layout.findViewById(R.id.extrasRecycler);
//        LinearLayout extrasLayout = layout.findViewById(R.id.extrasLayout);
//
//        title.setText(product.getName());
//        Glide.with(this).load(AppConfig.BASE_URL_Image + product.getImage()).into(picture);
//
//        if (product.getExtras() != null && product.getExtras().size() > 0) {
//            variationLayout.setVisibility(View.VISIBLE);
//            VariationListAdapter adapter2 = new VariationListAdapter(this, product.getExtras(), new VariationListAdapter.ExtrasCallback() {
//                @Override
//                public void onSelect(Extra extra) {
//                    eid[0] = extra.getId();
//                    list.add(extra.getId());
////                    showAddToCartAlert(product, eid[0], dialog);
////                    CommonUtils.showToast(extra.getName());
//                }
//
//                @Override
//                public void onRemove(Extra extra) {
//                    list.remove(extra.getId());
//
//                }
//            });
//            variation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//            variation.setAdapter(adapter2);
//        }
//        if (categoryId != 5) {
//            extrasLayout.setVisibility(View.VISIBLE);
//
//            extrasRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//            ExtrasProductAdapter extrasProductAdapter = new ExtrasProductAdapter(this, MainActivity.additionalItems, new ExtrasProductAdapter.AddExtraCallback() {
//                @Override
//                public void onAdd(Product product) {
//                    addExtraToCartProduct(product);
//                }
//
//                @Override
//                public void onRemove(Product product) {
//                    removeFromCartProduct(product);
//                }
//            });
//            extrasRecycler.setAdapter(extrasProductAdapter);
//        }
//
//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if (product.getExtras() != null && product.getExtras().size() > 0) {
//                    if (eid[0] == 0) {
//                        CommonUtils.showToast("Please select your meal size");
//                    } else {
//                        for (Integer abc : list) {
//                            addToCartProduct(product, abc, dialog);
//
//                        }
////                        addToCartProduct(product, eid[0], dialog);
//
//                    }
//                } else {
//                    addToCartProduct(product, eid[0], dialog);
//                }
//            }
//        });
//
//
//        dialog.show();
//
//    }
//
//    private void showAddToCartAlert(final Product product, final int aaaaaa, final Dialog dialog) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ListOfProducts.this);
//        builder.setTitle("Alert");
//        builder.setMessage("Add to cart? ");
//
//        // add the buttons
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                addToCartProduct(product, aaaaaa, dialog);
//
//
//            }
//        });
//        builder.setNegativeButton("Cancel", null);
//
//        // create and show the alert dialog
//        AlertDialog dialog1 = builder.create();
//        dialog1.show();
//
//    }
//
//    private void getCartids() {
//        productIdList.clear();
//        HashMap<Integer, Integer> map = SharedPrefs.getCartMenuIds();
//        if (map != null) {
//            if (map.size() > 0) {
//                for (Map.Entry me : map.entrySet()) {
//                    System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
//                    productIdList.add(me.getValue());
//                }
//                if (adapter != null) {
//                    adapter.setProductIdList(productIdList);
//                }
//            } else {
//                productIdList = new ArrayList();
//                if (adapter != null) {
//                    adapter.setProductIdList(productIdList);
//                }
//            }
//        }
//
//    }
//
//    private void removeFromCartProduct(final Product product) {
//        wholeLayout.setVisibility(View.VISIBLE);
//        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
//        Call<AddToCartResponse> call = getResponse.removeFromCart(
//                SharedPrefs.getToken(), "" + product.getId()
//        );
//        call.enqueue(new Callback<AddToCartResponse>() {
//            @Override
//            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
//                if (response.isSuccessful()) {
//                    wholeLayout.setVisibility(View.GONE);
//                    AddToCartResponse object = response.body();
//                    if (object != null) {
//                        if (object.getMeta().getMessage().equalsIgnoreCase("Successfully Added")) {
////                            dialog.dismiss();
//                            CommonUtils.showToast(object.getMeta().getMessage());
//                            HashMap<Integer, Integer> map = SharedPrefs.getCartMenuIds();
//                            if (map != null) {
//                                map.put(product.getId(), product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            } else {
//                                map = new HashMap<>();
//                                map.put(product.getId(), product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            }
//                            getCartids();
//                        } else if (object.getMeta().getMessage().equalsIgnoreCase("Successfully Removed")) {
////                            dialog.dismiss();
//                            CommonUtils.showToast(object.getMeta().getMessage());
//                            HashMap<Integer, Integer> map = SharedPrefs.getCartMenuIds();
//                            if (map != null) {
//                                map.remove(product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            } else {
//                                map = new HashMap<>();
//                                map.remove(product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            }
//                            getCartids();
//                        }
//                    } else {
//                        CommonUtils.showToast("There is some error");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
//                wholeLayout.setVisibility(View.GONE);
//                CommonUtils.showToast(t.getMessage());
//            }
//        });
//    }
//
//
//    private void addToCartProduct(final Product product, int edi, final Dialog dialog) {
//        wholeLayout.setVisibility(View.VISIBLE);
//        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
//        Call<AddToCartResponse> call = getResponse.addToCart(
//                SharedPrefs.getToken(), "" + product.getId(), "" + edi
//        );
//        call.enqueue(new Callback<AddToCartResponse>() {
//            @Override
//            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
//                if (response.isSuccessful()) {
//                    wholeLayout.setVisibility(View.GONE);
//                    AddToCartResponse object = response.body();
//                    if (object != null) {
//                        if (object.getMeta().getMessage().equalsIgnoreCase("Successfully Added")) {
//                            dialog.dismiss();
//                            CommonUtils.showToast(object.getMeta().getMessage());
//                            HashMap<Integer, Integer> map = SharedPrefs.getCartMenuIds();
//                            if (map != null) {
//                                map.put(product.getId(), product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            } else {
//                                map = new HashMap<>();
//                                map.put(product.getId(), product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            }
//                            getCartids();
//                        } else if (object.getMeta().getMessage().equalsIgnoreCase("Successfully Removed")) {
//                            dialog.dismiss();
//                            CommonUtils.showToast(object.getMeta().getMessage());
//                            HashMap<Integer, Integer> map = SharedPrefs.getCartMenuIds();
//                            if (map != null) {
//                                map.remove(product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            } else {
//                                map = new HashMap<>();
//                                map.remove(product.getId());
//                                SharedPrefs.setCartMenuIds(map);
//                            }
//                            getCartids();
//                        }
//                    } else {
//                        dialog.dismiss();
//                        CommonUtils.showToast("There is some error");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
//                wholeLayout.setVisibility(View.GONE);
//                CommonUtils.showToast(t.getMessage());
//                dialog.dismiss();
//            }
//        });
//    }
//
//
//    private void addExtraToCartProduct(final Product product) {
//        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
//        Call<AddToCartResponse> call = getResponse.addExtraToCart(
//                SharedPrefs.getToken(), "" + product.getId(), "" + 0
//        );
//        call.enqueue(new Callback<AddToCartResponse>() {
//            @Override
//            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
//                if (response.isSuccessful()) {
//                    AddToCartResponse object = response.body();
//                    if (object != null) {
//
//                    } else {
//                        CommonUtils.showToast("There is some error");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
//                wholeLayout.setVisibility(View.GONE);
//                CommonUtils.showToast(t.getMessage());
//            }
//        });
//    }
//
//
//    private void getProductsDataFromDB() {
//        itemList.clear();
//        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
//        Call<ProductResponse> call = getResponse.getProducts(
//                SharedPrefs.getToken()
//        );
//        call.enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                if (response.isSuccessful()) {
//                    ProductResponse object = response.body();
//                    if (object != null && object.getData() != null) {
//                        if (object.getData().size() > 0) {
//                            for (Product product : object.getData()) {
//                                if (product.getcId().equalsIgnoreCase("" + categoryId)) {
//                                    itemList.add(product);
//                                }
//                            }
////                            itemList = object.getData();
//                            adapter.updateList(itemList);
//                        } else {
//                            CommonUtils.showToast("No products Data");
//                        }
//                    } else {
//                        CommonUtils.showToast("There is some error");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                CommonUtils.showToast(t.getMessage());
//            }
//        });
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
        if (item.getItemId() == android.R.id.home) {


            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            startActivity(new Intent(ListOfProducts.this, CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
