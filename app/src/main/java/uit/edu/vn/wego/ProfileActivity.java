package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import uit.edu.vn.wego.adapter.ModelItemUser;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton home_btn;

    private Button to_login_btn;
    private TextView to_register_btn;

    private ViewGroup profile_group;
    private ViewGroup login_Group;

    private ModelItemUser itemUser;

    private ImageView profile_image;
    private TextView profile_name;
    private TextView profile_username;
    private CardView favorite_articles;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        home_btn = findViewById(R.id.home_btn_3);
        to_login_btn = findViewById(R.id.to_login_button_1);
        to_register_btn = findViewById(R.id.to_register_button_1);

        profile_group = findViewById(R.id.profile_group);
        login_Group = findViewById(R.id.login_group_1);

        profile_image = findViewById(R.id.profile_image);
        profile_name = findViewById(R.id.profile_name);
        profile_username = findViewById(R.id.profile_username);
        favorite_articles = findViewById(R.id.favorite_articles);

        context = this;

        if (LoginActivity.getUser() == null) {
            profile_group.setVisibility(View.GONE);
            login_Group.setVisibility(View.VISIBLE);
            itemUser = null;
        } else {
            profile_group.setVisibility(View.VISIBLE);
            login_Group.setVisibility(View.GONE);
            itemUser = LoginActivity.getUser();

            Glide.with(context)
                    .load(itemUser.getAvatar())
                    .circleCrop()
                    .into(profile_image);
            profile_name.setText(itemUser.getFullName());
            profile_username.setText(itemUser.getUsername());
        }

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        to_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        to_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //TODO: check if logged in or not
}