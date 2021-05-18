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
        item_model.add(new ModelItemReviewPost("1", "12/1/2022","miennam|dalat", "Thành phố Đà Lạt", "https://www.google.com/maps/place/Dalat,+L%C3%A2m+%C4%90%E1%BB%93ng/data=!4m2!3m1!1s0x317112fef20988b1:0xad5f228b672bf930?sa=X&ved=2ahUKEwjq8K68ytPwAhWHHKYKHX0MCtgQ8gEwFXoECDUQAQ", "\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-", 231, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2", "12/1/2022","mientrung|dalak", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2", "12/1/2022","mientrung|dalak", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2", "12/1/2022","mientrung|dalak", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2", "12/1/2022","mientrung|dalak", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
        item_model.add(new ModelItemReviewPost("2", "12/1/2022","mientrung|dalak", "summer", "cool", "cold", 123123, new ArrayList<String>(), new ArrayList<String>()));
    }
}