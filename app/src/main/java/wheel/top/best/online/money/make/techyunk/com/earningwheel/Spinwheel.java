package wheel.top.best.online.money.make.techyunk.com.earningwheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Random;

public class Spinwheel extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;


    ImageView imageView_wheel;
    ImageButton imageButton_spin;
    int degree = 0;
    int degree_old = 0;
    Random r;
    int score=0;
    public static final float FACTOR = 15f;

    TextView textView;
    String user_id;

    DatabaseReference  user_id_child;
    int intValue;
    String current_score;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spinwheel);
        imageView_wheel = (ImageView) findViewById(R.id.wheel);
        imageButton_spin = (ImageButton) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textview);


getSupportActionBar().hide();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7464620770011345~4348903731");
current_score  = currentNumber(360 - (degree % 360));


        r = new Random();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);

       /* FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mroot = firebaseDatabase.getReference();
        DatabaseReference user_ref = mroot.child("users");*/






        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7464620770011345/7905005367");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());










        imageButton_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;

                RotateAnimation rotateAnimation = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, .5f,
                        RotateAnimation.RELATIVE_TO_SELF, .5f);


                rotateAnimation.setDuration(3600);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());


                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        textView.setText("score");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {



                        textView.setText( currentNumber(360 - (degree % 360)));


                        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();

                        int n =  intValue+score;
                        editor.putInt("your_int_key", n);
                        editor.commit();


                        SharedPreferences spe = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        int  myIntValue = spe.getInt("your_int_key", 0);


                        user_id_child.child("scores").setValue(+n);
                        diaglog();



                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }




                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView_wheel.startAnimation(rotateAnimation);


            }
        });








    }












    private String currentNumber(int degree){

        String text = "";



        if(degree>= (FACTOR*1) && degree<(FACTOR*3)  ){

            text = "2";

            score = score+2;

        }


        if(degree>= (FACTOR*3) && degree<(FACTOR*5)  ){

            text = "3";
            score = score+3;
        }

        if(degree>= (FACTOR*5) && degree<(FACTOR*7)  ){

            text = "10";
            score = score+10;
        }

        if(degree>= (FACTOR*7) && degree<(FACTOR*9)  ){

            text = "5";
            score = score+5;

        }

        if(degree>= (FACTOR*9) && degree<(FACTOR*11)  ){

            text = "6";
            score = score+6;
        }

        if(degree>= (FACTOR*11) && degree<(FACTOR*13)  ){

            text = "7";
            score = score+7;
        }

        if(degree>= (FACTOR*13) && degree<(FACTOR*15)  ){

            text = "8";
            score = score+8;
        }

        if(degree>= (FACTOR*15) && degree<(FACTOR*17)  ){

            text = "9";
            score = score+9;
        }

        if(degree>= (FACTOR*17) && degree<(FACTOR*19)  ){

            text = "100";
            score = score+100;
        }

        if(degree>= (FACTOR*19) && degree<(FACTOR*21)  ){

            text = "11";
            score = score+11;
        }

        if(degree>= (FACTOR*21) && degree<(FACTOR*23)  ){

            text = "12";
            score = score+12;
        }

        if(degree>= (FACTOR*23) && degree<(360) || degree>=0 && degree <(FACTOR*1) ){

            text = "0 point";

        }

        return text;



    }

public void diaglog(){


    final Dialog dialog = new Dialog(Spinwheel.this);
    dialog.setContentView(R.layout.custom_dialog);
    Button dialogButton = (Button) dialog.findViewById(R.id.cool_id);
    TextView textView = (TextView)dialog.findViewById(R.id.dialog_score_id);
    String a = currentNumber(360 - (degree % 360));

  textView.setText(a+" "+"Points");






    // if button is clicked, close the custom dialog

    dialogButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();


        }
    });
    dialog.show();

}

    @Override
    public void finish() {


    Intent intent = new Intent(Spinwheel.this,Home_page.class);

    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    startActivity(intent);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = getIntent();
     intValue = mIntent.getIntExtra("INT", 0);




    }
}
