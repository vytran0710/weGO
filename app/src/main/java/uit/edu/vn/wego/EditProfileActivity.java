package uit.edu.vn.wego;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
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

    private File selectedImageFile;
    private String encodeImage;
    private boolean confirmUpdatePhoto = false;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

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
                if (itemUser.getFullName().equals(fullName) && itemUser.getEmail().equals(email)) {
                    if (confirmUpdatePhoto) {
                        updatePhoto();
                    }
                    return;
                }
                updateProfile(fullName, email);
                if (confirmUpdatePhoto) {
                    updatePhoto();
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

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

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

                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        encodeImage = encodeBitmapImage(BitmapFactory.decodeStream(inputStream));

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

    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    private void updatePhoto() {
        Toast.makeText(getApplicationContext(), "test 1", Toast.LENGTH_SHORT).show();

        String url = "https://we-go-app2021.herokuapp.com/user/" + itemUser.getId() + "/upAvatar";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "test 2", Toast.LENGTH_SHORT).show();

                try {
                    String message = response.getString("message");
                    String error = response.getString("error");
                    Log.d("error",error);
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                    if (message.equals("Up avatar successful")) {
                        Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_SHORT).show();
                        String avatar = response.getString("avatar");
                        itemUser.setAvatar(avatar);
                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "test 3", Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("avatar",encodeImage);
                return params;
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
