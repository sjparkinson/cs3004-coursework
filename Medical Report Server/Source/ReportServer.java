/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:32
 */

import Logger.ReportLogger;

/*
The report server that will receive and log/store messages.
 */
public class ReportServer {

    private static final ReportLogger Log = ReportLogger.getLogger(ReportServer.class.getName());

    public static void main(String[] args) {

        Log.info("Server started.");


        Log.info("Server stopping.");

    }
}
