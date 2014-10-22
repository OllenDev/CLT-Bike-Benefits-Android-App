package ollendev.com.charlottespokespeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChrisOllenburg on 9/8/14.
 */
public class CardAdapter extends FragmentPagerAdapter {
    private static final String TAG = CardAdapter.class.getSimpleName();
    public List<Deal> mDeals;

    public CardAdapter(FragmentManager fm) {
        super(fm);
        Log.d(TAG, "CardAdapter");
        mDeals = new ArrayList<Deal>();
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem");
        CardFragment fragment = CardFragment.createCardFragment(mDeals.get(i));
        return fragment;
    }

    @Override
    public int getCount() {
        return mDeals.size();
    }
}
