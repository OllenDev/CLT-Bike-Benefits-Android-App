package ollendev.com.charlottespokespeople;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

/**
 * Created by ChrisOllenburg on 7/23/14.
 */
public class CSPBBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Deal.class);
        Parse.initialize(this, "aF9WB2Z4lAVJEjcUF0m4IbAdLN8W84jANin5aic5", "KgUHTOufhu5cColQrcye2phfVTdOGqD9QaCqlwQB");
    }
}
