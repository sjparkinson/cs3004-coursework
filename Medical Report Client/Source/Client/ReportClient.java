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
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
The client application that will submit medical reports to the server.
 */
public class ReportClient implements Runnable
{
    private static final ReportLogger Log = ReportLogger.getLogger();

    private static Socket socket = null;


    private ClientState _currentState;

    private ObservationResult _observationResult;

    private int _attempts = 0;

    enum ClientState
    {
        Disconnected,

        Connected,

        Transmitting,

        AwaitingResponse,

        Completed
    }

    public ReportClient(ObservationResult observationResult)
    {
        this._observationResult = observationResult;

        this._currentState = ClientState.Disconnected;
    }

    public void run()
    {
        processState();
    }

    private void processState()
    {
        switch (this._currentState)
        {
            case Disconnected:
                connect();
                break;

            case Connected:
                sendObservation();
                break;

            case AwaitingResponse:
                awaitResponse();
                break;

            case Completed:
                disconnect();
                break;
        }
    }

    private void connect()
    {
        Log.debug("Connecting to the server.");

        try
        {
            socket = new Socket(ReportConfig.Host, ReportConfig.Port);

            socket.setSoTimeout(30000);

            this._currentState = ClientState.Connected;
        }
        catch (UnknownHostException e)
        {
            Log.severe("Could not understand the host %s.", ReportConfig.Host);
        }
        catch (IOException e)
        {
            Log.severe("Could not connect to the server at %s:%s.", ReportConfig.Host, ReportConfig.Port);
        }

        processState();
    }

    private void disconnect()
    {
        try
        {
            socket.close();
        }
        catch (Exception e)
        {
            Log.severe("An error occurred: %s.", e.getMessage());
        }
    }

    private void sendObservation()
    {
        this._currentState = ClientState.Transmitting;

        try
        {
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

            outputWriter.println(ReportProtocol.encodeMessage(this._observationResult));

            Log.debug("Sent observation, awaiting reply.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this._currentState = ClientState.AwaitingResponse;

        processState();
    }

    private void awaitResponse()
    {
        DateTime timeout = DateTime.now().plusSeconds(30);

        try
        {
            BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine = response.readLine();

            ReportProtocol.Command command = ReportProtocol.ParseCommand(inputLine);

            if (command == ReportProtocol.Command.Ok)
            {
                Log.info("Observation sent successfully.");
            }
            else if (command == ReportProtocol.Command.Error)
            {
                Log.severe("The server returned an error: %s", inputLine.substring(4));
            }

            this._currentState = ClientState.Completed;

            processState();
        }
        catch (Exception e)
        {
            Log.severe("An error occurred: %s.", e.getMessage());

            _attempts += 1;

            disconnect();

            if (_attempts < ReportConfig.TransmissionAttempts)
            {
                Log.info("Attempting to resend the observation.");

                this._currentState = ClientState.Disconnected;
            }
            else
            {
                Log.severe("Reached max transmission attempts, observation failed to send successfully.");

                this._currentState = ClientState.Completed;
            }

            processState();
        }
    }
}
