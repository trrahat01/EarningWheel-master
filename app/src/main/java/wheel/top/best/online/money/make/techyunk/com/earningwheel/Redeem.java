package wheel.top.best.online.money.make.techyunk.com.earningwheel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Redeem extends AppCompatActivity {


    EditText redeem_no;
    Button submit,contact_id;
    String user_no;
    String user_id;
    DatabaseReference  user_id_child;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        redeem_no = (EditText) findViewById(R.id.redeem_mob_number);

        submit = (Button) findViewById(R.id.redeem_submit);
        contact_id = (Button) findViewById(R.id.contact_id);



        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);



contact_id.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Uri uri = Uri.parse("");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
});







        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        final int myIntValue = sp.getInt("your_int_key", -1);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                user_no = redeem_no.getText().toString();

                if (TextUtils.isEmpty(user_no)){

                    Toast.makeText(Redeem.this,"Fill The Detail",Toast.LENGTH_SHORT).show();

               }


               else if(myIntValue>20000){

                    Toast.makeText(Redeem.this,"Congratulations, You will get reward within 24 hours",Toast.LENGTH_SHORT).show();

                    user_id_child.child("details").setValue(user_no);

                }


                else{

                    Toast.makeText(Redeem.this,"You need minimum 10,000 points",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }




}