package Server;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 18:36
 */

import Framework.Logger.ReportLogger;
import Framework.ObservationResult.ObservationResult;
import Framework.ReportProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static Framework.ReportProtocol.Command;
import static Framework.ReportProtocol.ParseCommand;

class ReportServerWorker implements Runnable
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private final Socket socket;

    private ServerState _currentState;

    private String _currentMessage;

    private ObservationResult _receivedObservation;

    enum ServerState
    {
        Waiting,

        Receiving,

        Replying,

        Disconnected
    }

    public ReportServerWorker(Socket socket)
    {
        this.socket = socket;

        this._currentState = ServerState.Waiting;
    }

    public void run()
    {
        Log.debug("Connected to %s.", socket.getRemoteSocketAddress());

        processState();
    }

    private void processState()
    {
        Log.debug("Current state: %s.", this._currentState);

        switch (_currentState)
        {
            case Waiting:
                awaitMessage();
                break;

            case Receiving:
                processClientMessage();
                break;

            case Replying:
                reply();
                break;

            case Disconnected:
                disconnect();
                break;
        }
    }

    private void awaitMessage()
    {
        try
        {
            Scanner scanner = new Scanner(socket.getInputStream());

            if (scanner.hasNextLine())
            {
                //process each line in some way
                this._currentMessage = scanner.nextLine();

                Log.debug("Client message received: %s.", this._currentMessage);

                this._currentState = ServerState.Receiving;

                processState();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processClientMessage()
    {
        Log.debug("Processing received message.", socket.getRemoteSocketAddress());

        Command command = null;

        try
        {
            command = ParseCommand(this._currentMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        switch (command)
        {
            case Message:
                Log.info("Message received: %s.", command);
                this._currentState = ServerState.Replying;
                break;

            case Close:
                this._currentState = ServerState.Disconnected;
                break;
        }

        processState();
    }

    private void reply()
    {
        Log.debug("Sending reply to to %s.", socket.getRemoteSocketAddress());

        try
        {
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

            outputWriter.println(ReportProtocol.Command.Ok);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this._currentState = ServerState.Waiting;

        processState();
    }

    private void disconnect()
    {
        Log.debug("Disconnecting from %s.", socket.getRemoteSocketAddress());

        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
