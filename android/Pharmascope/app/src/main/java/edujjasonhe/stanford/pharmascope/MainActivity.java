package edujjasonhe.stanford.pharmascope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String R1 = "R1";
    String R2 = "R2";
    String R3 = "R3";
    String R4 = "R4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, ScopeActivity.class);
        //intent.putExtra("devNum", R1);
        startActivity(intent);
    }

    public void click2(View view) {
        Intent intent = new Intent(this, SheetsWriteActivity.class);
        intent.putExtra("nfcData", "1234;2345;3456;4567");
        intent.putExtra("devNum", R2);
        startActivity(intent);
    }

    public void click3(View view) {
        Intent intent = new Intent(this, NFCActivity.class);
        ((Globals) this.getApplication()).setDevNum(R3);
        startActivity(intent);
    }

    public void click4(View view) {
        Intent intent = new Intent(this, SheetsTestActivity.class);
        intent.putExtra("devNum", R4);
        startActivity(intent);
    }

}
