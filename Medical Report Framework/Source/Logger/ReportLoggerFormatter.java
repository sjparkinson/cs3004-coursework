package Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 18/10/13
 * Time: 16:09
 */

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/*
Log formatter for output to the console.
 */
class ReportLoggerFormatter extends Formatter {

    private static String sourceClassName;

    public ReportLoggerFormatter(String name) {
        sourceClassName = name;
    }

    public String format(LogRecord record) {
        // Get the date and time that the record was made.
        Date recordDate = new Date(record.getMillis());

        // Return a formatted string ready for output.
        return String.format("[%1$tT.%1$tL] %2$s %3$s: %4$s\n", recordDate, sourceClassName, record.getLevel(), record.getMessage());
    }
}
