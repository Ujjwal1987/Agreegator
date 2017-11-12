package Agreegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Authentication {
	
	public String query;
	public String SQL_URL = "jdbc:mysql://localhost/arduino_device";
	public String SQL_USER = "root";
	public String SQL_PASSWORD = "Ujjwal1234$";
	public Connection DB_CONN = null;
	public Statement stmt = null;
	public Socket Socket_server;
	public BufferedReader br;
	public BufferedWriter bw;
	
	
	public Authentication(String query) {

		this.query = query;
	}
	
	public String authenticate(){
		
		try {
			DB_CONN = (Connection) DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt = (Statement)DB_CONN.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = null;
		try {
			if(rs.next()){
				result = "1";
			}else{
				result = "0";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public String authenticate_level2(DataComm dc, Socket dev_sock){
		/*try {
			Socket_server = new Socket("192.168.1.4", 8885);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
//		try {
//			bw = new BufferedWriter(new OutputStreamWriter(Socket_server.getOutputStream()));
			query = "/auth/" + query;
/*			System.out.println(query);
			bw.write(query);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
/*		try {
			br = new BufferedReader(new InputStreamReader(Socket_server.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String Result = null;
/*		try {
			Result = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		DataComm dc = new DataComm();
		Result = dc.send_data(query);
		query = query.replace("/auth/", "");
		if(Result.equals("1")){
			/*query = "/notif/" + query;
			try {
				bw.write(query);
				bw.newLine();
				bw.flush();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			query = query.replace("/notif/", "");*/
			String []temp = query.split(":");
			
			query = "INSERT INTO ARDUINO_DEVICE (DEVICE_ID, DEVICE_IP_ADDRESS, DEVICE_APP_ID, GROUP_ID, UID) VALUES ('"+temp[0]+"','"+dev_sock.getInetAddress()+"','"+temp[1]+"', '"+temp[2]+"','"+temp[3]+"')";
			try {
				DB_CONN = (Connection) DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASSWORD);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt = (Statement)DB_CONN.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int i = 0;
					
			try {
				i = stmt.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(i==0){
				query = "UPDATE ARDUINO_DEVICE SET DEVICE_IP_ADDRESS = '"+dev_sock.getInetAddress()+"' WHERE DEVICE_ID='"+temp[0]+"'";
				try {
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return Result;
		
	}

}
