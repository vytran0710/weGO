package uit.edu.vn.wego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uit.edu.vn.wego.HomeScreenActivity;
import uit.edu.vn.wego.LoginActivity;
import uit.edu.vn.wego.R;
import uit.edu.vn.wego.ReviewPostShow;
import uit.edu.vn.wego.ReviewPosts;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ModelItemReviewPost> item_model;
    ModelItemUser itemUser;

    public RecyclerAdapter(Context context, ArrayList<ModelItemReviewPost> item_model) {
        this.context = context;
        this.item_model = item_model;
        if (LoginActivity.getUser() != null) {
            this.itemUser = LoginActivity.getUser();
        } else {
            this.itemUser = null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView review_post_imageview;
        private CardView item;
        private TextView update_date;

        private ImageButton unlike_button;
        private ImageButton like_button;
        private ImageButton comment_button;
        private ImageButton location_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textview);
            review_post_imageview = itemView.findViewById(R.id.review_post_imageview);
            item = itemView.findViewById(R.id.review_post);
            update_date = itemView.findViewById(R.id.update_date_textview);

            unlike_button = itemView.findViewById(R.id.unlike_btn);
            like_button = itemView.findViewById(R.id.like_btn);
            comment_button = itemView.findViewById(R.id.cmt_btn);
            location_button = itemView.findViewById(R.id.map_btn);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (itemUser != null) {
            for (int i = 0; i < itemUser.getLikedPostId().size(); i++) {
                if (itemUser.getLikedPostId().get(i).equals(item_model.get(position).getId())) {
                    holder.like_button.setVisibility(View.GONE);
                    holder.unlike_button.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        holder.title.setText(item_model.get(position).getTitle());
        holder.update_date.setText(item_model.get(position).getUpdate_date());
        Glide.with(context).load(item_model.get(position).getImgURL().get(0)).centerCrop().into(holder.review_post_imageview);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReviewPostShow.class);
                intent.putExtra("item", item_model.get(position));
                if (holder.like_button.getVisibility() == View.GONE) {
                    intent.putExtra("likeStatus", "like_gone");
                } else {
                    intent.putExtra("likeStatus", "like_visible");
                }
                ((Activity) v.getContext()).startActivity(intent);
            }
        });

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userLike_UnLikePost("like", position)) {
                    holder.like_button.setVisibility(View.GONE);
                    holder.unlike_button.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.unlike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userLike_UnLikePost("unlike", position)) {
                    holder.like_button.setVisibility(View.VISIBLE);
                    holder.unlike_button.setVisibility(View.GONE);
                }
            }
        });

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReviewPostShow.class);
                intent.putExtra("item", item_model.get(position));
                if (holder.like_button.getVisibility() == View.GONE) {
                    intent.putExtra("likeStatus", "like_gone");
                } else {
                    intent.putExtra("likeStatus", "like_visible");
                }
                ((Activity) v.getContext()).startActivity(intent);
            }
        });

        if (item_model.get(position).getLocationURL().isEmpty())
        {
            holder.location_button.setVisibility(View.GONE);
        }
        else
        {
            holder.location_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item_model.get(position).getLocationURL()));
                    ((Activity) v.getContext()).startActivity(browserIntent);
                }
            });
        }
    }

    private boolean userLike_UnLikePost(String status, int position) {

        RequestQueue queue = Volley.newRequestQueue(context);

        if (itemUser == null) {
            Toast.makeText(context, "You must login", Toast.LENGTH_SHORT).show();
            return false;
        }
        //TODO: check if like or unlike

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/" + status + "/" + item_model.get(position).getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("done")) {
                        // Toast.makeText(context, "Added to favorite list", Toast.LENGTH_SHORT).show();

                        JSONArray temp = response.getJSONArray("newList");
                        ArrayList<String> newListLiked = new ArrayList<String>();
                        for (int i = 0; i < temp.length(); i++) {
                            newListLiked.add(temp.getString(i));
                        }
                        itemUser.setLikedPostId(newListLiked);
                        LoginActivity.setUser(itemUser);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", itemUser.getToken());
                return params;
            }
        };
        queue.add(request);
        return true;
    }

    @Override
    public int getItemCount() {
        return item_model.size();
    }
}
