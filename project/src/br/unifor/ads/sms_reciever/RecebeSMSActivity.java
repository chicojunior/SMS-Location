package br.unifor.ads.sms_reciever;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RecebeSMSActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recebe_sms);

		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			finish();
			return;
		}

		String sms = bundle.getString("SMSs");

		TextView txtSMS = (TextView) findViewById(R.id.txtSMS);
		txtSMS.setText(sms);
	}
}
