//test
package Agreegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Arduino implements Runnable{
	
	private Thread Arduino,run;
	ServerSocket ad;
	String Arduino_IP_address, Arduino_App_ID, Arduino_ID, Group_ID, UID;
//	public DataComm dc; 
	
	
	public Arduino() {
		try {
			ad = new ServerSocket(9000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dc = new DataComm();
//		Socket_server = Server_sock;
		run = new Thread(this, "running");
		run.start();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket Ad_sock = null;
		while(true){
		try {
			Ad_sock = ad.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Arduino_function(Ad_sock);
		}
	}


	private void Arduino_function(final Socket ad_sock) {
		// TODO Auto-generated method stub
		Arduino = new Thread("Arduino"){
			public void run(){
				while(true){
					DataComm dc = new DataComm();
					String Arduino_info = null;
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(ad_sock.getInputStream()));
						Arduino_info = br.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Arduino_info);
					InetAddress ia = ad_sock.getInetAddress();
					Arduino_IP_address = String.valueOf(ia);
					Arduino_IP_address = Arduino_IP_address.replace("/", "");
					String []temp = Arduino_info.split(":");
					Arduino_ID = temp[0];
					Arduino_App_ID = temp[1];
					Group_ID = temp[2];
					UID = temp[3];
					
					String query = "SELECT * from ARDUINO_DEVICE WHERE (DEVICE_ID = '"+Arduino_ID+"' and GROUP_ID = '"+Group_ID+"' and UID = '"+UID+"')";
					Authentication auth_level1 = new Authentication(query);
					String result = auth_level1.authenticate();
/*					Socket Socket_server = null;
					try {
						Socket_server = new Socket("192.168.1.4", 8885);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					if(result.equals("1")){
						System.out.println("Authenticated");
						BufferedWriter bw;
//						try {
							String data = "/notif/"+temp[0]+":"+temp[2];
//							DataComm dc = new DataComm();
							String test = dc.send_data(data);
/*							bw = new BufferedWriter(new OutputStreamWriter(Socket_server.getOutputStream()));
							bw.write(data);
							bw.newLine();
							bw.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						Online on = new Online(Arduino_IP_address,ad_sock, temp[0]);
						Thread online = new Thread(on);
						online.start();
						
						dc.add_var(temp[0], ad_sock);
						dc.communication(ad_sock,temp[0]);
						return;
					}
					else
					{
						query = Arduino_ID + ":" + Arduino_App_ID + ":" + Group_ID + ":" + UID;
						Authentication auth_level2 = new Authentication(query);
						String result_final = auth_level2.authenticate_level2(dc, ad_sock);
						System.out.println(result_final);
						if(result_final.equals("1")){
						Online on = new Online(Arduino_IP_address, ad_sock, temp[0]);
						Thread online = new Thread(on);
						online.start();
//						DataComm dc = new DataComm();
						dc.add_var(temp[0], ad_sock);
						dc.communication(ad_sock, temp[0]);
						Thread.currentThread().interrupt();
						return;
						}
						else{
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		};
		Arduino.start();
		
	}
	
	

}
