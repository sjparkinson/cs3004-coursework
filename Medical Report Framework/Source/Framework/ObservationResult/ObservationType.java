package Framework.ObservationResult;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 05/11/13
 * Time: 15:13
 */

public enum ObservationType
{
    None(""),

    Weight("Weight"),

    BloodPressure("Blood Pressure"),

    HeartRate("Heart Rate");

    private ObservationType(String command)
    {
        this.observationType = command;
    }

    private final String observationType;

    @Override
    public String toString()
    {
        return this.observationType;
    }

    public static ObservationType fromString(String observationType)
    {
        for (ObservationType observation : ObservationType.values())
        {
            if (observation.toString() == observationType)
            {
                return observation;
            }
        }

        return None;
    }
}
