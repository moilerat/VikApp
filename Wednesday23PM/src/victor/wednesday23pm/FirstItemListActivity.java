package victor.wednesday23pm;

import android.content.Intent;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
/*
import android.net.http;

public class CallHttp
{
    public string server_;
}
*/

import victor.wednesday23pm.GetWebPage;

public class FirstItemListActivity extends FragmentActivity
        implements FirstItemListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstitem_list);

        if (findViewById(R.id.firstitem_detail_container) != null) {
            mTwoPane = true;
            ((FirstItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.firstitem_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(FirstItemDetailFragment.ARG_ITEM_ID, id);
            FirstItemDetailFragment fragment = new FirstItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.firstitem_detail_container, fragment)
                    .commit();

        } else {
        	if (id == "1")
        	{
                Intent detailIntent = new Intent(this, FirstItemDetailActivity.class);
                //detailIntent.putExtra(FirstItemDetailFragment.ARG_ITEM_ID, id);
                startActivity(detailIntent);
        	}
        	else if (id == "4")
        	{
//                Intent detailIntent = new Intent(this, FirstItemDetailActivity.class);
                Intent detailIntent = new Intent(this, GetWebPage.class);
          //      detailIntent.putExtra(FirstItemDetailFragment.ARG_ITEM_ID, id);
                startActivity(detailIntent);
        	}
        	else if (id == "5")
        	{
                Intent detailIntent = new Intent(this, LocationActivity.class);
                startActivity(detailIntent);
        	}
        	else if (id == "6")
        	{
                Intent detailIntent = new Intent(this, SqlActivity.class);
                startActivity(detailIntent);
        	}
        	else if (id == "7")
        	{
                Intent detailIntent = new Intent(this, NfcActivity.class);
                startActivity(detailIntent);
        	}
        	else if (id == "8")
        	{
                Intent detailIntent = new Intent(this, TakePicture.class);
                startActivity(detailIntent);
        	}
        	else if (id == "9")
        	{
                Intent detailIntent = new Intent(this, MyMapActivity.class);
                startActivity(detailIntent);
        	}
        	else
        	{
                Intent detailIntent = new Intent(this, FirstItemDetailActivity.class);
                detailIntent.putExtra(FirstItemDetailFragment.ARG_ITEM_ID, id);
                startActivity(detailIntent);
        	}
        }
    }
}
