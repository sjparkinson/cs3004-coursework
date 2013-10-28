package Server; /**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:32
 */

import Framework.Logger.ReportLogger;
import Framework.ReportConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
The report server that will receive and log/store messages.
 */
public class ReportServer
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static final ExecutorService Executor = Executors.newFixedThreadPool(4);

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException
    {
        start(ReportConfig.Port);
    }

    private static void start(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            Log.severe("Failed to connect to socket %s.", port);

            e.printStackTrace();
            System.exit(-1);
        }

        Log.info("Server started.");

        waitOnConnection();
    }

    private static void waitOnConnection()
    {
        boolean listening = true;

        while (listening)
        {
            Log.info("Ready for new client connection.");

            Socket connection;

            try
            {
                connection = serverSocket.accept();

                Log.debug("Connecting with %s.", connection.getRemoteSocketAddress());
            }
            catch (IOException e)
            {
                Log.warning("Bad client connection attempt.");
                continue;
            }

            Runnable worker = new ReportServerWorker(connection);

            Log.debug("Creating new worker.");

            Executor.execute(worker);
        }

        shutdown();
    }

    private static void shutdown()
    {
        Log.info("Server shutting down.");

        Executor.shutdown();

        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            Log.severe("Unable to close the socket.");

            e.printStackTrace();
        }

        Log.info("Server has shutdown.");
    }
}
