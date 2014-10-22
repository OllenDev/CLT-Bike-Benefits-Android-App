package ollendev.com.charlottespokespeople;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by Chris Ollenburg on 9/8/14.
 */
public class CardFragment extends Fragment {
    private static final String TAG = CardFragment.class.getSimpleName();
    private static final String DEAL_KEY = "DEAL_KEY";
    public Deal mDeal;

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
        Bundle bundle = getArguments();
        mDeal = (Deal) bundle.getSerializable(DEAL_KEY);
        View rootView = inflater.inflate(R.layout.detail_card, null);
        CardView cardView = (CardView) rootView.findViewById(R.id.card_view);
        Context context = cardView.getContext();
        Card card = new Card(context);
        card.setTitle(mDeal.getDealText());
        CardHeader header = new CardHeader(context);
        card.addCardHeader(header);
        header.setTitle(mDeal.getName());
        cardView.setCard(card);
        return rootView;
    }
}
