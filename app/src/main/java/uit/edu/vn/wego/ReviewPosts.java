package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_posts);
        recyclerView = findViewById(R.id.review_post_recyclerview);

        mContext = this;

        home_button = findViewById(R.id.home_btn_2);
        profile_button = findViewById(R.id.profile_btn_2);

        // TODO: Get a list of objects from the database
        item_model = new ArrayList<>();

        queue = Volley.newRequestQueue(this);
        //volley//
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

                        item_model.add(new ModelItemReviewPost(post_id, post_update_date, post_tag, post_title, post_locationURL, post_content, 231, post_listComment, post_listImage));
                    }
                    ArrayList<ModelItemReviewPost> filtered_item = new ArrayList<>();
                    filtered_item = setArrayList(item_model, getIntent().getStringExtra("selected_item"));

                    recycler_adapter = new RecyclerAdapter(mContext, filtered_item);
                    recyclerView.setAdapter(recycler_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

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
        //volley//

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
    }

    private ArrayList<ModelItemReviewPost> setArrayList(ArrayList<ModelItemReviewPost> temp, String tag) {
        for (int i = 0; i < temp.size(); ++i) {
            if (!temp.get(i).getTag().contains(tag)) {
                temp.remove(i);
                --i;
            }
        }
        return temp;
    }
}