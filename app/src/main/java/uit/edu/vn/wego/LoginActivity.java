package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    TextView toRegisterBtn;

    TextView txtUsername, txtPassword;
    Button btLogin;

    private RequestQueue queue;

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
                    Toast.makeText(getApplicationContext(), "Dien day du thong tin", Toast.LENGTH_SHORT).show();
                } else {
                    String data = "{" + "\"username\":\"" + username +
                            "\",\"password\":\"" + password + "\"}";
//                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    submitLogin(data);
                }

            }
        });
    }

    private void submitLogin(String dataSubmit) {
        String url = "http://192.168.1.12:3000/user/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String type = response.getString("type");
//                    Toast.makeText(getApplicationContext(), "type: "+type, Toast.LENGTH_SHORT).show();
                    if (type.equals("Not exists user")) {
                        Toast.makeText(getApplicationContext(), "Not exists user", Toast.LENGTH_SHORT).show();
                    } else if (type.equals("login fail")) {
                        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
                    } else {
                        String token = response.getString("token");
                        String message = response.getString("message");
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
}