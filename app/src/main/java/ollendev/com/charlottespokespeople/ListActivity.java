package ollendev.com.charlottespokespeople;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ListActivity extends Activity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.dealsList) ListView mListView;
    DealsAdapter mDealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(this);

        List<Deal> deals = DealManager.getInstance().getDeals();
        mDealsAdapter = new DealsAdapter(deals);

        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mDealsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = getApplicationContext();
        CharSequence text = "Not yet implemented";
        int duration = Toast.LENGTH_SHORT;

        Toast.makeText(context, text, duration).show();
    }

    private class DealsAdapter extends BaseAdapter {
        private List<Deal> mDeals;

        public DealsAdapter(List<Deal> deals) {
            if (deals != null) {
                mDeals = deals;
            } else {
                mDeals = new ArrayList<Deal>();
            }
        }

        @Override
        public int getCount() {
            return mDeals.size();
        }

        @Override
        public Object getItem(int position) {
            return mDeals.get(position);
        }

        @Override
        public long getItemId(int position) {
            Deal deal = mDeals.get(position);
            return deal.getName().hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Deal deal = mDeals.get(position);
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder)convertView.getTag();
            } else {
                LayoutInflater inflater = ListActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.deal_list_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            holder.title.setText(deal.getName());
            holder.subtitle.setText(deal.getDealText());

            return convertView;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.deal_list_item_title) TextView title;
        @InjectView(R.id.deal_list_item_subtitle) TextView subtitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
