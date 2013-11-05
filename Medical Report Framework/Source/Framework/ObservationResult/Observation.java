package Framework.ObservationResult;

import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 05/11/13
 * Time: 15:13
 */

enum ObservationType
{
    Weight,

    BloodPressure,

    HeartRate
}

public class Observation
{
    public UUID Id;

    public Date ObservationTimestamp;

    public ObservationType Type;

    public String Value;

    public String Units;

    public static Observation fromJson(String json)
    {
        return new Gson().fromJson(json, Observation.class);
    }

    public String toJson()
    {
        return new Gson().toJson(this, Observation.class);
    }
}
