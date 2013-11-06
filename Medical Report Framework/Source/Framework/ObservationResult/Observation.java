package Framework.ObservationResult;

import org.joda.time.DateTime;

import java.util.UUID;

public class Observation
{
    public UUID Id;

    public DateTime TimeStamp;

    public ObservationType Type;

    public String Value;

    public String Units;

    public Observation()
    {
        this(null, null, null);
    }

    public Observation(ObservationType type, String value, String units)
    {
        Id = UUID.randomUUID();
        TimeStamp = new DateTime();

        Units = units;
        Type = type;
        Value = value;
    }
}
