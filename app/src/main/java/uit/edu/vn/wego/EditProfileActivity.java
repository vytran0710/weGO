package uit.edu.vn.wego;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import uit.edu.vn.wego.adapter.ModelItemUser;
import uit.edu.vn.wego.model.NewAvatarData;

public class EditProfileActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    private ImageButton apply_btn;
    private ImageButton cancel_btn;
    private Button logout_btn;
    private Button change_password_info_btn;
    private Button apply_change_password_btn;
    private TextView change_profile_photo_btn;

    private LinearLayout info_group;
    private LinearLayout password_group;

    private ImageView profile_image;

    private EditText fullName_edt;
    private EditText email_edt;

    private EditText oldPassword_edt;
    private EditText newPassword_edt;
    private EditText confirmPassword_edt;

    private ModelItemUser itemUser;
    private Context context;

    private RequestQueue queue;

    private File selectedImageFile;
    private boolean confirmUpdatePhoto = false;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        apply_change_password_btn = findViewById(R.id.apply_change_password_btn);

        apply_btn = findViewById(R.id.apply_edit_btn);
        cancel_btn = findViewById(R.id.cancel_edit_btn);
        logout_btn = findViewById(R.id.logout_button);

        change_password_info_btn = findViewById(R.id.change_password_information_btn);
        change_profile_photo_btn = findViewById(R.id.change_profile_photo_button);

        profile_image = findViewById(R.id.edit_profile_image);

        fullName_edt = findViewById(R.id.fullName_editText);
        email_edt = findViewById(R.id.email_editText);
        oldPassword_edt = findViewById(R.id.oldPassword_editText);
        newPassword_edt = findViewById(R.id.newPassword_editText);
        confirmPassword_edt = findViewById(R.id.confirmPassword_editText);

        info_group = findViewById(R.id.edit_profile_info_group);
        password_group = findViewById(R.id.change_password_group);

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
                if (itemUser.getFullName().equals(fullName) && itemUser.getEmail().equals(email)) {
                    if (confirmUpdatePhoto) {
                        setProgressDialog();
                        uploadAvatar();
                    }
                    return;
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                setProgressDialog();
                updateProfile(fullName, email);
                if (confirmUpdatePhoto) {
                    uploadAvatar();
                }
            }
        });

        change_profile_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            EditProfileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    selectImage();
                }
            }
        });

        change_password_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info_group.getVisibility() == View.VISIBLE) {
                    change_password_info_btn.setText("change information");
                    info_group.setVisibility(View.GONE);
                    password_group.setVisibility(View.VISIBLE);
                } else {
                    change_password_info_btn.setText("change password");
                    info_group.setVisibility(View.VISIBLE);
                    password_group.setVisibility(View.GONE);
                }
            }
        });

        apply_change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPassword_edt.getText().toString();
                String newPassword = newPassword_edt.getText().toString();
                String confirmPassword = confirmPassword_edt.getText().toString();
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill full the information", Toast.LENGTH_LONG).show();
                    return;
                }
                setProgressDialog();
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Confirm password doesn't match new password", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                String data = "{" + "\"oldPassword\":\"" + oldPassword +
                        "\",\"newPassword\":\"" + newPassword + "\"}";
                changePassword(data);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    public void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {

                        selectedImageFile = new File(getPathFromUri(selectedImageUri));
                        Glide.with(context)
                                .load(selectedImageFile.toString())
                                .circleCrop()
                                .into(profile_image);
                        confirmUpdatePhoto = true;

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    private void uploadAvatar() {

        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), selectedImageFile);
        MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("avatar", selectedImageFile.getPath(), requestBodyAvt);

        ApiService.retrofit.getObject(itemUser.getId(), multipartBodyAvt).enqueue(new Callback<NewAvatarData>() {
            @Override
            public void onResponse(@NonNull Call<NewAvatarData> call, @NonNull retrofit2.Response<NewAvatarData> response) {
                NewAvatarData data = response.body();
                Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();

                itemUser.setAvatar(data.getAvatar());

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NewAvatarData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please login again", Toast.LENGTH_LONG).show();
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
                        if (!confirmUpdatePhoto) {
                            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
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
                    return dataSubmit.getBytes("utf-8");
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

    private void changePassword(String dataSubmit) {

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/changePassword";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("Old password does not match")) {
                        Toast.makeText(getApplicationContext(), "Old password does not match", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    if (message.equals("Change password successful")) {
                        Toast.makeText(getApplicationContext(), "Change password successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
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
                        Intent intent = new Intent(EditProfileActivity.this, HomeScreenActivity.class);
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
