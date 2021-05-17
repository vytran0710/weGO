package uit.edu.vn.wego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uit.edu.vn.wego.R;

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
        private ImageView imgview;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            imgview = itemView.findViewById(R.id.image_view);
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
    }
    @Override
    public int getItemCount(){
        return item_model.size();
    }
}
