package Framework.ObservationResult;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 05/11/13
 * Time: 15:13
 */

enum Gender
{
    Male,

    Female
}

public class Patient
{
    public UUID Id;

    public String Name;

    public Date DateOfBirth;

    public Gender Gender;

    public Address Address;
}
