package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uit.edu.vn.wego.R;
import uit.edu.vn.wego.adapter.CategoryAdapter;
import uit.edu.vn.wego.adapter.ModelItemCategory;
import uit.edu.vn.wego.adapter.ModelItemUser;

public class CategoryItemListActivity extends AppCompatActivity {

    private String selected_category;
    private CategoryAdapter adapter;
    private RecyclerView recyclerview;
    private ArrayList<ModelItemCategory> array;

    private ImageView home_button;
    private ImageView profile_button;
    private ImageView fav_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_list);

        selected_category = getIntent().getStringExtra("selected_category");
        recyclerview = findViewById(R.id.category_item_recyclerview);

        home_button = findViewById(R.id.home_btn_1);
        profile_button = findViewById(R.id.profile_btn_1);
        fav_button = findViewById(R.id.fav_btn_1);

        switch(selected_category)
        {
            case "famous_place":
                initialize_famous_place_category();
                break;
            case "place_by_region":
                initialize_place_by_region_category();
                break;
        }

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryItemListActivity.this,HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryItemListActivity.this,ProfileActivity.class);
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
    }

    private void initialize_famous_place_category()
    {
        array = new ArrayList<ModelItemCategory>();
        array.add(new ModelItemCategory("Hạ Long",
                "https://owa.bestprice.vn/images/tours/uploads/ha-long-tuan-chau-2-ngay-1-dem-5e5642a3b1b03.jpg",
                "halong"));
        array.add(new ModelItemCategory("Đà Lạt",
                "https://vcdn1-dulich.vnecdn.net/2019/05/23/12-1558593963.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=sEbfKs9N6CgwUja6gayIJA",
                "dalat"));
        adapter = new CategoryAdapter(this, array);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initialize_place_by_region_category()
    {
        array = new ArrayList<ModelItemCategory>();
        array.add(new ModelItemCategory("Miền Bắc",
                "https://cdn.yeudulich.com/media/cms/8a/18/8168-f0df-4531-a5f7-806bd60a7728.jpg",
                "mienbac"));
        array.add(new ModelItemCategory("Miền Trung",
                "https://odt.vn/storage/01-2021/tay-nguyen-don-mot-loat-du-an-cua-cac-ong-lon.jpg",
                "mientrung"));
        array.add(new ModelItemCategory("Miền Nam",
                "http://tiasang.com.vn/Portals/0/Images/Tac-dong-kinh-te-DBSCL-a1.jpg",
                "miennam"));
        adapter = new CategoryAdapter(this, array);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}