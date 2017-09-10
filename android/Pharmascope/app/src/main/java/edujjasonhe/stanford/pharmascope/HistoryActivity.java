// Use POST method + Google Forms for history attempt

package edujjasonhe.stanford.pharmascope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HistoryActivity extends AppCompatActivity {

    String nfcData;
    String formNFCID;
    String formNDC;
    String formLocation;

    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLSftM95uTc1omBUS88o82QGmMbaz61_BxvINEI8wT95-5Hu4UA/formResponse";
    //input element ids found from the live form page
    public static final String NFCID="entry.1607339597";
    public static final String NDC="entry.949584210";
    public static final String LOCATION="entry.2124242561";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        nfcData = intent.getStringExtra("nfcData");

        String[] split = nfcData.split(";");
        formNDC = split[0];
        formNFCID = split[1];
        formLocation = ((Globals) this.getApplication()).getDevNum();

        PostDataTask postDataTask = new PostDataTask();

        postDataTask.execute(URL, formNFCID, formNDC, formLocation);
    }

    private class PostDataTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String nfcid = contactData[1];
            String ndc = contactData[2];
            String location = contactData[3];
            String postBody="";

            try {
                postBody = NFCID+"=" + URLEncoder.encode(nfcid,"UTF-8") +
                        "&" + NDC + "=" + URLEncoder.encode(ndc,"UTF-8") +
                        "&" + LOCATION + "=" + URLEncoder.encode(location,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = false;
            }

            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
            } catch (IOException exception) {
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(HistoryActivity.this,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
