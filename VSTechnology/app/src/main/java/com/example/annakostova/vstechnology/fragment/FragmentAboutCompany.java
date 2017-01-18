package com.example.annakostova.vstechnology.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annakostova.vstechnology.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentAboutCompany extends Fragment {
    TextView CurrencyUSDCb,CurrencyEURCb,CurrencyRUBCb;
    MyTask4 task;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about_company, container, false);
        CurrencyUSDCb=(TextView)v.findViewById(R.id.CurrencyUSD);
        CurrencyEURCb=(TextView)v.findViewById(R.id.CurrencyEUR);
        return v;

    }
    public void onResume(){
        if(task!=null){
            task.cancel(true);
            task=null;

        }
        task=new MyTask4();
        task.execute();
        super.onResume();
    }
    class MyTask4 extends AsyncTask<Void, Void,String> {


        @Override
        protected String doInBackground(Void... params) {

            return myRequest("https://query.yahooapis.com/v1/public/yql?q=select+*+from+yahoo.finance.xchange+where+pair+=+%22USDILS,EURILS,RUBILS%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
        }


        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject jsonResponse = new JSONObject(result);
                JSONArray results=jsonResponse.getJSONObject("query").getJSONObject("results").getJSONArray("rate");
                for (int i = 0; i <results.length() ; i++) {

                    JSONObject rate=results.getJSONObject(i);
                    if (rate.getString("id").equals("USDILS")){
                        CurrencyUSDCb.setText(round(rate.getDouble("Rate"),2)+"");
                    }else if(rate.getString("id").equals("EURILS")){
                        CurrencyEURCb.setText(round(rate.getDouble("Rate"),2)+"");

                    }
                }


            } catch (JSONException e) {

                e.printStackTrace();
                CurrencyUSDCb.setText("");
                CurrencyEURCb.setText("");
                CurrencyRUBCb.setText("");

            }


        }
    }
    public String myRequest(String RequestCb){
        String responseStr=null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(RequestCb)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseStr=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;


    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
