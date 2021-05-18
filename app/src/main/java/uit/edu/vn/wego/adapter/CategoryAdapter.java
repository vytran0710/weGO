package uit.edu.vn.wego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uit.edu.vn.wego.CategoryItemListActivity;
import uit.edu.vn.wego.R;
import uit.edu.vn.wego.ReviewPosts;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<ModelItemCategory> item_model;
    public CategoryAdapter(Context context, ArrayList<ModelItemCategory> item_model)
    {
        this.context = context;
        this.item_model = item_model;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView imgURL;
        private CardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name_textview);
            imgURL = itemView.findViewById(R.id.category_item_imageview);
            item = itemView.findViewById(R.id.category_item);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.name.setText(item_model.get(position).getName());
        Glide.with(context).load(item_model.get(position).getImgURL()).centerCrop().into(holder.imgURL);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReviewPosts.class);
                intent.putExtra("selected_item", item_model.get(position).getTag());
                ((Activity)v.getContext()).startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return item_model.size();
    }
}

