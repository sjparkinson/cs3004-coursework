package Server;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 18:36
 */

import Framework.Logger.ReportLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ReportServerWorker implements Runnable
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private final Socket socket;

    public ReportServerWorker(Socket socket)
    {
        this.socket = socket;

        Log.debug("ReportServerWorker constructor called.");
    }

    public void run()
    {
        Log.info("Connected to %s.", socket.getRemoteSocketAddress());

        try
        {
            Scanner scanner = new Scanner(socket.getInputStream());

            while (scanner.hasNextLine())
            {
                //process each line in some way
                String command = scanner.nextLine();

                Log.info("Message received: %s.", command);

                socket.close();

                Log.info("Close connection with %s.", socket.getRemoteSocketAddress());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
