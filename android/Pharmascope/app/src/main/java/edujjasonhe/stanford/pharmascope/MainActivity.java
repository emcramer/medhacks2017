package edujjasonhe.stanford.pharmascope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, NFCActivity2.class);
        startActivity(intent);
    }

    public void click2(View view) {
        Intent intent = new Intent(this, SheetsTestActivity.class);
        startActivity(intent);
    }

    public void click3(View view) {
        Intent intent = new Intent(this, SheetsTestActivity.class);
        startActivity(intent);
    }

}
