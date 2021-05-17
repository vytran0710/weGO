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

        if(selected_category.equals("famous_place"))
        {
            array = new ArrayList<ModelItemCategory>();
            array.add(new ModelItemCategory("Đà Lạt", "https://vcdn1-dulich.vnecdn.net/2019/05/23/12-1558593963.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=sEbfKs9N6CgwUja6gayIJA", "dalat"));
            adapter = new CategoryAdapter(this, array);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}