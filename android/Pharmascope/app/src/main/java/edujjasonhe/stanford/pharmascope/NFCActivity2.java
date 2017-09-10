package edujjasonhe.stanford.pharmascope;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class NFCActivity2 extends AppCompatActivity {

    public static final String TAG = NFCActivity2.class.getSimpleName();

    public TextView mEtMessage;

    /*
    private Button mBtWrite;
    private Button mBtRead;

    private NFCWriteFragment mNfcWriteFragment;
    private NFCReadFragment mNfcReadFragment;
    */

    //private boolean isAttached = false;
    //private boolean isDialogDisplayed = false;
    //private boolean isWrite = false;

    public NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc2);

        //initViews();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }
/*
    private void initViews() {

        mEtMessage = (EditText) findViewById(R.id.et_message);
        //mBtWrite = (Button) findViewById(R.id.btn_write);
        mBtRead = (Button) findViewById(R.id.btn_read);

        //mBtWrite.setOnClickListener(view -> showWriteFragment());
        mBtRead.setOnClickListener(view -> showReadFragment());
    }

    private void showWriteFragment() {
        isWrite = true;
        mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);
        if (mNfcWriteFragment == null) {
            mNfcWriteFragment = NFCWriteFragment.newInstance();
        }
        mNfcWriteFragment.show(getFragmentManager(),NFCWriteFragment.TAG);
    }

    private void showReadFragment() {
        mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
        if (mNfcReadFragment == null) {
            mNfcReadFragment = NFCReadFragment.newInstance();
        }
        mNfcReadFragment.show(getFragmentManager(), NFCReadFragment.TAG);
    }

    @Override
    public void onDialogDisplayed() {
        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {
        isDialogDisplayed = false;
        isWrite = false;
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        //IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        //IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        //IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        //IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        //PendingIntent pendingIntent = PendingIntent.getActivity(
        //        this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //if(mNfcAdapter!= null)
        //    mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //if(mNfcAdapter!= null)
            //mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //Log.d(TAG, "onNewIntent: "+intent.getAction());
        if(tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);
            readFromNFC(ndef);
            //mNfcReadFragment = (NFCReadFragment)getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
            //mNfcReadFragment.onNfcDetected(ndef);
        }
    }

    public void readFromNFC(Ndef ndef) {
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: "+message);
            mEtMessage.setText(message);
            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();

        }
    }
}