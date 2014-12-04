package br.unifor.ads.sms_reciever;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TelefoneDAO {

	public static final String TAG = "SMS Location";
	public static final String DB_NAME = "UniforADS";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "telefones";
	public static final String ROW_ID = "_id";
	public static final String ROW_NUMERO = "numero";

	private DBHelper helper;
	private SQLiteDatabase database;

	public TelefoneDAO(Context context) {
		helper = new DBHelper(context);
	}

	private void openDB() {
		database = helper.getWritableDatabase();
	}

	private void closeDB() {
		database.close();
	}

	public long addTelefone(Telefone telefone) {
		long id = -1;

		try {
			openDB();

			ContentValues values = new ContentValues();
			values.put(ROW_NUMERO, telefone.numero);
			id = database.insert(TABLE, null, values);

			closeDB();
		} catch (Exception e) {
			Log.e(TAG, "Erro ao salvar o número no banco de dados. " + e.getMessage());
		}

		return id;
	}

	public boolean deleteTelefone(Telefone telefone) {
		boolean res = false;
		if (telefone == null || telefone.id <= 0) {
			return res;
		}

		try {
			openDB();
			String where = ROW_ID + " = " + telefone.id + ";";
			int count = database.delete(TABLE, where, null);
			res = count > 0;

			closeDB();
		} catch (Exception e) {
			Log.e(TAG,
					"Erro ao deletar o número do telefone. " + e.getMessage());
		}

		return res;
	}

	public boolean updateTelefone(Telefone telefone) {
		boolean res = false;
		if (telefone == null || telefone.id <= 0) {
			return res;
		}

		try {
			openDB();

			String where = ROW_ID + " = " + telefone.id + ";";
			ContentValues values = new ContentValues();
			values.put(ROW_ID, telefone.id);
			values.put(ROW_NUMERO, telefone.numero);
			int count = database.update(TABLE, values, where, null);
			res = count > 0;

			closeDB();
		} catch (Exception e) {
			Log.e(TAG,"Erro ao atualizar o número do telefone. " + e.getMessage());
		}

		return res;
	}

	public ArrayList<Telefone> getTelefones() {
		ArrayList<Telefone> telefones = new ArrayList<Telefone>();

		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(TABLE, new String[] { ROW_ID,
					ROW_NUMERO }, null, null, null, null, ROW_NUMERO);

			if (cursor.moveToFirst()) {
				int index_id = cursor.getColumnIndex(ROW_ID);
				int index_numero = cursor.getColumnIndex(ROW_NUMERO);

				do {
					Telefone telefone = new Telefone();
					telefone.id = cursor.getLong(index_id);
					telefone.numero = cursor.getString(index_numero);

					telefones.add(telefone);
				} while (cursor.moveToNext());
			}

			closeDB();

		} catch (Exception e) {
			Log.e(TAG,
					"Erro ao recuperar números no banco de dados. " + e.getMessage());
		}

		return telefones;
	}
	
	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			try {
				database.execSQL("CREATE TABLE " + TABLE + " (" + ROW_ID
						+ " integer primary key autoincrement, " + ROW_NUMERO
						+ " text not null);");
			} catch (Exception e) {

			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL("DROP TABLE IF EXISTS " + TABLE);
			onCreate(database);
		}
	}
}
