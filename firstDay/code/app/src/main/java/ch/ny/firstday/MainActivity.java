package ch.ny.firstday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBackButton(View v){
        System.out.println("clicked Back");
    }

    public void onClickLoginButton(View v){
        Toast.makeText(v.getContext(), "Login successful", Toast.LENGTH_LONG).show();
    }
}