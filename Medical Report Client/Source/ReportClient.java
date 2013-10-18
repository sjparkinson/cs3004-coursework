/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:31
 */

import Logger.ReportLogger;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/*
The client application that will submit medical reports to the server.
 */
public class ReportClient
{
    private static final ReportLogger Log = ReportLogger.getLogger(ReportClient.class.getName());

    private static Socket socket;

    public static void main(String[] args)
    {
        Log.info("Client starting.");

        try
        {
            socket = new Socket(ReportConfig.Host, ReportConfig.Port);

            socket.close();
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: 4444.");
            System.exit(1);
        }

        Log.info("Client stopping.");
    }
}
