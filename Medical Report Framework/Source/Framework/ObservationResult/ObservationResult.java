package Framework.ObservationResult;

import Framework.DateTimeTypeConverter;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 05/11/13
 * Time: 15:13
 */
public class ObservationResult
{
    public UUID Id;

    public Patient Patient;

    public Observation Observation;

    public ObservationResult()
    {
        this(null, null);
    }

    public ObservationResult(Patient patient, Observation observation)
    {
        Id = UUID.randomUUID();

        Patient = patient;
        Observation = observation;
    }

    public static ObservationResult fromJson(String json)
    {
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());

        return gson.create().fromJson(json, ObservationResult.class);
    }

    public String toJson()
    {
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());

        return gson.create().toJson(this, ObservationResult.class);
    }
}
