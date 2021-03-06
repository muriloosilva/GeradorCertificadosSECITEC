package br.com.edu.ifg.formosa.certificado.util;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DataUtil {
	
	public static Date formatDateToSave(String d){
		
		SimpleDateFormat sdf=null;  
		Date da = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz); 
	        
	        sdf =  new SimpleDateFormat("yyyy-MM-dd"); 
	        
	        da = new Date(sdf.parse(d).getTime());
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return da;
		
	}
	
	public static Date pegaDataAtual(){
		SimpleDateFormat sdf=null;  
		Calendar ca = null;
		Date da = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        ca = GregorianCalendar.getInstance(tz);
	        
	        sdf =  new SimpleDateFormat("yyyy-MM-dd"); 
	        
	        da = new Date(sdf.parse(ca.get(GregorianCalendar.YEAR) + "-" + ca.get(GregorianCalendar.MONTH) + "-" + ca.get(GregorianCalendar.DAY_OF_MONTH)).getTime());
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return da;
	}
	
	public static Time pegaHoraAtual(){
		SimpleDateFormat sdf=null;  
		Calendar ca = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        ca = GregorianCalendar.getInstance(tz);
	        
	        sdf =  new SimpleDateFormat("HH:mm:ss"); 
	        
	        ca.setTime(sdf.parse(ca.get(GregorianCalendar.HOUR_OF_DAY) + ":" + ca.get(GregorianCalendar.MINUTE) + ":" + ca.get(GregorianCalendar.SECOND)));
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Time t = new Time(ca.getTime().getTime());
		return t;
	}

	public static Time formatTimeToSave(String ti){
		SimpleDateFormat sdf=null;  
		Calendar ca = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        ca = GregorianCalendar.getInstance(tz);
	        
	        sdf =  new SimpleDateFormat("HH:mm:ss"); 
	        
	        ca.setTime(sdf.parse(ti.toString()));
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Time t = new Time(ca.getTime().getTime());
		return t;
	}
	
	public static String formatTimeShow(Time ti){
		SimpleDateFormat sdf=null;  
		String time = "";
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        
	        
	        sdf =  new SimpleDateFormat("HH:mm"); 
	        
	        time = sdf.format(ti);
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return time;
	}
	
	public static String formatTimeShowMin(Time ti){
		SimpleDateFormat sdf=null;  
		String time = "";
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        
	        
	        sdf =  new SimpleDateFormat("mm"); 
	        
	        time = sdf.format(ti);
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return time;
	}
	
	public static String formatDateShow(Date date){
		SimpleDateFormat sdf=null;  
		String d = "";
		try {
			
			TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
	        TimeZone.setDefault(tz);  
	        
	        sdf =  new SimpleDateFormat("dd/MM/yyyy"); 
	        
	        d =  sdf.format(date);
			 
			//data = new Date(((java.util.Date) fmt.parse(hora)).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	

}
