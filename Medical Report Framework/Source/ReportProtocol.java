/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:20
 */

/*
Defines the protocol used by both the client and server
in the Report application.
*/
class ReportProtocol
{
    // Defines the states that the client and server can be in.
    public enum ReportProtocolState
    {
        // Waiting on a connection to begin.
        Waiting,

        // Connection made, waiting for request.
        Listening,

        // Request made, waiting on reply.
        ReportSent,

        // Response received.
        ReportReceived,

        // Connection terminated.
        Closed
    }
}
