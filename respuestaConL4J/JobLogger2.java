package pe.belatrix.jk;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger2 {

	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	//private static Map dbParams;
	private static Logger logger = Logger.getLogger("MyLog");
	private static Logger fileLogger = Logger.getLogger("MyFileLogger");
	private static Logger dbLogger = Logger.getLogger("MyDBLogger");
	
	public enum JobLevel {
		MESSAGE (1),
		ERROR(2),
		WARNING (3);
		private final int id;
		
		JobLevel(int id){
			this.id=id;
		}
		
		public int getId(){ return id;}		
	}
	
	public JobLogger2(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam) {
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		//dbParams = dbParamsMap; se configura en el .properties
	}

	public static void LogMessage(String messageText, JobLevel level) throws Exception {
		messageText = messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (level==null)) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		//NO ES NECESARIO
		/*File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}*/		
		String l = null;
		if (level==JobLevel.ERROR && logError) {
			l = "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		} else if (level==JobLevel.WARNING && logWarning) {
			l = "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		} else if (level==JobLevel.MESSAGE && logMessage) {
			l = "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		
		if (l!=null) {
			if(logToFile) {
				//FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "logFile.txt");
				//fileLogger.addHandler(fh);
				fileLogger.log(Level.INFO, l);
			}
			
			if(logToConsole) {
				//ConsoleHandler ch = new ConsoleHandler();
				//logger.addHandler(ch);
				logger.log(Level.INFO, l);
			}
			
			if(logToDatabase) {
				//ESTO SE SETEA EN EL ARCHIVO PROPERTIES
				/*Connection connection = null;
				Properties connectionProps = new Properties();
				connectionProps.put("user", dbParams.get("userName"));
				connectionProps.put("password", dbParams.get("password"));

				connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
						+ ":" + dbParams.get("portNumber") + "/", connectionProps);
				Statement stmt = connection.createStatement();
				stmt.executeUpdate("insert into Log_Values('" + l + "', " + String.valueOf(level.getId()) + ")");*/
				l = "insert into Log_Values('" + l + "', " + String.valueOf(level.getId()) + ")";
				if (level.getId()==1) {
					dbLogger.log(Level.INFO, l);
				} else if (level.getId()==2){
					dbLogger.log(Level.SEVERE, l);
				} else if (level.getId()==3) {
					dbLogger.log(Level.WARNING, l);
				}
			}
		}
	}
	
}
