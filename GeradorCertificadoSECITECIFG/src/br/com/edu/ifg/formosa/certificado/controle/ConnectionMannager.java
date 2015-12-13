package br.com.edu.ifg.formosa.certificado.controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMannager {
	
	public static Connection conn = null;
	
	public static Connection getConnetion(){
		
		if(conn == null){
		
		
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bkp-visecitec-final", "postgres", "ifg");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
		else{
			return conn;
		}
	}


}