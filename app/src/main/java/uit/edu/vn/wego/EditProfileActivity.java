package uit.edu.vn.wego;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import uit.edu.vn.wego.adapter.ModelItemUser;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton apply_btn;
    private ImageButton cancel_btn;
    private ImageButton logout_btn;
    private TextView change_profile_photo_btn;

    private ImageView profile_image;

    private EditText fullName_edt;
    private EditText email_edt;

    private ModelItemUser itemUser;
    private Context context;

    private RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        apply_btn = findViewById(R.id.apply_edit_btn);
        cancel_btn = findViewById(R.id.cancel_edit_btn);
        logout_btn = findViewById(R.id.logout_button);
        change_profile_photo_btn = findViewById(R.id.change_profile_photo_button);
        profile_image = findViewById(R.id.edit_profile_image);
        fullName_edt = findViewById(R.id.fullName_editText);
        email_edt = findViewById(R.id.email_editText);

        itemUser = LoginActivity.getUser();
        context = this;

        Glide.with(context)
                .load(itemUser.getAvatar())
                .circleCrop()
                .into(profile_image);
        fullName_edt.setText(itemUser.getFullName());
        email_edt.setText(itemUser.getEmail());

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        queue = Volley.newRequestQueue(this);

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullName_edt.getText().toString();
                String email = email_edt.getText().toString();
                if (fullName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill full the information", Toast.LENGTH_LONG).show();
                    return;
                }
                updateProfile(fullName, email);
            }
        });

        change_profile_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    private void updateProfile(String fullName, String email) {

        String dataSubmit = "{" + "\"fullName\":\"" + fullName +
                "\",\"email\":\"" + email + "\"}";

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("Update successful")) {
                        itemUser.setFullName(fullName);
                        itemUser.setEmail(email);
                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return dataSubmit == null ? null : dataSubmit.getBytes("utf-8");
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

    public void logOut() {
        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/logout";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("Logout successful")) {
                        //Toast.makeText(getApplicationContext(), "logout successful", Toast.LENGTH_SHORT).show();
                        LoginActivity.setUser(null);
                        Intent intent = new Intent(EditProfileActivity.this,HomeScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
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
    }

}
