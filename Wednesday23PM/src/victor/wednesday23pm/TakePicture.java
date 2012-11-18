package victor.wednesday23pm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TakePicture extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final Button buttonTakePicture = (Button) findViewById(R.id.ButtonTakePicture);
        buttonTakePicture.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Picture", "Tap");
// http://developer.android.com/training/camera/photobasics.html
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

}
