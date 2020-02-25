package com.appsinventiv.littlegarden.Activities;

import android.os.Bundle;
import android.widget.TextView;

import com.appsinventiv.littlegarden.Adapters.OrderProductsAdapter;
import com.appsinventiv.littlegarden.Adapters.OrderTablesAdapter;
import com.appsinventiv.littlegarden.Models.DetailModel;
import com.appsinventiv.littlegarden.Models.OrderModel;
import com.appsinventiv.littlegarden.Models.Table;
import com.appsinventiv.littlegarden.NetworkResponses.ViewOrderResponse;
import com.appsinventiv.littlegarden.R;
import com.appsinventiv.littlegarden.Utils.AppConfig;
import com.appsinventiv.littlegarden.Utils.CommonUtils;
import com.appsinventiv.littlegarden.Utils.SharedPrefs;
import com.appsinventiv.littlegarden.Utils.UserClient;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewOrderDetails extends AppCompatActivity {

    int orderId;
    TextView tvorderId, orderTime, orderDate, orderStatus;
    RecyclerView tableRecycler, productsRecycler;
    OrderTablesAdapter orderTablesAdapter;
    OrderProductsAdapter orderProductAdapter;
    private List<Table> tableList = new ArrayList<>();
    private List<DetailModel> productList = new ArrayList<>();
    TextView paymentType, totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        orderId = getIntent().getIntExtra("orderId", 0);
        tvorderId = findViewById(R.id.orderId);
        orderStatus = findViewById(R.id.orderStatus);
        orderTime = findViewById(R.id.orderTime);
        orderDate = findViewById(R.id.orderDate);
        paymentType = findViewById(R.id.paymentType);
        totalAmount = findViewById(R.id.totalAmount);
        productsRecycler = findViewById(R.id.productsRecycler);
        tableRecycler = findViewById(R.id.tableRecycler);

        orderTablesAdapter = new OrderTablesAdapter(this, tableList);
        tableRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        tableRecycler.setAdapter(orderTablesAdapter);


        orderProductAdapter = new OrderProductsAdapter(this, productList);
        productsRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        productsRecycler.setAdapter(orderProductAdapter);


        getDataFromServer();


    }

    private void getDataFromServer() {
        UserClient getResponse = AppConfig.getRetrofit().create(UserClient.class);
        Call<ViewOrderResponse> call = getResponse.getOrdersDetails(
                SharedPrefs.getToken(),
                "" + orderId
        );
        call.enqueue(new Callback<ViewOrderResponse>() {
            @Override
            public void onResponse(Call<ViewOrderResponse> call, Response<ViewOrderResponse> response) {
                if (response.isSuccessful()) {
                    ViewOrderResponse object = response.body();
                    if (object != null) {
                        OrderModel orderModel = object.getData();
                        if (orderModel != null) {
                            tvorderId.setText("Order Id: " + orderModel.getId());
                            tvorderId.setText("Order Id: " + orderModel.getId());
                            String[] abc = orderModel.getCreatedAt().split(" ");
                            orderDate.setText("Date: " + abc[0]);
                            orderTime.setText("Time: " + abc[1]);
                            orderStatus.setText("Current");
                            paymentType.setText("Payment Type: " + orderModel.getPaymentMethod());
                            totalAmount.setText("Total: $" + String.format("%.2f", Double.parseDouble(orderModel.getTotal())));
                            if (orderModel.getTables() != null && orderModel.getTables().size() > 0) {
                                orderTablesAdapter.setItemList(orderModel.getTables());
                            }
                            if (orderModel.getDetail() != null && orderModel.getDetail().size() > 0) {
                                orderProductAdapter.setItemList(orderModel.getDetail());
                            }


                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ViewOrderResponse> call, Throwable t) {
                CommonUtils.showToast(t.getMessage());
            }
        });
    }
}
