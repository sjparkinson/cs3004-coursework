package Framework; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:20
 */

import Framework.Logger.ReportLogger;
import Framework.ObservationResult.ObservationResult;

import java.io.InvalidObjectException;

/*
Defines the protocol used by both the client and server
in the Report application.
*/
public class ReportProtocol
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static final String Separator = "; ";

    // Defines the states that the client and server can be in.
    public enum Command
    {
        None(""),

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
            return this.command + Separator;
        }

        public static Command fromString(String command)
        {
            for (Command cmd : Command.values())
            {
                if (cmd.toString() == command)
                {
                    return cmd;
                }
            }

            return None;
        }
    }

    public static Command ParseCommand(String command)
    {
        return Command.fromString(command.split(Separator)[0]);
    }

    public static String encodeMessage(ObservationResult observationResult)
    {
        return String.format("%s%s", Command.Message, observationResult.toJson());
    }

    public static ObservationResult decodeMessage(String message) throws InvalidObjectException
    {
        if (ParseCommand(message) != Command.Message)
        {
            throw new InvalidObjectException("Did not receive a Message command.");
        }

        String json = message.substring(Command.Message.toString().length() + Separator.length());

        return ObservationResult.fromJson(json);
    }
}
