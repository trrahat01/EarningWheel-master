package wheel.top.best.online.money.make.techyunk.com.earningwheel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    EditText name,email,password;
    Button register_button;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private static final String user_dr = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference().child(user_dr);
    name = (EditText)findViewById(R.id.register_name);
    email = (EditText)findViewById(R.id.register_email);
    password = (EditText)findViewById(R.id.register_password);

    register_button = (Button)findViewById(R.id.register_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");


        register_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {




        register_method();
    }
});
    }

    private void register_method() {



        progressDialog.show();

        final String user_name = name.getText().toString();

        final String user_email = email.getText().toString();

        String user_password = password.getText().toString();





        if(TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_name) || TextUtils.isEmpty(user_password))

        {
         progressDialog.dismiss();
            Toast.makeText(Register.this,"FILL ALL THE FIELDS", Toast.LENGTH_SHORT).show();
        return;
        }



        mAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "SUCCESS.",Toast.LENGTH_SHORT).show();
                            Log.d("SUCCESS", "createUserWithEmail:success");
                            String user_id = mAuth.getCurrentUser().getUid();

                           DatabaseReference  user_id_child =  databaseReference.child(user_id);

                           user_id_child.child("name").setValue(user_name);

                           user_id_child.child("email").setValue(user_email);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, ""+task.getException(),Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



    }

    @Override
    public void finish() {
        super.finish();
        finish();

    }
}
