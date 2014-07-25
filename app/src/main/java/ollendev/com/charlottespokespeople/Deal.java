package ollendev.com.charlottespokespeople;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by ChrisOllenburg on 7/25/14.
 */
@ParseClassName("Deal")
public class Deal extends ParseObject {
    public String getName() {
        return getString("name");
    }

    public void setName(String value) {
        put("name", value);
    }

    public String getDealText() {
        return getString("dealText");
    }

    public void setDealText(String value) {
        put("dealText",value);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("location");
    }

    public void setLocation(ParseGeoPoint value) {
        put("location", value);
    }

    public static ParseQuery<Deal> getQuery() {
        return ParseQuery.getQuery(Deal.class);
    }
}
