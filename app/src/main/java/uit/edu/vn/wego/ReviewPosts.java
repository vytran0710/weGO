package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;
import uit.edu.vn.wego.adapter.ModelItemUser;
import uit.edu.vn.wego.adapter.RecyclerAdapter;

public class ReviewPosts extends AppCompatActivity {
    private ArrayList<ModelItemReviewPost> item_model;
    private RecyclerAdapter recycler_adapter;
    private RecyclerView recyclerView;
    private String tag;
    private Context mContext;
    private RequestQueue queue;

    private ImageView home_button;
    private ImageView profile_button;
    private ImageView fav_button;

    private ProgressBar review_post_loading;
    private ImageButton search_btn;
    private EditText search_edit_text;
    private TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_posts);
        recyclerView = findViewById(R.id.review_post_recyclerview);

        mContext = this;

        home_button = findViewById(R.id.home_btn_2);
        profile_button = findViewById(R.id.profile_btn_2);
        fav_button = findViewById(R.id.fav_btn_2);

        search_btn = findViewById(R.id.search_btn);
        search_edit_text = findViewById(R.id.search_edit_text);
        logo = findViewById(R.id.logo2);

        review_post_loading = findViewById(R.id.review_post_loading);

        if (getIntent().getStringExtra("selected_item").equals("user_fav_list"))
        {
            home_button.setImageResource(R.drawable.home_inactive);
            fav_button.setImageResource(R.drawable.like);
        }

        item_model = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewPosts.this,HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewPosts.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelItemUser user = LoginActivity.getUser();
                if(user != null)
                {
                    Intent intent = new Intent(v.getContext(), ReviewPosts.class);
                    intent.putExtra("selected_item", "user_fav_list");
                    ((Activity)v.getContext()).startActivity(intent);
                }
                else
                {
                    Toast.makeText(v.getContext(), "Please login first.", Toast.LENGTH_LONG).show();
                }
            }
        });

        populate_recyclerview(getIntent().getStringExtra("selected_item"));
    }

    private void populate_recyclerview(String tag)
    {
        switch (tag)
        {
            case "search":
                search_tag();
                break;
            case "user_fav_list":
                fav_tag();
                break;
            default:
                default_tag(tag);
                break;
        }
    }

    private void search_tag()
    {
        logo.setVisibility(View.GONE);
        search_edit_text.setVisibility(View.VISIBLE);
        review_post_loading.setVisibility(View.GONE);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search_edit_text.getText().toString().toLowerCase();
                item_model = new ArrayList<>();
                if (!query.equals(""))
                {
                    review_post_loading.setVisibility(View.VISIBLE);
                    String url = "https://we-go-app2021.herokuapp.com/post/";
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject reviewPost = array.getJSONObject(i);

                                    String post_id = reviewPost.getString("_id");
                                    String post_update_date = reviewPost.getString("update_date");
                                    String post_tag = reviewPost.getString("tag");
                                    String post_title = reviewPost.getString("title");
                                    String post_locationURL = reviewPost.getString("locationURL");
                                    String post_content = reviewPost.getString("content");

                                    JSONArray tempComment = reviewPost.getJSONArray("listComment");
                                    ArrayList<String> post_listComment = new ArrayList<String>();
                                    for (int j = 0; j < tempComment.length(); j++) {
                                        post_listComment.add(tempComment.getString(j));
                                    }

                                    JSONArray tempImage = reviewPost.getJSONArray("listImage");
                                    ArrayList<String> post_listImage = new ArrayList<String>();
                                    for (int j = 0; j < tempImage.length(); j++) {
                                        post_listImage.add(tempImage.getString(j));
                                    }

                                    if (post_title.toLowerCase().contains(query) || post_id.toLowerCase().contains(query) || post_tag.toLowerCase().contains(query))
                                    {
                                        item_model.add(new ModelItemReviewPost(post_id, post_update_date, post_tag, post_title, post_locationURL, post_content, 0, post_listComment, post_listImage));
                                    }
                                }

                                recycler_adapter = new RecyclerAdapter(mContext, item_model);
                                recyclerView.setAdapter(recycler_adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                recycler_adapter.notifyDataSetChanged();

                                review_post_loading.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                review_post_loading.setVisibility(View.GONE);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    queue.add(request);
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_edit_text.getWindowToken(), 0);
            }
        });

        search_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = search_edit_text.getText().toString().toLowerCase();
                    item_model = new ArrayList<>();
                    if (!query.equals(""))
                    {
                        review_post_loading.setVisibility(View.VISIBLE);
                        String url = "https://we-go-app2021.herokuapp.com/post/";
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = response.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject reviewPost = array.getJSONObject(i);

                                        String post_id = reviewPost.getString("_id");
                                        String post_update_date = reviewPost.getString("update_date");
                                        String post_tag = reviewPost.getString("tag");
                                        String post_title = reviewPost.getString("title");
                                        String post_locationURL = reviewPost.getString("locationURL");
                                        String post_content = reviewPost.getString("content");

                                        JSONArray tempComment = reviewPost.getJSONArray("listComment");
                                        ArrayList<String> post_listComment = new ArrayList<String>();
                                        for (int j = 0; j < tempComment.length(); j++) {
                                            post_listComment.add(tempComment.getString(j));
                                        }

                                        JSONArray tempImage = reviewPost.getJSONArray("listImage");
                                        ArrayList<String> post_listImage = new ArrayList<String>();
                                        for (int j = 0; j < tempImage.length(); j++) {
                                            post_listImage.add(tempImage.getString(j));
                                        }

                                        if (post_title.toLowerCase().contains(query) || post_id.toLowerCase().contains(query) || post_tag.toLowerCase().contains(query))
                                        {
                                            item_model.add(new ModelItemReviewPost(post_id, post_update_date, post_tag, post_title, post_locationURL, post_content, 0, post_listComment, post_listImage));
                                        }
                                    }

                                    recycler_adapter = new RecyclerAdapter(mContext, item_model);
                                    recyclerView.setAdapter(recycler_adapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                    recycler_adapter.notifyDataSetChanged();

                                    review_post_loading.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    review_post_loading.setVisibility(View.GONE);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                        queue.add(request);
                    }
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search_edit_text.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void fav_tag()
    {
        item_model = new ArrayList<>();
        search_btn.setVisibility(View.GONE);
        fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        String url = "https://we-go-app2021.herokuapp.com/post/";
        ModelItemUser user = LoginActivity.getUser();
        if (user == null)
            return;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject reviewPost = array.getJSONObject(i);

                        String post_id = reviewPost.getString("_id");
                        String post_update_date = reviewPost.getString("update_date");
                        String post_tag = reviewPost.getString("tag");
                        String post_title = reviewPost.getString("title");
                        String post_locationURL = reviewPost.getString("locationURL");
                        String post_content = reviewPost.getString("content");

                        JSONArray tempComment = reviewPost.getJSONArray("listComment");
                        ArrayList<String> post_listComment = new ArrayList<String>();
                        for (int j = 0; j < tempComment.length(); j++) {
                            post_listComment.add(tempComment.getString(j));
                        }

                        JSONArray tempImage = reviewPost.getJSONArray("listImage");
                        ArrayList<String> post_listImage = new ArrayList<String>();
                        for (int j = 0; j < tempImage.length(); j++) {
                            post_listImage.add(tempImage.getString(j));
                        }

                        for (int k = 0; k < user.getLikedPostId().size(); ++k)
                        {
                            if (post_id.equals(user.getLikedPostId().get(k)))
                            {
                                item_model.add(new ModelItemReviewPost(post_id, post_update_date, post_tag, post_title, post_locationURL, post_content, 0, post_listComment, post_listImage));
                                break;
                            }
                        }
                    }

                    recycler_adapter = new RecyclerAdapter(mContext, item_model);
                    recyclerView.setAdapter(recycler_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recycler_adapter.notifyDataSetChanged();

                    review_post_loading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void default_tag(String tag)
    {
        item_model = new ArrayList<>();
        search_btn.setVisibility(View.GONE);
        String url = "https://we-go-app2021.herokuapp.com/post/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject reviewPost = array.getJSONObject(i);

                        String post_id = reviewPost.getString("_id");
                        String post_update_date = reviewPost.getString("update_date");
                        String post_tag = reviewPost.getString("tag");
                        String post_title = reviewPost.getString("title");
                        String post_locationURL = reviewPost.getString("locationURL");
                        String post_content = reviewPost.getString("content");

                        JSONArray tempComment = reviewPost.getJSONArray("listComment");
                        ArrayList<String> post_listComment = new ArrayList<String>();
                        for (int j = 0; j < tempComment.length(); j++) {
                            post_listComment.add(tempComment.getString(j));
                        }

                        JSONArray tempImage = reviewPost.getJSONArray("listImage");
                        ArrayList<String> post_listImage = new ArrayList<String>();
                        for (int j = 0; j < tempImage.length(); j++) {
                            post_listImage.add(tempImage.getString(j));
                        }

                        if (post_tag.contains(tag))
                        {
                            item_model.add(new ModelItemReviewPost(post_id, post_update_date, post_tag, post_title, post_locationURL, post_content, 0, post_listComment, post_listImage));
                        }
                    }

                    recycler_adapter = new RecyclerAdapter(mContext, item_model);
                    recyclerView.setAdapter(recycler_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recycler_adapter.notifyDataSetChanged();

                    review_post_loading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }
}