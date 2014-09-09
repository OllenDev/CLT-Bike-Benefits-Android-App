package ollendev.com.charlottespokespeople;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by ChrisOllenburg on 9/8/14.
 */
public class CardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_card, null);
        TextView text = (TextView) rootView.findViewById(R.id.test_tv);
        text.setText("Hello");
        return rootView;
    }
}
