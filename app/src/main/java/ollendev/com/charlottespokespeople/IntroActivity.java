package ollendev.com.charlottespokespeople;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class IntroActivity extends Activity {
    @InjectView(R.id.dealsButton) Button mDealsButton;
    @InjectView(R.id.stickersButton) Button mStickersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.dealsButton)
    public void goToDeals(Button button) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.stickersButton)
    public void goToStickers(Button button) {
        Intent intent = new Intent(this, PreferredStickerSellerActivity.class);
        startActivity(intent);
    }
}
