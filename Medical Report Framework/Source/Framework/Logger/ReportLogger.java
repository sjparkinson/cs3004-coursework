package Framework.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 14:39
 */

import Framework.ReportConfig;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Log class for use in both the server and the client.
 */
public class ReportLogger
{
    // The singleton object to return.
    private static ReportLogger reportLogger;

    // The logger instance to use.
    private static final Logger LOGGER = Logger.getLogger(ReportLogger.class.getName());

    // Returns the logger instance to use.
    public static ReportLogger getLogger()
    {

        if (reportLogger == null)
        {
            reportLogger = new ReportLogger();

            // Remove the default ConsoleHandler so we can add our own.
            LOGGER.setUseParentHandlers(false);

            LOGGER.setLevel(ReportConfig.LogLevel);

            Handler consoleHandler = new ConsoleHandler();

            consoleHandler.setLevel(ReportConfig.LogLevel);

            consoleHandler.setFormatter(new ReportLoggerFormatter());

            LOGGER.addHandler(consoleHandler);
        }

        return reportLogger;
    }

    public void debug(String message, Object... args)
    {
        log(Level.FINE, message, args);
    }

    public void info(String message, Object... args)
    {
        log(Level.INFO, message, args);
    }

    public void warning(String message, Object... args)
    {
        log(Level.WARNING, message, args);
    }

    public void severe(String message, Object... args)
    {
        log(Level.SEVERE, message, args);
    }

    private void log(Level level, String format, Object... args)
    {
        log(level, String.format(format, args));
    }

    private static void log(Level level, String message)
    {
        if (LOGGER.isLoggable(level))
        {
            LOGGER.log(level, message);
        }
    }
}
