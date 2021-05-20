package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;

public class ReviewPostShow extends AppCompatActivity {
    private ModelItemReviewPost item;
    private TextView update_date;
    private TextView title;
    private TextView content;
    private LinearLayout images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post_show);
        item = (ModelItemReviewPost) getIntent().getSerializableExtra("item");

        update_date = findViewById(R.id.review_post_show_update_date_textview);
        title = findViewById(R.id.review_post_show_title_textview);
        content = findViewById(R.id.review_post_show_content_textview);
        images = findViewById(R.id.review_post_show_images);

        // TODO: setonclicklistener
        for(int i = 0; i < 10; ++i)
        {
            ImageView temp_imageview = new ImageView(this);
            Glide.with(this).load("https://photo-cms-sggp.zadn.vn/w580/Uploaded/2021/uhtcplu/2019_03_22/dalat_btbg.jpg").centerCrop().into(temp_imageview);
            temp_imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1024));
            LinearLayout temp_linearlayout = new LinearLayout(this);
            temp_linearlayout.setLayoutParams(new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT));
            temp_linearlayout.setBackgroundColor(getResources().getColor(R.color.white));
            images.addView(temp_imageview);
            images.addView(temp_linearlayout);
        }

        update_date.setText(item.getUpdate_date());
        title.setText(item.getTitle());
        content.setText(item.getContent());
    }
}