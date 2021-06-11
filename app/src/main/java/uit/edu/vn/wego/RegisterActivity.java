package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    TextView toLoginBtn;

    TextView txtUsername, txtEmail, txtPassword, txtConfirmPassword;
    Button btSignUp;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLoginBtn = (TextView) findViewById(R.id.to_login_button);
        txtUsername = (TextView) findViewById(R.id.register_username);
        txtEmail = (TextView) findViewById(R.id.register_email);
        txtPassword = (TextView) findViewById(R.id.register_password);
        txtConfirmPassword = (TextView) findViewById(R.id.register_confirm_password);
        btSignUp = (Button) findViewById(R.id.register_button);

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        queue = Volley.newRequestQueue(this);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String pw = txtPassword.getText().toString();
                String confirmPw = txtConfirmPassword.getText().toString();

                if (username.equals("") || email.equals("") || pw.equals("") || confirmPw.equals("")) {
                    Toast.makeText(getApplicationContext(), "Dien day du thong tin", Toast.LENGTH_SHORT).show();
                } else if (pw.equals(confirmPw)) {
                    String data = "{" + "\"username\":\"" + username +
                            "\",\"email\":\"" + email +
                            "\",\"password\":\"" + pw + "\"}";
                    //Log.d("data",data);
                    submitSignUp(data);

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Password doesn't match, please type again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void submitSignUp(String dataSubmit) {
        String url = "https://we-go-app2021.herokuapp.com/user/signup";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("Username exists")) {
                        Toast.makeText(getApplicationContext(), "Username exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Create successful", Toast.LENGTH_SHORT).show();
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