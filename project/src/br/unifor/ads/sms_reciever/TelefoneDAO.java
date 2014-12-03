package br.unifor.ads.sms_reciever;

import android.database.sqlite.SQLiteDatabase;

public class TelefoneDAO {
	
	public static final String TAG = "SMS Location";
	public static final String DB_NAME = "UniforADS";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "telefones";
	public static final String ROW_ID = "_id";
	public static final String ROW_NUEMROS = "numero";
	
	private DBHelper helper;
	private SQLiteDatabase database;
	
	

}
