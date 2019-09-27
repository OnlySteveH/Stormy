package uk.co.bigdogconsultants.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apiKey = "d0bbfb359e73a19d161686e885d73d68";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forecastUrl = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude +"," + longitude;

        if(isNetworkAvailable()){

        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    Log.d(TAG, response.body().string());
                    if(response.isSuccessful()){
                        Log.d(TAG, getString(R.string.success_message));
                    } else {
                        alertUserAboutError();
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

    }

    private boolean isNetworkAvailable() {
        boolean isAvailable = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            alertUserAboutError();
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(), "error_tag");
    }

}
