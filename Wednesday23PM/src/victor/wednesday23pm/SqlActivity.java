package victor.wednesday23pm;

import android.app.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import java.util.List;
import java.util.Vector;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import victor.wednesday23pm.NfcActivity;
import victor.wednesday23pm.LocationActivity;

import android.os.SystemClock;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SqlActivity extends Activity {

    public class SqlRecord
    {
        public long id;
        public double latitude;
        public double longitude;
        public double altitude;
        public double accuracy;
        public double time;
        public double speed;
        public String tag_content;

        public void onCreate(Cursor cursor) {
            id = cursor.getLong(0);
            latitude = cursor.getDouble(1);
            longitude = cursor.getDouble(2);
            altitude = cursor.getDouble(3);
            accuracy = cursor.getDouble(4);
            time = cursor.getDouble(5);
            speed = cursor.getDouble(6);
            tag_content = cursor.getString(6);

            // cursor.getColumnNames().length

		  }
    }

    // http://www.vogella.com/articles/AndroidSQLite/article.html
	public class MySQLiteHelper extends SQLiteOpenHelper {


		public static final String TABLE_LOCATIONS = "locations";
		public static final String TABLE_VERSION = "version";

		public static final String COLUMN_ID = "id";
		public static final String COLUMN_LATITUDE = "latitude";
		public static final String COLUMN_LONGITUDE = "longitude";
		public static final String COLUMN_ALTITUDE = "altitude";
		public static final String COLUMN_ACCURACY = "accuracy";
		public static final String COLUMN_TIME = "time";
		public static final String COLUMN_SPEED = "speed";
		public static final String COLUMN_TAG_CONTENT = "tagContent";

		private static final String DATABASE_NAME = "location.db";
		private static final int DATABASE_VERSION = 1;

		private String allColumns()
		{
			return COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + ", " + COLUMN_ALTITUDE + ", "
			    + COLUMN_ACCURACY + ", " + COLUMN_TIME + ", " + COLUMN_SPEED + ", " + COLUMN_TAG_CONTENT;
		}

		private String creationTableLocation = "create table "
	    + TABLE_LOCATIONS + "(" 
		+ COLUMN_ID + " integer primary key autoincrement"
	    + ", " + COLUMN_LATITUDE+ " double not null"
		+ ", " + COLUMN_LONGITUDE+ " double not null"
		+ ", " + COLUMN_ALTITUDE+ " double not null"
		+ ", " + COLUMN_ACCURACY+ " double not null"
		+ ", " + COLUMN_TIME+ " long not null"
		+ ", " + COLUMN_SPEED+ " double not null"
		+ ", " + COLUMN_TAG_CONTENT+ " string not null"
	    + ");";

		private String creationTableVersion = "create table "
			    + TABLE_VERSION + "(" 
				+ TABLE_VERSION + " integer default " + DATABASE_VERSION
			    + ");";
	    private String insertTableVersion = " INSERT INTO " + TABLE_VERSION + " VALUES (" + DATABASE_VERSION + ")";

		  public MySQLiteHelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  }

		  @Override
		  public void onCreate(SQLiteDatabase database) {
			    database.execSQL(creationTableLocation);
			    database.execSQL(creationTableVersion);
			    database.execSQL(insertTableVersion);
		  }

		  public int getVersion(SQLiteDatabase db) {
			  try
			  {
			  Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_VERSION, new String[] {});
			  int version = 0;
			  cursor.moveToFirst();
			    if (!cursor.isAfterLast())
				  version = cursor.getInt(0);
			return version;
			  }
			  catch (Exception e)
			  {
			      return 0;
			  }
		  }

		  @Override
		  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (getVersion(db) < DATABASE_VERSION)
			{
		        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
		        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSION);
		        onCreate(db);
			}
		  }

		  public void clear(SQLiteDatabase db) {
		    db.execSQL("DELETE FROM  " + TABLE_LOCATIONS );
		    // " + TABLE_LOCATIONS ?? 
		  }

		  public void record(SQLiteDatabase database, double latitude, double longitude, double altitude,
				  double accuracy, double time, double speed, String tagContent) {
			  String request = "INSERT INTO " + TABLE_LOCATIONS + 
			    		"(" + allColumns() + " ) " +
					  "VALUES (" + latitude + ", " + longitude + ", " + altitude + ", " + 
					  accuracy + ", " + time + ", " + speed + ", " + "\"" + tagContent + "\"" + ") ";
		    database.execSQL(request);
		  }

		  public String select(SQLiteDatabase database) {
			    Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_LOCATIONS, new String[] {});
			    String results = cursor.toString();

		  cursor.moveToFirst();
		  double numberResults = cursor.getCount();
		  results = "There are : " + numberResults + " results ";
		  // We want only 
		  double numberResultToDisplay = 10;
		  // je veux la racine 10eme de 40
		  double quo = Math.exp (Math.log(numberResults) / numberResultToDisplay);
		  double numberDisplayed = 0;
		  double numberResultDivided = numberResults;
		  double counting = numberResults;
		    while (!cursor.isAfterLast())
		    {
		    	if (numberResultDivided > counting)
		    	{
    		    	results = results +  "||" + counting + "|" + cursorToStringLocation(cursor);
    		    	numberResultDivided /= quo;
		    	}

		    	counting -= 1.;
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return "Version : " + getVersion(database) + "\n" + results;
		  }

		  private String cursorToStringLocation(Cursor cursor) {
			  String res = " " + Long.toString(cursor.getLong(0));
			  int i = 1;
			  // cursor.getCount() : donne le nombre de donnee...
			  while (i < cursor.getColumnNames().length)
			  {
			      res = res + " " + cursor.getString(i);
			      i++;
			  }
		    return res;
		  }

		} 

	  private static SQLiteDatabase database = null;
	  private static MySQLiteHelper dbHelper = null;
	  private static TextView tView = null;

      public static void record()
      {

  		double time = LocationActivity.time_;

          if (time == 0.)
          {
              time = System.currentTimeMillis();
          }

          if (dbHelper != null)
          {
  		//dbHelper.record(database, 0.66666, NFCActivity.tagContent_);
  		dbHelper.record(database,
  				LocationActivity.latitude_,
  				LocationActivity.longitude_,
  				LocationActivity.altitude_,
  				LocationActivity.accuracy_,
  				time,
  				LocationActivity.speed_,
  				NfcActivity.tagID_);
          }
       //   else
       // 	  Toast.makeText((Context) LocationActivity, "You should initialize Sql", Toast.LENGTH_SHORT).show();
      }

    @Override
    public void onCreate(Bundle savedInstanceState){// throws SQLException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new MySQLiteHelper(this);
        database = dbHelper.getWritableDatabase();
        tView = (TextView) findViewById(R.id.editTextToDisplayDataBase);
        // VR 10-11-12 : On upgrade franchement il faudrait le faire un peu mieux...
        dbHelper.onUpgrade(database,  0, 1);
        // dbHelper.close();

        final Button buttonSql = (Button) findViewById(R.id.ButtonRecordLastLocationSql);
        buttonSql.setOnClickListener(new Button.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Log.d("SQL", "Record");

        		record();

        		String res = dbHelper.select(database);
        		tView.setText(res);

            }
        });

        final Button buttonDisplayContent = (Button) findViewById(R.id.ButtonDisplayContent);
        buttonDisplayContent.setOnClickListener(new Button.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Log.d("SQL", "Display");

        		String res = dbHelper.select(database);
        		tView.setText(res);
        	}
        });

        final Button buttonSqlClear = (Button) findViewById(R.id.ButtonSqlClear);
        buttonSqlClear.setOnClickListener(new Button.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Log.d("SQL", "Clear");

                dbHelper.clear(database);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, FirstItemListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String buildXml()
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder =  documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("catalog");
        rootElement.setAttribute("journal", "Oracle Magazine");
        rootElement.setAttribute("publisher", "Oracle Publishing");
        document.appendChild(rootElement);

        Element articleElement = document.createElement("article");
            rootElement.appendChild(articleElement);

            Element editionElement = document.createElement("edition");
            articleElement.appendChild(editionElement);
            editionElement.
            appendChild(document.createTextNode("Sept-Oct 2005"));

            return rootElement.toString();
        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

}
