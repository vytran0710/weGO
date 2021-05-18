package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HomeScreenActivity extends AppCompatActivity {

    private ImageView famous_place_background_imageview;
    private ImageView place_by_region_background_imageview;
    private ImageView travel_tips_background_imageview;
    private ImageView food_background_imageview;

    private CardView famous_place;
    private CardView place_by_region;
    private CardView travel_tips;
    private CardView food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        famous_place_background_imageview = findViewById(R.id.famous_place_background_imageview);
        place_by_region_background_imageview = findViewById(R.id.place_by_region_background_imageview);
        travel_tips_background_imageview = findViewById(R.id.travel_tips_background_imageview);
        food_background_imageview = findViewById(R.id.food_background_imageview);

        famous_place = findViewById(R.id.famous_place);
        place_by_region = findViewById(R.id.place_by_region);
        travel_tips = findViewById(R.id.travel_tips);
        food = findViewById(R.id.food);

        Glide.with(this).load("https://photo-cms-sggp.zadn.vn/w580/Uploaded/2021/uhtcplu/2019_03_22/dalat_btbg.jpg").centerCrop().into(famous_place_background_imageview);
        Glide.with(this).load("https://kenh14cdn.com/thumb_w/660/203336854389633024/2021/5/1/img6110-1619861109621834840211.jpg").centerCrop().into(place_by_region_background_imageview);
        Glide.with(this).load("https://static.tago.vn/media/source/H%C3%A0%20Tago/kinh-nghiem-du-lich-da-lat-cho-nhom-ban-tago-01_1.jpg").centerCrop().into(travel_tips_background_imageview);
        Glide.with(this).load("https://deih43ym53wif.cloudfront.net/cao-lau-vietnam-shutterstock_1210948300_056c01187b.jpeg").centerCrop().into(food_background_imageview);

        famous_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CategoryItemListActivity.class);
                intent.putExtra("selected_category", "famous_place");
                startActivity(intent);
            }
        });

        place_by_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CategoryItemListActivity.class);
                intent.putExtra("selected_category", "place_by_region");
                startActivity(intent);
            }
        });

        travel_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CategoryItemListActivity.class);
                intent.putExtra("selected_category", "travel_tips");
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CategoryItemListActivity.class);
                intent.putExtra("selected_category", "food");
                startActivity(intent);
            }
        });
    }
}