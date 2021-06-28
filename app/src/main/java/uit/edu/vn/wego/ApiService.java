package uit.edu.vn.wego;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import uit.edu.vn.wego.model.NewAvatarData;

public interface ApiService {

    ApiService retrofit = new Retrofit.Builder()
            .baseUrl("https://we-go-app2021.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @Multipart
    @POST("user/{id}/upAvatar")
    Call<NewAvatarData> getObject(@Path("id") String userId, @Part MultipartBody.Part avatar);
}
