package br.unifor.ads.sms_reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceptorSMS extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		// SmsMessage[] msgs = null;
		String str = "SMS de ";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			 
			 SmsMessage[] msgs = new SmsMessage[pdus.length];
			 
			 for (int i = 0; i < msgs.length; i++) { 
				 msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); 
			 }
			 
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0]);
			Toast.makeText(context, "SMS recebido pelo Receiver", Toast.LENGTH_LONG).show();
			TelefoneDAO telefone = new TelefoneDAO(context);
			String from = sms.getOriginatingAddress();
			
			if (from.contains(telefone.getTelefones().toString())) {
				String message = sms.getMessageBody().toString();
				String smsString = "Enviado por: " + from + "\n" + message;

				Intent smsintent = new Intent(context, RecebeSMSActivity.class);
				smsintent.putExtra("SMSs", smsString);
				smsintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(smsintent);
			}
		}
	}
}
		
	}

}
