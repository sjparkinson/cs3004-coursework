package Framework.ObservationResult;

import org.joda.time.DateTime;

import java.util.UUID;

public class Patient
{
    public UUID Id;

    public String Name;

    public DateTime DateOfBirth;

    public PatientGender Gender;

    public Patient()
    {
        this(null, null, null);
    }

    public Patient(String name, DateTime dateOfBirth, PatientGender gender)
    {
        Id = UUID.randomUUID();

        Name = name;
        DateOfBirth = dateOfBirth;
        Gender = gender;
    }
}
