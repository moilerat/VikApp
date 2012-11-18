package victor.wednesday23pm;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.pm.ApplicationInfo;
import java.io.File;

public class FirstItemDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstitem_detail);

        ApplicationInfo info = getApplicationInfo();
        String text = info.dataDir;
        final TextView tView = (TextView) findViewById(R.id.textViewInfo);
        tView.setText(text);

        File f = new File("/"); 
        String[] someFiles = f.list();
        int i = 0;
        while (i < someFiles.length)
        {
            text += someFiles[i] + "\n";
            i++;
        }
        tView.setText(text);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(FirstItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FirstItemDetailFragment.ARG_ITEM_ID));
            FirstItemDetailFragment fragment = new FirstItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.firstitem_detail_container, fragment)
                    .commit();
        }
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
