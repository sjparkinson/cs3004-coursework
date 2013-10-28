package Framework;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 21/10/13
 * Time: 14:21
 */
public class ReportRecord
{
    public enum ReportRecordType
    {
        Weight
    }

    public ReportRecordType type;

    public float value;

    public String units;
}
