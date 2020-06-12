package com.programmingly.meetmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    String category_id;
    AppCompatEditText etAd;
    String ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

       /* 1.Law - 1
        2.Medicine - 2
        3.Actors - 3
        4.Hairdressers - 4
        5.Coder - 5
        6.Hospitality - 6
        7.Marketing - 7
        8.Sales - 8
        9.Finance - 9*/

       Bundle bundle = getIntent().getExtras();

       if (bundle!=null){

         category_id =   bundle.getString("category_id");
       }


       etAd = findViewById(R.id.etAd);
        Button btnPostad = findViewById(R.id.btnPostad);

       btnPostad.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            ad =   Objects.requireNonNull(etAd.getText()).toString().trim();
               if (ad.isEmpty()){

                   etAd.setError("Empty");
                   etAd.requestFocus();
                   return;
               }

               sendhttrequest();

           }
       });


    }

    private void sendhttrequest() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();



        String url = "http://www.programmingly.com/meetmart/api/post/addpost";


        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //boolean result = jsonObject.getBoolean(getString(R.string.post_error));

                            String res = jsonObject.getString("code");

                            if (res.equals("OK")) {

                                Toast.makeText(PostActivity.this, "Post added", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                               // onSuccess();
                            } else {
                              //  onFailed(1);
                                progressDialog.dismiss();

                                Toast.makeText(PostActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                       //     onFailed(2);
                            Toast.makeText(PostActivity.this, "Invalid response from server ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   onFailed(3);

                        Toast.makeText(PostActivity.this, "Problem occur at server", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("userId","1");
                params.put("post_text", ad);
                params.put("post_category", category_id);
                return params;
            }
        };

        int socketTimeout = 30000; //30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


        jsonRequest.setRetryPolicy(policy);

        ApplicationRequest.getInstance(this).addToRequestQueue(jsonRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listusers,menu);

        return true;
    }


    public void listusers(MenuItem item) {

        Intent intent = new Intent(this,UserListActivity.class);
        intent.putExtra("category_id",category_id);
        startActivity(intent);
    }
}