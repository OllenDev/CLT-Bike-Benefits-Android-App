package ollendev.com.charlottespokespeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ChrisOllenburg on 9/8/14.
 */
public class CardAdapter extends FragmentPagerAdapter {
    private List<Deal> mDeals;


    public CardAdapter(FragmentManager fm, List<Deal> deals) {
        super(fm);
        mDeals = deals;
    }

    @Override
    public Fragment getItem(int i) {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return mDeals.size();
    }
}
