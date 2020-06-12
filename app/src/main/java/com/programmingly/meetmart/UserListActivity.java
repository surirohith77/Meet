package com.programmingly.meetmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    String category_id;

    private List<UserList> itemList;
    private UserListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){

            category_id =   bundle.getString("category_id");
        }

        initializeRecyclerView();
        sendHttpsRequest();

    }





    private void initializeRecyclerView() {
        RecyclerView rvRequest = findViewById(R.id.list_rec_view);
        rvRequest.setHasFixedSize(true);
        rvRequest.setLayoutManager(new LinearLayoutManager(this));

      /*  GridLayoutManager manager = new GridLayoutManager(activity,2);
        rvRequest.setLayoutManager(manager);*/

/*
        rvRequest.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(
                activity, LinearLayoutManager.VERTICAL);

        rvRequest.addItemDecoration(decoration);
*/

        itemList = new ArrayList<>();
        adapter = new UserListAdapter(itemList);
        rvRequest.setAdapter(adapter);
    }


    /**
     * Send http request for get list users
     */
    private void sendHttpsRequest() {

        itemList.clear();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

      /*  if (!NetworkConnection.isConnected(activity)) {
            updateViews("No internet connection",R.drawable.dog6);
            return;
        }
*/
        String url = "http://www.programmingly.com/meetmart/api/post/getlist";



        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        itemList.clear();
                        progressDialog.dismiss();
                        //Log.e(TAG, response);

                        try {


                                JSONObject jsonObjec = new JSONObject(response);

                                //  JSONObject jsonObject =   jsonObjec.getJSONObject("order");

                                JSONArray jsonArray1 =  jsonObjec.getJSONArray("postList");

                                int length = jsonArray1.length();

                                for (int index = 0; index < length; index++) {

                                    JSONObject object = jsonArray1.getJSONObject(index);

                                    UserList item = new UserList();
                                    item.setPost_id(object.getString("post_id"));
                                    item.setPost_text(object.getString("post_text"));
                                    item.setPost_category(object.getString("post_category"));
                                    item.setPost_added_by(object.getString("post_added_by"));

                                    item.setPost_status(object.getString("post_status"));
                                    item.setPost_created(object.getString("post_created"));
                                    item.setPost_updated(object.getString("post_updated"));



                                    itemList.add(item);

                                    adapter.notifyDataSetChanged();

                                  /*  if (itemList.isEmpty() || itemList == null) {
                                        updateViews("No order's till now", R.drawable.monkey_anger3);
                                    }*/
                                    // onSuccess();

                                }

                             //   String deal   =  jsonObjec.getString("deal");






                        } catch (JSONException e) {
                            e.printStackTrace();
                            // onFailed(2);
                          Toast.makeText(UserListActivity.this, "Invalid resonse from the server", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  onFailed(3);
                        Toast.makeText(UserListActivity.this, "Problem there in server", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put("post_category", category_id);

                //  params.put("merchant_id","1002");
                return params;
            }
        };

        int socketTimeout = 30000; //30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    //    jsonRequest.setTag(TAG);
        jsonRequest.setRetryPolicy(policy);

        ApplicationRequest.getInstance(this).addToRequestQueue(jsonRequest);
    }
}