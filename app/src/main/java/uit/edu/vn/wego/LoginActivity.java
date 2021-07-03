package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import uit.edu.vn.wego.adapter.ModelItemUser;

public class LoginActivity extends AppCompatActivity {

    TextView toRegisterBtn;

    TextView txtUsername, txtPassword;
    Button btLogin;

    private RequestQueue queue;

    private static ModelItemUser itemUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toRegisterBtn = (TextView) findViewById(R.id.to_register_button);
        txtUsername = (TextView) findViewById(R.id.login_username);
        txtPassword = (TextView) findViewById(R.id.login_password);
        btLogin = (Button) findViewById((R.id.login_button));

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        queue = Volley.newRequestQueue(this);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "More information required", Toast.LENGTH_SHORT).show();
                } else {
                    setProgressDialog();
                    String data = "{" + "\"username\":\"" + username +
                            "\",\"password\":\"" + password + "\"}";
                    submitLogin(data);
                }
            }
        });
    }

    private void setProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void submitLogin(String dataSubmit) {

        String url = "https://we-go-app2021.herokuapp.com/user/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String type = response.getString("type");

                    if (type.equals("Not exists user")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    } else if (type.equals("Login fail")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Username or password is invalid", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject user = response.getJSONObject("user");
                        String id = user.getString("_id");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String fullName = user.getString("fullName");
                        String avatar = user.getString("avatar");
                        String token = user.getString("token");

                        JSONArray temp = user.getJSONArray("listLikedPostId");
                        ArrayList<String> likedPostId = new ArrayList<String>();
                        for (int i = 0; i < temp.length(); i++) {
                            likedPostId.add(temp.getString(i));
                        }

                        setUser(new ModelItemUser(id, username, fullName, email, token, avatar, likedPostId));
                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        progressDialog.dismiss();

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
        };
        queue.add(request);
    }

    public static ModelItemUser getUser() {
        return (itemUser != null) ? itemUser : null;
    }

    public static void setUser(ModelItemUser user) {
        itemUser = user;
    }
}