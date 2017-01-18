package com.example.annakostova.vstechnology.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.annakostova.vstechnology.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentRegister extends Fragment {
    EditText txtEmailCb,txtPasswordCb,txtCompanyNameCb,lslName;
    Button btnAddCb;
    MyTask3 task;
    ProgressBar progressBar2Cb;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);


        txtEmailCb = (EditText) v.findViewById(R.id.txtEmail);
        txtPasswordCb = (EditText) v.findViewById(R.id.txtPassword);
        txtCompanyNameCb = (EditText) v.findViewById(R.id.txtCompanyName);
        lslName=(EditText)v.findViewById(R.id.lslName);
        progressBar2Cb = (ProgressBar) v.findViewById(R.id.progressBar3);
        btnAddCb = (Button) v.findViewById(R.id.btnAdd);



     btnAddCb.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                if(task!=null){
                    task.cancel(true);
                    task=null;

                }
                task=new MyTask3();
                task.execute();

            }
        });

        return v;

    }

        class MyTask3 extends AsyncTask<Void, Void, String> {
            String Reqvest;


            @Override
            protected void onPreExecute() {
                progressBar2Cb.setVisibility(View.VISIBLE);
               String Email=txtEmailCb.getText().toString();
               String pasw=txtPasswordCb.getText().toString();
               String CompanyName=txtCompanyNameCb.getText().toString();
               String Name=lslName.getText().toString();

                Reqvest = "http://vacuumltd.com/androidLogin.php?action=registrNewUser&email=" + txtEmailCb.getText() + "&password=" + txtPasswordCb.getText() + "&company=" + txtCompanyNameCb.getText() ;
                super.onPreExecute();

                if (txtEmailCb.getText().toString().length() == 0 || txtPasswordCb.getText().toString().length() == 0 || txtCompanyNameCb.getText().toString().length() == 0|| lslName.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "fill all fields", Toast.LENGTH_LONG).show();
                    cancel(true);
                    return;
                }
                progressBar2Cb.setVisibility(View.VISIBLE);
                //Reqvest = "http://www.vacuumltd.com/androidLogin.php?action=sendRegistrationRequest&email=" + lslEmailRegisterCb.getText() + "&password=" + lstPasswordCb.getText() + "&company=" + lstCompaniCb.getText() + "&name=" + lstName.getText();
               // super.onPreExecute();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {

                return myRequest(Reqvest);
            }

            @Override
            protected void onPostExecute(String result) {
               txtEmailCb.setText("");
                txtPasswordCb.setText("");
               txtCompanyNameCb.setText("");
               lslName.setText("");

                Log.d("lalala", "onPostExecute: "+result);

                if (result.equals("ok")) {

                    Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_LONG).show();

                } else if (result.equals("email_used")) {
                    Toast.makeText(getActivity(), "User with this Email", Toast.LENGTH_LONG).show();
                } else if (result.equals("fill_fields")) {
                    Toast.makeText(getActivity(), "fill all fields ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "Error Connection pleas try again later ", Toast.LENGTH_LONG).show();
                }

                if (result.equals("ok")) {
                    Toast.makeText(getContext(), "Email Send", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error Connection pleas try again later", Toast.LENGTH_LONG).show();
                }
                progressBar2Cb.setVisibility(View.GONE);


            }

            }
            public String myRequest(String RequestCb) {
                String responseStr = null;
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(RequestCb)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return responseStr;


            }


        }
