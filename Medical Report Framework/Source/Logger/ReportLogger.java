package Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:39
 */

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Log class for use in both the server and the client.
 */
public class ReportLogger {

    private static final Logger LOGGER = Logger.getLogger(ReportLogger.class.getName());

    private static ReportLogger reportLogger;

    // Log handlers
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    // Returns the logger instance to use.
    public static ReportLogger getLogger(String name) {

        if (reportLogger == null) {
            reportLogger = new ReportLogger();

            LOGGER.setUseParentHandlers(false);

            LOGGER.setLevel(Level.ALL);

            consoleHandler.setFormatter(new ReportLoggerFormatter(name));

            LOGGER.addHandler(consoleHandler);
        }

        return reportLogger;
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void severe(String message) {
        log(Level.SEVERE, message);
    }

    private static void log(Level level, String message) {
        if (LOGGER.isLoggable(level)) {
            LOGGER.log(level, message);
        }
    }
}
