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

import org.w3c.dom.Text;

import java.util.ArrayList;

import uit.edu.vn.wego.R;
import uit.edu.vn.wego.ReviewPostShow;
import uit.edu.vn.wego.ReviewPosts;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ModelItemReviewPost> item_model;
    public RecyclerAdapter(Context context, ArrayList<ModelItemReviewPost> item_model)
    {
        this.context = context;
        this.item_model = item_model;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView review_post_imageview;
        private CardView item;
        private TextView update_date;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textview);
            review_post_imageview = itemView.findViewById(R.id.review_post_imageview);
            item = itemView.findViewById(R.id.review_post);
            update_date = itemView.findViewById(R.id.update_date_textview);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.review_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.title.setText(item_model.get(position).getTitle());
        holder.update_date.setText(item_model.get(position).getUpdate_date());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReviewPostShow.class);
                intent.putExtra("item", item_model.get(position));
                ((Activity)v.getContext()).startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return item_model.size();
    }
}
