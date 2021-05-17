package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;
import uit.edu.vn.wego.adapter.RecyclerAdapter;

public class ReviewPosts extends AppCompatActivity {
    ArrayList<ModelItemReviewPost> item_model;
    RecyclerAdapter recycler_adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_posts);
        recyclerView = findViewById(R.id.review_post_recyclerview);
        item_model = new ArrayList<>();
        CreateItem();
        recycler_adapter = new RecyclerAdapter(this, item_model);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void CreateItem(){
        item_model.add(new ModelItemReviewPost("1st", "12/1/2022","Testing", "winter", "cold", "cold", 231, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2nd", "12/1/2022","Testing 2", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
    }
}