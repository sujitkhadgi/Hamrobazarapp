package com.shallow.hamrobazar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.shallow.hamrobazar.Adapter.ItemAdapter;
import com.shallow.hamrobazar.Api.ItemAPI;
import com.shallow.hamrobazar.Model.Item;
import com.shallow.hamrobazar.R;
import com.shallow.hamrobazar.Url.Url;

import java.util.List;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView itemsRecyclerView,itemsRecyclerViewTrend;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private Timer timer;
    private  int currentPosition=0;
   private ViewFlipper viewFlipper;
            private CircleImageView imageProfile;
            private RecyclerView recyclerView;
            private RecyclerView recentrecycleview;
    public static String token="";//to check logged in or not
    private  int [] imageFiles={R.drawable.aa,R.drawable.ab,R.drawable.abc};


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_dashboard);

                //imageProfile = findViewById(R.id.imgProfile);
                recyclerView = findViewById(R.id.recycleview);

                recentrecycleview = findViewById(R.id.recentrecycleview);
              viewFlipper = findViewById(R.id.viewFlipper);

                drawerLayout=findViewById(R.id.drawerLayout);
                toolbar=findViewById(R.id.toolbar);


                setSupportActionBar(toolbar);
                ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.start,R.string.end);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();


 for (int image:imageFiles)
 {
     flipperimages(image);
 }
                Retrofit retrofit=Url.retrofit;
                ItemAPI itemAPI=Url.itemAPI;
                Call<List<Item>>listCall=itemAPI.getAllRecentItems();
                listCall.enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        List<Item>items=response.body();

                        ItemAdapter itemAdapter=new ItemAdapter(DashboardActivity.this,items);
                        recentrecycleview.setAdapter(itemAdapter);
                        recentrecycleview.setLayoutManager(new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.HORIZONTAL,false));

                    }


                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, "failed:"+t, Toast.LENGTH_SHORT).show();
                    }
                });


                Call<List<Item>>call=itemAPI.getAllTrendingItems();
                call.enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        List<Item>items=response.body();

                        ItemAdapter itemAdapter=new ItemAdapter(DashboardActivity.this,items);
                        recyclerView.setAdapter(itemAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, "failed:"+t, Toast.LENGTH_SHORT).show();

                    }
                });

                //loadCurrentUser();
            }

    //for slide show
    public void flipperimages(int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);


        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.user:
                if(token.equals(""))
                {
                    Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(DashboardActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }

        }
        return super.onOptionsItemSelected(item);
    }
}




