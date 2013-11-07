package Framework; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:20
 */

import Framework.Logger.ReportLogger;
import Framework.ObservationResult.ObservationResult;

/*
Defines the protocol used by both the client and server
in the Report application.
*/
public class ReportProtocol
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static final String Separator = " ";

    // Defines the states that the client and server can be in.
    public enum Command
    {
        None(""),

        Message("MSG"),

        Close("BYE"),

        Error("ERR"),

        Ok("THX");

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
    }

    public static Command ParseCommand(String message) throws Exception
    {
        String command = message.substring(0, 3);

        Log.debug("Received command: %s", command);

        for (Command cmd : Command.values())
        {
            if (cmd.command.equals(command))
            {
                return cmd;
            }
        }

        return Command.None;
    }

    public static String encodeMessage(ObservationResult observationResult)
    {
        return String.format("%s%s", Command.Message, observationResult.toJson());
    }

    public static ObservationResult decodeMessage(String message) throws Exception
    {
        try
        {
            if (ParseCommand(message) != Command.Message)
            {
                throw new Exception("Did not receive a Message command.");
            }
        }
        catch (Exception e)
        {
            Log.severe("Received an invalid command: %s.", message);
        }

        String json = message.substring(Command.Message.toString().length() + Separator.length());

        return ObservationResult.fromJson(json);
    }
}
