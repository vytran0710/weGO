package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;
import uit.edu.vn.wego.adapter.RecyclerAdapter;

public class ReviewPosts extends AppCompatActivity {
    private ArrayList<ModelItemReviewPost> item_model;
    private RecyclerAdapter recycler_adapter;
    private RecyclerView recyclerView;
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_posts);
        recyclerView = findViewById(R.id.review_post_recyclerview);

        // TODO: Get a list of objects from the database
        item_model = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("Anonymous: Đẹp quá hihi");
        ArrayList<String> temp1 = new ArrayList<String>();
        temp1.add("https://fantasea.vn/wp-content/uploads/2018/09/da-lat.jpg");
        item_model.add(new ModelItemReviewPost("1", "12/1/2022","tips|miennam|dalat", "Thành phố Đà Lạt", "https://www.google.com/maps/place/Dalat,+L%C3%A2m+%C4%90%E1%BB%93ng/data=!4m2!3m1!1s0x317112fef20988b1:0xad5f228b672bf930?sa=X&ved=2ahUKEwjq8K68ytPwAhWHHKYKHX0MCtgQ8gEwFXoECDUQAQ", "content", 231, temp, temp1));
        item_model.add(new ModelItemReviewPost("1", "12/1/2022","miennam|dalat", "Thành phố Đà Lạt", "https://www.google.com/maps/place/Dalat,+L%C3%A2m+%C4%90%E1%BB%93ng/data=!4m2!3m1!1s0x317112fef20988b1:0xad5f228b672bf930?sa=X&ved=2ahUKEwjq8K68ytPwAhWHHKYKHX0MCtgQ8gEwFXoECDUQAQ", "content", 231, temp, temp1));
        item_model.add(new ModelItemReviewPost("1", "12/1/2022","miennam|dalat", "Thành phố Đà Lạt", "https://www.google.com/maps/place/Dalat,+L%C3%A2m+%C4%90%E1%BB%93ng/data=!4m2!3m1!1s0x317112fef20988b1:0xad5f228b672bf930?sa=X&ved=2ahUKEwjq8K68ytPwAhWHHKYKHX0MCtgQ8gEwFXoECDUQAQ", "content", 231, temp, temp1));
        item_model.add(new ModelItemReviewPost("1", "12/1/2022","miennam|dalat", "Thành phố Đà Lạt", "https://www.google.com/maps/place/Dalat,+L%C3%A2m+%C4%90%E1%BB%93ng/data=!4m2!3m1!1s0x317112fef20988b1:0xad5f228b672bf930?sa=X&ved=2ahUKEwjq8K68ytPwAhWHHKYKHX0MCtgQ8gEwFXoECDUQAQ", "content", 231, temp, temp1));

        recycler_adapter = new RecyclerAdapter(this, setArrayList(item_model, getIntent().getStringExtra("selected_item")));

        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private ArrayList<ModelItemReviewPost> setArrayList(ArrayList<ModelItemReviewPost> temp, String tag)
    {
        for(int i = 0; i < temp.size(); ++i)
        {
            if(!temp.get(i).getTag().contains(tag))
            {
                temp.remove(i);
                --i;
            }
        }
        return temp;
    }
}