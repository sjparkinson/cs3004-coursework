package Client; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:31
 */

import Framework.Logger.ReportLogger;
import Framework.ReportConfig;

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

    public static void main(String[] args)
    {
        Log.info("Client starting.");

        connect();

        try
        {
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

            outputWriter.println("CLOSE");

            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Log.info("Client is stopping.");
    }

    private static void connect()
    {
        try
        {
            socket = new Socket(ReportConfig.Host, ReportConfig.Port);
        }
        catch (UnknownHostException e)
        {
            Log.severe("Could not understand the host %s.", ReportConfig.Host);
            System.exit(1);
        }
        catch (IOException e)
        {
            Log.severe("Could not connect to the server at %s:%s.", ReportConfig.Host, ReportConfig.Port);
            System.exit(1);
        }
    }
}
