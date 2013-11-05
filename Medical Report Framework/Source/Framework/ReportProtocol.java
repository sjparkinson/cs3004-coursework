package Framework; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:20
 */

import Framework.Logger.ReportLogger;
import Framework.ObservationResult.Observation;
import com.google.gson.Gson;

import java.io.InvalidObjectException;

/*
Defines the protocol used by both the client and server
in the Report application.
*/
public class ReportProtocol
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static final String Separator = " # ";

    // Defines the states that the client and server can be in.
    public enum Command
    {
        Message("MESSAGE"),

        Close("CLOSE"),

        Error("ERROR"),

        Ok("OK");

        private Command(String command)
        {
            this.command = command;
        }

        private final String command;

        @Override
        public String toString()
        {
            return this.command;
        }
    }

    public static String encodeMessage(Observation observation)
    {
        return String.format("%s%s%s", Command.Message, Separator, observation.toJson());
    }

    public static Observation decodeMessage(String message) throws InvalidObjectException
    {
        String[] splitMessage = message.split(Separator);

        if (Command.valueOf(splitMessage[0]) != Command.Message)
        {
            throw new InvalidObjectException("Did not receive a Message command.");
        }

        return new Gson().fromJson(splitMessage[1], Observation.class);
    }
}
