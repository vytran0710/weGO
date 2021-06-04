package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;

public class ReviewPostShow extends AppCompatActivity {
    private ModelItemReviewPost item;
    private TextView update_date;
    private TextView title;
    private TextView content;
    private ImageView image;
    private LinearLayout comments;

    private ImageButton like_button;
    private ImageButton comment_button;
    private ImageButton location_button;    

    private LinearLayout comment_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post_show);
        item = (ModelItemReviewPost) getIntent().getSerializableExtra("item");

        update_date = findViewById(R.id.review_post_show_update_date_textview);
        title = findViewById(R.id.review_post_show_title_textview);
        content = findViewById(R.id.review_post_show_content_textview);
        image = findViewById(R.id.review_post_show_imageview);
        comments = findViewById(R.id.posted_comments);

        update_date.setText(item.getUpdate_date());
        title.setText(item.getTitle());
        content.setText(item.getContent());
        Glide.with(this).load(item.getImgURL().get(0)).into(image);

        like_button = findViewById(R.id.like_btn_reviewpost);
        comment_button = findViewById(R.id.cmt_btn_reviewpost);
        location_button = findViewById(R.id.map_btn_reviewpost);

        comment_section = findViewById(R.id.review_post_comment_section);

        for(int i = 0; i < item.getComment().size(); ++i)
        {
            TextView temp_textview = new TextView(this);
            temp_textview.setText(item.getComment().get(i));
            temp_textview.setTextSize(20);
            temp_textview.setTextColor(getResources().getColor(R.color.black));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,10);
            temp_textview.setLayoutParams(params);

            LinearLayout temp_linearlayout = new LinearLayout(this);
            temp_linearlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            temp_linearlayout.setBackgroundColor(getResources().getColor(R.color.black));
            comments.addView(temp_linearlayout);
            comments.addView(temp_textview);
        }

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add to user's favorite list
            }
        });

        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_section.getVisibility() == View.VISIBLE)
                {
                    comment_section.setVisibility(View.GONE);
                }
                else
                {
                    comment_section.setVisibility(View.VISIBLE);
                }
            }
        });

        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLocationURL()));
                startActivity(browserIntent);
            }
        });
    }
}