package com.example.annakostova.vstechnology.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.annakostova.vstechnology.MainApp;
import com.example.annakostova.vstechnology.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentSignIn extends Fragment {
    EditText lslEmeilCb,lstPasswordCb;
    Button btnSingInCb;
    ProgressBar progresBarCb;
    MyTask task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sign_in, container, false);
        btnSingInCb=(Button)v.findViewById(R.id.btnSignIn);
        lslEmeilCb=(EditText)v.findViewById(R.id.lstEmail);
        lstPasswordCb=(EditText)v.findViewById(R.id.lstPassword);
        progresBarCb=(ProgressBar)v.findViewById(R.id.progressBar);
        btnSingInCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task!=null){
                    task.cancel(true);
                    task=null;

                }
                task=new MyTask();
                task.execute();

            }
        });

        return v;

    }
    class MyTask extends AsyncTask<Void, Void,String> {
        String Reqvest;
        @Override
        protected void onPreExecute() {
            progresBarCb.setVisibility(View.VISIBLE);
            Reqvest="http://vacuumltd.com/androidLogin.php?action=login&login="+lslEmeilCb.getText()+"&pass="+lstPasswordCb.getText();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return myRequest(Reqvest);
        }


        @Override
        protected void onPostExecute(String result) {

            if(result.equals("ok")){

                    Intent intent2=new Intent(getActivity(),MainApp.class);
                    startActivity(intent2);


            }else {
                Toast.makeText(getContext(),"Passwor or Emeil is wrong",Toast.LENGTH_LONG).show();
            }

            progresBarCb.setVisibility(View.GONE);
            lstPasswordCb.setText("");
            lslEmeilCb.setText("");


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




}
