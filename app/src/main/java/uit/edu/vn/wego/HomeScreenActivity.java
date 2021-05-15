package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
    public void homebtn(View v)
    {
        ImageButton b = (ImageButton)v;
        ((ImageButton) v).setImageResource(R.drawable.home);
    }

}