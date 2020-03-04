package wheel.top.best.online.money.make.techyunk.com.earningwheel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {
EditText login_email, login_password;

FirebaseAuth auth;

Button login_button, register;

ProgressDialog progressDialog;

FirebaseAuth.AuthStateListener authStateListener;


    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(MainActivity.this);
progressDialog.setMessage("Logging....");
login_email = (EditText)findViewById(R.id.login_email);

login_password = (EditText)findViewById(R.id.login_password);

login_button = (Button)findViewById(R.id.login_button);

register = (Button)findViewById(R.id.login_register);



auth = FirebaseAuth.getInstance();






login_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String memail,mpassword;
        memail = login_email.getText().toString().trim();
        mpassword = login_password.getText().toString().trim();
progressDialog.show();

        if(TextUtils.isEmpty(memail)   || TextUtils.isEmpty(mpassword)   ){

            Toast.makeText(MainActivity.this,"FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
return;
        }

        auth.signInWithEmailAndPassword(memail, mpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Log.d("SUCCESS", "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            Intent intent = new Intent(MainActivity.this,Home_page.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            finishAffinity();

                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w("FAILED", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });




    }
});




register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

Intent intent = new Intent(MainActivity.this,Register.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_right,R.anim.slide_left);



    }
});

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
if(auth.getCurrentUser()!=null){

    startActivity(new Intent(MainActivity.this,Home_page.class));


}
            }
        };














    }

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);





    }

    @Override
    public void finish() {
        super.finish();
        finish();
    }
}
