package com.shallow.hamrobazar.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shallow.hamrobazar.Api.UserAPI;
import com.shallow.hamrobazar.R;
import com.shallow.hamrobazar.ServerResponse.ImageResponse;
import com.shallow.hamrobazar.StrictMode.StrictModeClass;
import com.shallow.hamrobazar.Url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    EditText etFullName, etEmail, etPassword, etMobileNo, etAddress;
    Button btnSignUp;
    CircleImageView imgProfile;
    String imagePath;
    String imageName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName =findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etAddress=findViewById(R.id.etAddress);
        etPassword=findViewById(R.id.etPassword);
        etMobileNo=findViewById(R.id.etMobileNo);
        btnSignUp=findViewById(R.id.btnSignUp);
        imgProfile =findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                signUp();
            }
        });
    }
    private void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imgProfile.setImageURI(uri);
        imagePath=getRealPathFormUri(uri);
    }
    private String getRealPathFormUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        Retrofit retrofit= Url.retrofit;
        UserAPI userAPI=Url.userAPI;
        // Call<Void>voidCall=userAPI.signUp(email,fullName,password,mobileNo,address);
        Call<ImageResponse> responseBodyCall = userAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signUp()
    {
        String fullName= etFullName.getText().toString();
        String email=etEmail.getText().toString();
        String address=etAddress.getText().toString();
        String password=etPassword.getText().toString();
        String mobileNo=etMobileNo.getText().toString();



        Retrofit retrofit= Url.retrofit;
        UserAPI userAPI=Url.userAPI;
        Call<Void>voidCall=userAPI.signUp(email,fullName,password,mobileNo,address,imageName);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(SignUpActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Registered Failed"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CheckPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }
}
