package edu.unipr.valdrin.finalandroidassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends DialogFragment {

    TextInputEditText username,email,password,confirmPass;
    Button register,btnback;
    //FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressBar loading;
    //DatabaseReference ref;
    //fullscreen dialog for signup
    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.dialogFull);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup,container,false);
        auth = FirebaseAuth.getInstance();

        loading = view.findViewById(R.id.loading);
        username = view.findViewById(R.id.addusernameregister);
        email = view.findViewById(R.id.addemailregister);
        password = view.findViewById(R.id.addpasswordregister);
        confirmPass = view.findViewById(R.id.addpasswordconfirm);
        register = view.findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String conpass = confirmPass.getText().toString();
                if(name.isEmpty() || mail.isEmpty() || pass.isEmpty() || !conpass.equals(pass)){
                    message("Please fill all the information correctly!");
                }else if (pass.length() < 6){
                    message("Your password should be at least 6 characters!");
                }else {
                    createAccount(name);
                }
            }
        });
        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void createAccount(String fullName) {
        auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String id = firebaseUser.getUid();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("userid", id);
                    hashMap.put("username", fullName);
                    hashMap.put("photo", "default");
                    database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                loading.setVisibility(View.VISIBLE);
                                message("Your account was successfully created!");
                                Intent intent = new Intent(getContext(), Dashboard.class);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    message(task.getException().getLocalizedMessage());
                }
            }
        });

    }
    void message(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }
    /*void showMessage(String title, String message){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }*/
}
