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

import static Framework.ReportProtocol.Command;
import static Framework.ReportProtocol.ParseCommand;

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
        Log.debug("Connected to %s.", socket.getRemoteSocketAddress());

        try
        {
            Scanner scanner = new Scanner(socket.getInputStream());

            while (scanner.hasNextLine())
            {
                //process each line in some way
                String clientMessage = scanner.nextLine();

                Log.debug("Client message received: %s.", clientMessage);

                this.processClientMessage(clientMessage);
            }

            // Connection has ended...
            socket.close();

            Log.debug("Closed connection with %s.", socket.getRemoteSocketAddress());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void processClientMessage(String clientMessage) throws IOException
    {
        Command command = ParseCommand(clientMessage);

        switch (command)
        {
            case Message:
                Log.info("Message received: %s.", command);

                // try and convert message, if success send ok, else error.

                break;
            case Close:
                socket.close();
                break;
        }
    }
}
