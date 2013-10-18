

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 18:36
 */

import Logger.ReportLogger;

import java.net.Socket;
import java.util.logging.Level;

public class ReportServerWorker implements Runnable
{
    private static final ReportLogger Log = ReportLogger.getLogger(ReportServerWorker.class.getName());

    private Socket socket;

    public ReportServerWorker(Socket socket)
    {
        this.socket = socket;

        Log.debug("ReportServerThread constructor called.");
    }

    public void run()
    {
        Log.log(Level.INFO, "Connected to %s.", socket.getRemoteSocketAddress());
    }
}
