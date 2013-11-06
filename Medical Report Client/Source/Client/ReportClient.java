package Client; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:31
 */

import Framework.Logger.ReportLogger;
import Framework.ObservationResult.ObservationResult;
import Framework.ReportConfig;
import Framework.ReportProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
The client application that will submit medical reports to the server.
 */
public class ReportClient
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static Socket socket = null;

    public static void sendObservation(ObservationResult observationResult)
    {
        if (connect() == true)
        {
            try
            {
                PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

                outputWriter.println(ReportProtocol.encodeMessage(observationResult));

                outputWriter.println(ReportProtocol.Command.Close);

                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            Log.info("Disconnected from the server.");
        }
    }

    private static boolean connect()
    {
        Log.debug("Connecting to the server.");

        try
        {
            socket = new Socket(ReportConfig.Host, ReportConfig.Port);

            return true;
        }
        catch (UnknownHostException e)
        {
            Log.severe("Could not understand the host %s.", ReportConfig.Host);
        }
        catch (IOException e)
        {
            Log.severe("Could not connect to the server at %s:%s.", ReportConfig.Host, ReportConfig.Port);
        }

        return false;
    }
}
