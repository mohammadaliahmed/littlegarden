package com.appsinventiv.littlegarden.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.appsinventiv.littlegarden.Activities.CartManagement.CartActivity;
import com.appsinventiv.littlegarden.R;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StripeCheckOut extends AppCompatActivity {
    /**
     * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment#android
     * <p>
     * To run this app, follow the steps here: https://github.com/stripe-samples/accept-a-card-payment#how-to-run-locally
     */
    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "http://lilgarden.net/api/";

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;

    RelativeLayout wholeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_check_out);
        wholeLayout = findViewById(R.id.wholeLayout);
        startCheckout();
    }

    private void startCheckout() {
        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String json = "{"
                + "\"currency\":\"aud\","
                + "\"amount\":\"" + CartActivity.total + "\","
                + "\"items\":["
                + "{\"id\":\"photo_subscription\"}"
                + "]"
                + "}";
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "acceptPayment")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
      /*  payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
*/


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wholeLayout.setVisibility(View.VISIBLE);
                CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {

                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
//                    stripe = Stripe(this, PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey();
                    stripe.confirmPayment(StripeCheckOut.this, confirmParams);
                }
            }
        });

    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        startCheckout();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();*/


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        startCheckout();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);

        if (restartDemo) {
            builder.setPositiveButton("Restart demo", dialogClickListener);
        }
        builder.setNegativeButton("Ok", dialogClickListener).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        wholeLayout.setVisibility(View.GONE);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        // The response from the server includes the Stripe publishable key and
        // PaymentIntent details.
        // For added security, our sample app gets the publishable key from the server
        String stripePublishableKey = responseMap.get("publishableKey");
        paymentIntentClientSecret = responseMap.get("clientSecret");

        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull(stripePublishableKey)
        );
    }

    private static final class PayCallback implements Callback {
        @NonNull
        private final WeakReference<StripeCheckOut> activityRef;

        PayCallback(@NonNull StripeCheckOut activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull final IOException e) {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show();
                }
            });
          /*  activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );*/
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show();
                    }
                });

               /* activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );*/
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripeCheckOut> activityRef;

        PaymentResultCallback(@NonNull StripeCheckOut activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                Log.e("Payment completed", "" + gson.toJson(paymentIntent));
//                activity.displayAlert(
//                        "Payment completed",
//                        gson.toJson(paymentIntent),
//                        true
//                );
                Intent returnIntent = getIntent();
                returnIntent.putExtra("paymentId", paymentIntent.getId());

                setResult(RESULT_OK, returnIntent);
                finish();
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                Log.e("Payment failed", "" + paymentIntent.getLastPaymentError());
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
            Log.e("Error ", "" + e.toString());
        }
    }
}

/*
public class StripeCheckOut extends AppCompatActivity {

    private String paymentIntentClientSecret;
    private Stripe stripe;
    CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_check_out);
        init();
        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());




        startCheckout();
    }

    public void init()
    {
        cardInputWidget = findViewById(R.id.cardInputWidget);
    }


    private void startCheckout() {



        // Request a PaymentIntent from your server and store its client secret in paymentIntentClientSecret
        // Click Open on GitHub to see a full implementation
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
//                    stripe = Stripe(this, PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey();
                    stripe.confirmPayment(StripeCheckOut.this, confirmParams);
                }
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            CardInputWidget cardInputWidget = StripeCheckOut.this.findViewById(R.id.cardInputWidget);
                            cardInputWidget.clear();
                            StripeCheckOut.this.startCheckout();
                        }
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }


    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<StripeCheckOut> activityRef;

        PaymentResultCallback(@NonNull StripeCheckOut activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripeCheckOut activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }
    }

}*/
