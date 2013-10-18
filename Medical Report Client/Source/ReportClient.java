/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:31
 */

import Logger.ReportLogger;

/*
The client application that will submit medical reports to the server.
 */
public class ReportClient {

    private static final ReportLogger Log = ReportLogger.getLogger(ReportClient.class.getName());

    public static void main(String[] args) {

        Log.info("Client started.");


        Log.info("Client stopping.");

    }
}
