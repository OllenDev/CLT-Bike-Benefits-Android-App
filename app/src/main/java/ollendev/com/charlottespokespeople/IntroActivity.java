package ollendev.com.charlottespokespeople;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
