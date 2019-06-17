package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dates {
	
	
	/**
	 * Convertir fecha 
	 * @return
	 */
	public static String toString(LocalDate date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter);
	}

	/**
	 * Convertir fecha 
	 * @return
	 */
	public static String toString(LocalDateTime date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd k:m:s");
		return date.format(formatter);
	}
	
	
	/**
	 * Convierte un string de fecha o de fecha y hora a un LocalDate o a LocalDateTime.
	 * @return
	 */
	public static Object fromString(String dateStr)
	{
		if(dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(dateStr, format);
		}
		else if(dateStr.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")) {
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd k:m:s");
			return LocalDateTime.parse(dateStr, format);
		}
		else {
			return LocalDate.now();
		}
	}
	
	
}
