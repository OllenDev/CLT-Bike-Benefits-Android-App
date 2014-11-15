package ollendev.com.charlottespokespeople;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Chris Ollenburg on 9/8/14.
 *
 */
public class CardFragment extends Fragment {
    private static final String TAG = CardFragment.class.getSimpleName();
    private static final String DEAL_KEY = "DEAL_KEY";
    public Deal mDeal;
    @InjectView(R.id.dealText) TextView dealText;
    @InjectView(R.id.companyName) TextView companyName;

    public static CardFragment createCardFragment(Deal deal) {
        Log.d(TAG, "CardFragment");
        Bundle bundle = new Bundle();
        bundle.putSerializable(DEAL_KEY, deal);
        CardFragment frag = new CardFragment();
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.detail_card, container, false);
        ButterKnife.inject(this, rootView);

        Bundle bundle = getArguments();
        mDeal = (Deal) bundle.getSerializable(DEAL_KEY);

        dealText.setText(mDeal.getDealText());
        companyName.setText(mDeal.getName());

        return rootView;
    }

    @OnClick(R.id.websiteButton)
    public void goToWebSite(ImageButton button) {
        Toast.makeText(button.getContext(),"Website", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.navigationButton)
    public void navigateToDeal(ImageButton button) {
        Toast.makeText(button.getContext(),"Navigation", Toast.LENGTH_SHORT).show();
    }
}
