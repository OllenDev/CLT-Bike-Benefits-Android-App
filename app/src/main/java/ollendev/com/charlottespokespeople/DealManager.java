package ollendev.com.charlottespokespeople;

import java.util.List;

/**
 * Created by ChrisOllenburg on 10/28/14.
 */
public class DealManager {
    private static DealManager instance;
    private List<Deal> mDeals;

    private DealManager() {

    }

    public static DealManager getInstance() {
        if (instance == null) {
            instance = new DealManager();
        }
        return instance;
    }

    public void setDeals(List<Deal> deals) {
        mDeals = deals;
    }

    public List<Deal> getDeals() {
        return mDeals;
    }
}
