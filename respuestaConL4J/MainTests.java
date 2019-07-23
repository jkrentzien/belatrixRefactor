package pe.belatrix.jk;

import java.util.HashMap;
import java.util.Map;

import pe.belatrix.jk.JobLogger.JobLevel;

public class MainTests {

	public static void main(String[] args) {
		
		//CONFIG INVALIDO -EXCEPTION
		JobLogger logger1 = new JobLogger(false,false,false,false,false,false);
		try {
			JobLogger.LogMessage("HOLA1", JobLevel.ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//ESPECIFICAR TIPO DE ERROR A LOGUEAR -EXCEPTION
		JobLogger logger2 = new JobLogger(true,false,false,false,false,false);
		try {
			JobLogger.LogMessage("HOLA2", JobLevel.ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//MENSAJE NULO- NO SE OBSERVA EN CONSOLA
		JobLogger logger3 = new JobLogger(true,false,false,true,false,false);
		try {
			JobLogger.LogMessage("HOLA3", JobLevel.ERROR);
			System.out.println("NULL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//MENSAJE SE OBSERVA EN CONSOLA
		JobLogger logger4 = new JobLogger(false,true,false,true,false,true);
		try {
			JobLogger.LogMessage("HOLA4", JobLevel.ERROR);
			JobLogger.LogMessage("HOLA5", JobLevel.MESSAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//MENSAJE EN BD -> en Consola
		JobLogger logger5 = new JobLogger(false,false,true,true,false,true);
		try {
			JobLogger.LogMessage("HOLA6", JobLevel.ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//MENSAJE EN FILE
		JobLogger logger6 = new JobLogger(true,false,false,false,true,false);
		try {
			JobLogger.LogMessage("HOLA7", JobLevel.WARNING);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		//MENSAJE EN FILE y CONSOLA
		JobLogger logger7 = new JobLogger(true,false,true,false,true,false);
		try {
			JobLogger.LogMessage("HOLA8", JobLevel.WARNING);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}

}
