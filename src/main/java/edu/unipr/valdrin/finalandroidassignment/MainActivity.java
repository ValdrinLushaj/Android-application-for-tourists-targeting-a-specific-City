package edu.unipr.valdrin.finalandroidassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView goRegister,forgotpassword,email,password,forgetpasword;
    Button btnlogin;
    FirebaseUser user;
    FirebaseAuth auth;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.addemail);
        password = findViewById(R.id.addpassword);
        forgetpasword = findViewById(R.id.forgotpassword);
        loading = findViewById(R.id.loading);

        goRegister = findViewById(R.id.goRegister);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment;
                dialogFragment = new SignUp();
                dialogFragment.show(getSupportFragmentManager(),"SignUp");
            }
        });
        forgotpassword = findViewById(R.id.forgotpassword);
        forgetpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment;
                dialogFragment = new ResetPasswordActivity();
                dialogFragment.show(getSupportFragmentManager(),"ResetPasswordActivity");
            }
        });
        //btnlogin = findViewById(R.id.btnlogin);
    }

    public void signin(View view) {
        /*String mail = email.getText().toString();
        String pass = password.getText().toString();*/
        if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loading.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "You are logged in!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        /*public void message (String string){
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }*/
    /*public void signout(View view){

        auth.signOut();
        message("You are signed out");
    }*/


    //to do this is the condition when the user has logged in, then enter the dashboard menu, so that it does not continue to login
    /*@Override
    protected void onStart() {
        super.onStart();
        if(user!=null){
            Intent  intent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            //message("You are already logged in!");
            finish();
        }
    }*/

    /*public void writedb(View view){
        ref.setValue("Hello Valdrin!");
    }*/

    /*void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }*/
    }
}