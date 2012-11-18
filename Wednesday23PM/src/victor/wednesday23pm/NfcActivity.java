package victor.wednesday23pm;

import java.util.Iterator;
import java.util.List;
import java.lang.Byte;

import victor.wednesday23pm.LocationActivity.MyLocationListener;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.nfc.*;
import android.nfc.tech.*;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.widget.Toast;

public class NfcActivity extends Activity {

	private NfcManager manager;
	private NfcAdapter adapter;
	private boolean inWriteMode = false;
	static public String tagContent_ = "";
	static public String tagID_ = "";
	private TextView tView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		manager = (NfcManager) getSystemService(NFC_SERVICE);
		adapter = manager.getDefaultAdapter();

		tView = (TextView) findViewById(R.id.textViewNFC);

		final Button buttonLocation = (Button) findViewById(R.id.ButtonStartNFC);
		buttonLocation.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("NFC", "Start");

				startScanning();
			}
		});

		final Button buttonStopLocation = (Button) findViewById(R.id.ButtonStopNFC);
		buttonStopLocation.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("NFC", "End");

				stopScanning();
				// LocationManager lm = (LocationManager)
				// getSystemService(LOCATION_SERVICE);
				// lm.removeUpdates(mlocListener);

			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (!inWriteMode)
			return;

		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()))
			Toast.makeText( getApplicationContext(), "ACTION_TECH_DISCOVERED", Toast.LENGTH_SHORT).show();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
			Toast.makeText( getApplicationContext(), "ACTION_NDEF_DISCOVERED", Toast.LENGTH_SHORT).show();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			Bundle b = intent.getExtras();
			Object BID = b.get("android.nfc.extra.ID");
			String ID = BID.toString();

			ID = tag.getId().toString();
			byte[] bID = tag.getId();
			int i = 0;
			ID = "";
			while (i < bID.length) {
				Byte ByteObj = bID[i];
				int integre = ByteObj.intValue();
				ID += Integer.toString(integre);
				i++;
				if (i < bID.length)
					ID += ".";

				// if (bID[i] == 1)
				// ID += "1";
				// else
				// ID += "0";
			}
			String tagContent = intent.getStringExtra(Intent.EXTRA_TEXT)
					+ "\n|| Data String :" + intent.getDataString()
					+ "\n|| Action :" + intent.getAction() + "\n|| Extra :"
					+ intent.getExtras().toString() + "\n|| Number Flags :"
					+ Integer.toString(intent.getFlags()) + "\n|| Scheme :"
					+ intent.getScheme() 
					+ "\n|| TechList :" + tag.getTechList().toString()
					+ "\n|| ID :" + ID;
			tagID_ = ID != null ? ID : tagContent;

			tView.setText(tagID_ + "\n" + tagContent);

			if (tagContent == null)
				tagContent = "";
			tagContent_ = tagContent;
			Log.d("Tag", tagContent);
			// Toast.makeText( getApplicationContext(), tagContent, Toast.LENGTH_SHORT).show();

			String stringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
			Toast.makeText(getApplicationContext(),
					" NFC Found : " + tagID_, Toast.LENGTH_SHORT)
					.show();
			/*
			 * byte[]
			 * url=buildUrlBytes(intent.getStringExtra(Intent.EXTRA_TEXT));
			 * NdefRecord record=new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
			 * NdefRecord.RTD_URI, new byte[] {}, url); NdefMessage msg=new
			 * NdefMessage(new NdefRecord[] {record});
			 * 
			 * new WriteTask(this, msg, tag).execute();
			 */
		}
	}

    public void startScanning()
    {
		if (!inWriteMode) {
			IntentFilter discovery = new IntentFilter(
					NfcAdapter.ACTION_TAG_DISCOVERED);
			IntentFilter discovery2 = new IntentFilter(
					NfcAdapter.ACTION_NDEF_DISCOVERED);
			IntentFilter discovery3 = new IntentFilter(
					NfcAdapter.ACTION_TECH_DISCOVERED);
			IntentFilter[] tagFilters = new IntentFilter[] { discovery,
					discovery2, discovery3 };
			// tagFilters = null;
			Intent i = new Intent(this, getClass())
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

			inWriteMode = true;
			adapter.enableForegroundDispatch(this, pi, tagFilters, null);
		}
    }

    public void stopScanning()
    {
		if (isFinishing()) {
			adapter.disableForegroundDispatch(this);
			inWriteMode = false;
		}
    }

    @Override
    public void onResume() {
        super.onResume();
		startScanning();
	}

	@Override
	public void onPause() {
		// on dirait que cela n'arrete pas tout le temps...
		stopScanning();
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					FirstItemListActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
