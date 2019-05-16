package ch.ny.firstday;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBackButton(View v){

        new AlertDialog.Builder(v.getContext())
                .setTitle("Exit")
                .setMessage("Do you really want to leave Michelin man?!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.sad_michelin)
                .show();
    }

    public void onClickLoginButton(View v){
        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        ImageView img = findViewById(R.id.michelinImage);
        if(password.getText().length() != 0 && username.getText().length() != 0) {
            password.getText().clear();
            username.getText().clear();
            img.setImageResource(R.drawable.michelin);
            Toast.makeText(v.getContext(), "Login successful", Toast.LENGTH_LONG).show();
        }else{
            img.setImageResource(R.drawable.sad_michelin);
            Toast.makeText(v.getContext(), "Username and password can't be empty", Toast.LENGTH_LONG).show();
        }
    }
}