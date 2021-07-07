package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import uit.edu.vn.wego.adapter.ModelItemReviewPost;
import uit.edu.vn.wego.adapter.ModelItemUser;
import uit.edu.vn.wego.adapter.RecyclerAdapter;

public class ReviewPostShow extends AppCompatActivity {
    private ModelItemReviewPost item;
    private TextView update_date;
    private TextView title;
    private TextView content;
    private ImageView image;
    private LinearLayout comments;

    private ImageButton like_button;
    private ImageButton unlike_button;
    private ImageButton comment_button;
    private ImageButton location_button;

    private LinearLayout comment_section;

    private Button postComment_button;
    private EditText writeComment_editText;

    private ModelItemUser itemUser;

    private RequestQueue queue;

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
        unlike_button = findViewById(R.id.unlike_btn_reviewpost);

        comment_button = findViewById(R.id.cmt_btn_reviewpost);
        location_button = findViewById(R.id.map_btn_reviewpost);

        comment_section = findViewById(R.id.review_post_comment_section);

        postComment_button = findViewById(R.id.post_comment);
        writeComment_editText = findViewById(R.id.write_comment_edittext);

        if (LoginActivity.getUser() != null) {
            itemUser = LoginActivity.getUser();
        }

        if(getIntent().getStringExtra("likeStatus").equals("like_gone")){
            like_button.setVisibility(View.GONE);
            unlike_button.setVisibility((View.VISIBLE));
        }else{
            like_button.setVisibility(View.VISIBLE);
            unlike_button.setVisibility(View.GONE);
        }

        loadComment();

        queue = Volley.newRequestQueue(this);

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLike_UnLikePost("like")){
                    like_button.setVisibility(View.GONE);
                    unlike_button.setVisibility((View.VISIBLE));
                }
            }
        });

        unlike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLike_UnLikePost("unlike")){
                    like_button.setVisibility(View.VISIBLE);
                    unlike_button.setVisibility(View.GONE);
                }
            }
        });

        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment_section.getVisibility() == View.VISIBLE) {
                    comment_section.setVisibility(View.GONE);
                } else {
                    comment_section.setVisibility(View.VISIBLE);
                }
            }
        });

        postComment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = writeComment_editText.getText().toString().trim();
                if (!comment.isEmpty()) {
                    String dataSubmit = "{" + "\"comment\":\"" + comment + "\"}";
                    userCommentPost(dataSubmit);
                    writeComment_editText.setText("");
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(writeComment_editText.getWindowToken(), 0);
            }
        });

        if (item.getLocationURL().isEmpty())
        {
            location_button.setVisibility(View.GONE);
        }
        else
        {
            location_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLocationURL()));
                    startActivity(browserIntent);
                }
            });
        }

        Fresco.initialize(this);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder(ReviewPostShow.this, item.getImgURL())
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, ReviewPosts.class);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
    }

    private void loadComment(){
        comments.removeAllViews();
        for (int i = 0; i < item.getComment().size(); ++i) {
            TextView temp_textview = new TextView(this);
            temp_textview.setText(item.getComment().get(i));
            temp_textview.setTextSize(20);
            temp_textview.setTextColor(getResources().getColor(R.color.black));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            temp_textview.setLayoutParams(params);

            LinearLayout temp_linearlayout = new LinearLayout(this);
            temp_linearlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            temp_linearlayout.setBackgroundColor(getResources().getColor(R.color.black));
            comments.addView(temp_linearlayout);
            comments.addView(temp_textview);
        }
    }

    private boolean userLike_UnLikePost(String status) {

        if (itemUser == null) {
            Toast.makeText(getApplicationContext(), "You must login", Toast.LENGTH_SHORT).show();
            return false;
        }
        //TODO: check if like or unlike

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/"+status+"/" + item.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("done")) {
                        // Toast.makeText(getApplicationContext(), "Added to favorite list", Toast.LENGTH_SHORT).show();

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
//                Intent intent = new Intent(ReviewPostShow.this, HomeScreenActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);

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

    private void userCommentPost(String comment) {

        if (itemUser == null) {
            Toast.makeText(getApplicationContext(), "You must login", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/comment/" + item.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("comment success")) {
                        Toast.makeText(getApplicationContext(), "Comment posted", Toast.LENGTH_SHORT).show();

                        JSONArray temp = response.getJSONArray("newList");
                        ArrayList<String> newListComment = new ArrayList<String>();
                        for (int i = 0; i < temp.length(); i++) {
                            newListComment.add(temp.getString(i));
                        }
                        item.setComment(newListComment);
                        loadComment();
                        //TODO: set enter key

                    } else {
                        Toast.makeText(getApplicationContext(), "Comment is not posted", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ReviewPostShow.this, HomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return comment == null ? null : comment.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", itemUser.getToken());
                return params;
            }
        };
        queue.add(request);
    }
}