package com.example.maishahostelbookingapp;

import static com.example.maishahostelbookingapp.Utilities.Constants.BUSINESS_SHORT_CODE;
import static com.example.maishahostelbookingapp.Utilities.Constants.CALLBACKURL;
import static com.example.maishahostelbookingapp.Utilities.Constants.PARTYB;
import static com.example.maishahostelbookingapp.Utilities.Constants.PASSKEY;
import static com.example.maishahostelbookingapp.Utilities.Constants.TRANSACTION_TYPE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.maishahostelbookingapp.Utilities.DarajaApiClient;
import com.example.maishahostelbookingapp.R;
import com.example.maishahostelbookingapp.Utilities.Utils;
import com.example.maishahostelbookingapp.models.AccessToken;
import com.example.maishahostelbookingapp.models.STKPush;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MpesaActivity extends AppCompatActivity implements View.OnClickListener {

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.etAmount)
    EditText mAmount;
    @BindView(R.id.etPhone)EditText mPhone;
    @BindView(R.id.btnPay)
    Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient ();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        mPay.setOnClickListener(this);
        Intent i=getIntent();
        String amount=i.getStringExtra("amount");
        mAmount.setText(amount);

        getAccessToken();


    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view== mPay){
            String phone_number = mPhone.getText().toString();
            String amount = mAmount.getText().toString();
            performSTKPush(phone_number,amount);

            startActivity(new Intent(MpesaActivity.this,Dashboard.class));
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("channel","mychan",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager m_notificationMgr = (NotificationManager)getSystemService(NotificationManager.class);
            m_notificationMgr.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MpesaActivity.this,"channel").
                setSmallIcon(R.drawable.ic_baseline_account_circle_24).
                setContentTitle("Maisha Hostels").
//                    setContentText(drivers.getName() + drivers.getTel_number() + drivers.getRegistration());
        setContentText("Welcome to our Hostel!.Your Booking has been recieved Successfully");
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MpesaActivity.this);
        notificationManagerCompat.notify(1,builder.build());
    }



    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "MPESA Android Test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}