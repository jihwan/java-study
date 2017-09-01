package edu.java.io.addressbook.util;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final Logger instance = new Logger();
    
    /** Logger configuration */
    private static final LogLevel CURRENT_LOG_LEVEL = LogLevel.Info;
    private boolean displayable = true;
    private boolean deferred;
    
    /** Only use deferred */
    private List<Log> deferredLogs = new ArrayList<>();
    private long startTime;
    private long endTime;
    
    
    public static Logger getLogger() {
        return instance;
    }
    
    public void startDeferred() {
        this.deferred = true;
        
        deferredLogs = new ArrayList<>(180000);
        startTime = System.currentTimeMillis();
    }
    
    public long logDeferred(boolean displayble) {
        
        this.displayable = displayble;
        
        endTime = System.currentTimeMillis();
        
        for (Log log : deferredLogs) {
            display(log.text, log.level);
        }
        this.displayable = true;
        this.deferred = false;
        
        return endTime - startTime;
    }
    
    public void displayTime(String name) {
        
        long executionTime = endTime - startTime;
        
        String format = "%-30s - time: %dms";
        System.out.println(String.format(format, name, executionTime));
    }
    
    public void displayTime(String name, long contentsSize) {
        
        long executionTime = endTime - startTime;
        long perSecond = contentsSize / executionTime * 1000;
        
        String format = "%-30s - time: %dms, contentsSize: %d bytes, %d KB/s";
        System.out.println(String.format(format, name, executionTime, contentsSize, perSecond / 1024));
    }
    
    
    public void debug(String text) {
        log(text, LogLevel.Debug);
    }
    public void debug(Object obj) {
        log(obj.toString(), LogLevel.Debug);
    }
    
    public void info(String text) {
        log(text, LogLevel.Info);
    }
    public void info(Object obj) {
        log(obj.toString(), LogLevel.Info);
    }
    
    private void log(Object obj, LogLevel level) {
        if (displayable) {
            if (deferred) {
                deferredLogs.add(new Log(obj, level));
            }
            else {
                display(obj, level);
            }
        }
    }
    
    private void display(Object obj, LogLevel level) {
        if (displayable && CURRENT_LOG_LEVEL.isInclude(level)) {
            System.out.println(level.name() + " - " + obj.toString());
        }
    }
    
    enum LogLevel {
        Info(1), Debug(2);
        
        private int priority;
        
        LogLevel(int priority) {
            this.priority = priority;
        }
        private boolean isInclude(LogLevel logLevel) {
            return this.priority >= logLevel.priority;
        }
    }
    
    private class Log {
        private Object text;
        private LogLevel level;
        
        private Log(Object text, LogLevel level) {
            this.text = text;
            this.level = level;
        }
    }
}
