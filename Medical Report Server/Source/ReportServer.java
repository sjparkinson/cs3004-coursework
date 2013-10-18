/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:32
 */

import Logger.ReportLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
The report server that will receive and log/store messages.
 */
public class ReportServer
{
    private static final ReportLogger Log = ReportLogger.getLogger(ReportServer.class.getName());

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private static ServerSocket serverSocket = null;

    public static void main(String[] args)
    {
        start();
    }

    private static void start()
    {
        try
        {
            serverSocket = new ServerSocket(50505);
        }
        catch (IOException e)
        {
            Log.severe("Failed to connect to socket.");

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
            Socket connection;

            try
            {
                connection = serverSocket.accept();
            }
            catch (IOException e)
            {
                Log.warning("Bad client connection attempt.");
                continue;
            }

            Runnable worker = new ReportServerWorker(connection);

            Log.info("Creating new worker.");

            executor.execute(worker);
        }

        shutdown();
    }

    private static void shutdown()
    {
        Log.info("Server shutting down.");

        executor.shutdown();

        try
        {
            executor.awaitTermination(30, TimeUnit.SECONDS);

            executor.shutdownNow();

            serverSocket.close();
        }
        catch (InterruptedException e)
        {
            Log.severe("Could not await termination.");

            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.severe("Unable to close the socket.");

            e.printStackTrace();
        }

        Log.info("Server has shutdown.");
    }
}
