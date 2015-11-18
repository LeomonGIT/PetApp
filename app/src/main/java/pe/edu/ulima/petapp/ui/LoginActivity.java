package pe.edu.ulima.petapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.ui.navigator.MainActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLogin(View v){
        Log.e("clickLogin","clickeado");

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}
