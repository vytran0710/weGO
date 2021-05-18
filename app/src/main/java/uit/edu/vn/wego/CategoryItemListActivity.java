package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uit.edu.vn.wego.R;
import uit.edu.vn.wego.adapter.CategoryAdapter;
import uit.edu.vn.wego.adapter.ModelItemCategory;

public class CategoryItemListActivity extends AppCompatActivity {

    private String selected_category;
    private CategoryAdapter adapter;
    private RecyclerView recyclerview;
    private ArrayList<ModelItemCategory> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_list);

        selected_category = getIntent().getStringExtra("selected_category");
        recyclerview = findViewById(R.id.category_item_recyclerview);

        Toast.makeText(this,selected_category,Toast.LENGTH_LONG).show();

        switch(selected_category)
        {
            case "famous_place":
                initialize_famous_place_category();
                break;
            case "place_by_region":
                initialize_place_by_region_category();
                break;
            case "travel_tips":
                break;
            case "food":
                break;
        }
    }

    private void initialize_famous_place_category()
    {
        array = new ArrayList<ModelItemCategory>();
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
        array.add(new ModelItemCategory("Miền Nam",
                "http://tiasang.com.vn/Portals/0/Images/Tac-dong-kinh-te-DBSCL-a1.jpg",
                "miennam"));
        array.add(new ModelItemCategory("Miền Trung",
                "https://odt.vn/storage/01-2021/tay-nguyen-don-mot-loat-du-an-cua-cac-ong-lon.jpg",
                "mientrung"));
        array.add(new ModelItemCategory("Miền Bắc",
                "https://lh3.googleusercontent.com/proxy/6ZEsfyLdKbDFFiYfv6AHvfB8eakkNlO3KV1x0j4i37g3NyoFGsVloNq1zjrZhKk6U1JAmBKZt7ISWkts1cw6PR4C0TQqUODUpN7oqWDqL2rXuEP2J4OmrqCrXn5AHNRjlgSKGCTRC5W6xMW0e8lAt_YiR9ovcwdBzpzKR8GPyYksvINMMwWDHa46AZ0HFAIA1P1mxB19ulCUZ7EiG241XzpZWIuCLQ",
                "mienbac"));
        adapter = new CategoryAdapter(this, array);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
//    private void intialize_travel_tips_category()
//    {
//        array = new ArrayList<ModelItemCategory>();
//        array.add(new ModelItemCategory("Cắm trại",
//                "https://media.cntraveler.com/photos/607313c3d1058698d13c31b5/16:9/w_2560%2Cc_limit/FamilyCamping-2021-GettyImages-948512452-2.jpg",
//                "camtrai"));
//        adapter = new CategoryAdapter(this, array);
//        recyclerview.setAdapter(adapter);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//    }
//    private void initialize_food_category()
//    {
//        array = new ArrayList<ModelItemCategory>();
//        array.add(new ModelItemCategory("Đà Lạt",
//                "https://vcdn1-dulich.vnecdn.net/2019/05/23/12-1558593963.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=sEbfKs9N6CgwUja6gayIJA",
//                "dalat"));
//        adapter = new CategoryAdapter(this, array);
//        recyclerview.setAdapter(adapter);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//    }
}