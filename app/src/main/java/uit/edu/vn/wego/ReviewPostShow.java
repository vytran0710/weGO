package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;

public class ReviewPostShow extends AppCompatActivity {
    private ModelItemReviewPost item;
    private TextView update_date;
    private TextView title;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post_show);
        item = (ModelItemReviewPost) getIntent().getSerializableExtra("item");

        update_date = findViewById(R.id.review_post_update_date_textview);
        title = findViewById(R.id.review_post_title_textview);
        content = findViewById(R.id.review_post_content_textview);

        update_date.setText(item.getUpdate_date());
        title.setText(item.getTitle());
        content.setText(item.getContent());
    }
}