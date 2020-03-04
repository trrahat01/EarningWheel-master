package wheel.top.best.online.money.make.techyunk.com.earningwheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Home_page extends AppCompatActivity implements RewardedVideoAdListener {

    TextView score_text;
    LinearLayout spin_button, logout_button, redeem_button, watch_video_buuton,share_id,rate_button;

    private RewardedVideoAd mRewardedVideoAd;

    int myIntValue;
    DatabaseReference  user_id_child;
    String user_id;
    DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7464620770011345~4348903731");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);


        getSupportActionBar().setTitle("Earn Money");


        // Admob video ads
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7464620770011345~4348903731");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        spin_button = (LinearLayout) findViewById(R.id.spin_linear_id);
        redeem_button = (LinearLayout) findViewById(R.id.linear_redeem_id);
        logout_button = (LinearLayout) findViewById(R.id.linear_logout_id);
        watch_video_buuton = (LinearLayout) findViewById(R.id.linear_watch_video_id);
        share_id = (LinearLayout)findViewById(R.id.linear_share_id);
        score_text = (TextView) findViewById(R.id.wallet_text_score_id);
       rate_button = (LinearLayout)findViewById(R.id.linear_rate_id);


        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("your_int_key", 0);



        rate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("store url");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });








        watch_video_buuton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

               Toast.makeText(Home_page.this,"Please tap more times to load the ad", Toast.LENGTH_LONG).show();
                if (mRewardedVideoAd.isLoaded()) {


                    mRewardedVideoAd.show();
                }
           else {


                    loadRewardedVideoAd();
                }

            }
        });


        redeem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_page.this, Redeem.class);

                startActivity(intent);

                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglog();
            }
        });


        spin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home_page.this, Spinwheel.class);

                intent.putExtra("INT",myIntValue);

                startActivity(intent);


                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });


        share_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(view);
            }
        });













    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout_id: {
                logout();
            }

            return true;


        }


        return super.onOptionsItemSelected(item);


    }

    private void logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Home_page.this, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void finish() {
        super.finish();


      finish();

    }









// ADMOB VIDEO METHODS

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Toast.makeText(Home_page.this,"Congratulations, you will get 1000 points", Toast.LENGTH_SHORT).show();

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        int n =  myIntValue+1000;
        editor.putInt("your_int_key", n);
        editor.commit();
        user_id_child.child("scores").setValue(+n);



    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void loadRewardedVideoAd() {

        if(!mRewardedVideoAd.isLoaded()){
        mRewardedVideoAd.loadAd("ca-app-pub-7464620770011345/2652678686",
                new AdRequest.Builder().build());


        }
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }










    @Override
    protected void onStart() {
        super.onStart();

        String s = String.valueOf(myIntValue);
        score_text.setText(s);

        loadRewardedVideoAd();


    }


    public void share(View view){

//sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Earn Money");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Earn unlimited money by simple tasks"+"  "+"store Url link");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));



    }
    public void diaglog(){


        final Dialog dialog = new Dialog(Home_page.this);
        dialog.setContentView(R.layout.custom_logout);
        Button no = (Button) dialog.findViewById(R.id.no_id);

        Button yes = (Button)dialog.findViewById(R.id.yes_id);

        // if button is clicked, close the custom dialog
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        dialog.show();

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



