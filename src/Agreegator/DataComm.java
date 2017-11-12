package Agreegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;

public class DataComm {
	// private Thread run,DataComm;
	public Socket Server_sock;
	public static List<Device_sock_details> Device_list = new ArrayList<Device_sock_details>();

//	 public BufferedReader br1, br2;
//	 public BufferedWriter bw1, bw2;

	public static List<Device_sock_details> getDevice_list() {
		return Device_list;
	}

	public static void setDevice_list(List<Device_sock_details> device_list) {
		Device_list = device_list;
	}

	public void add_var(String Device_id, Socket Device_sock) {
		Device_list.add(new Device_sock_details(Device_id, Device_sock));
		setDevice_list(Device_list);
		System.out.println("coount: " + Device_list.size());
	}

	public Socket getServer_sock() {
		return Server_sock;
	}

	public void setServer_sock(Socket server_sock) {
		Server_sock = server_sock;
		
/*		 try { 
			 br1=new BufferedReader(new InputStreamReader(Server_sock.getInputStream())); 
			 bw1=new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream())); 
			 } 
		 catch(IOException e) { // TODO Auto-generated catch block
				 e.printStackTrace(); 
		 }*/
}

	public DataComm() {

		try {
			Server_sock = new Socket("192.168.1.4", 8885);
//			br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
//			bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

		/*
		 * Arduino ad = new Arduino(Server_sock); Thread t1 = new Thread(ad);
		 * t1.start();
		 */
		// run = new Thread(this, "running1");
		// run.start();
	}

	// public void run() {
	// TODO Auto-generated method stub
	/*
	 * Arduino ad = new Arduino(Server_sock); Thread t1 = new Thread(ad);
	 * t1.start();
	 */
	// }

	public String send_data(String data) {
//		BufferedReader br1;
//		BufferedWriter bw1;
		String test = null;
		try {
			BufferedReader br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream()));
			bw1.write(data);
			bw1.newLine();
			bw1.flush();
			test = br1.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;

	}

	public void communication(Socket dev_sock, String Dev_ID) {
//		BufferedReader br2;
//		BufferedWriter bw2;

/*		try {
			br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
			bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String temp = null;
		while (true) {
			try {
				// System.out.println(" i am here");
				BufferedReader br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
				BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream())); 
				temp = br1.readLine();
				System.out.println(Dev_ID + "received : " + temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  temp = temp.replace("/devid/","");
			  System.out.println("i am here 3"); 
			  System.out.println(Dev_ID);
			  if(temp.equals(Dev_ID)){ 
				  try { 
					  	BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream())); 
					  	System.out.println("Dev_auth");
					  	bw1.write("1"); 
					 	bw1.newLine(); 
					 	bw1.flush();
					  } 
				  catch (IOException e) 
				  { // TODO Auto-generated catch block e.printStackTrace(); 
					  e.printStackTrace();
				  }
			  //if you want to have different functions for different type...create a check here and accordingly creaate new functions
			  dev_control(Server_sock, dev_sock); 
			  continue; 
			  }		else{ 
			continue;
			  } 
			  }
		}


	public void dev_control(Socket Server_sock, Socket dev_sock) {
//		BufferedReader br2;
//		BufferedWriter bw2;
		BufferedReader br1 = null, br2 = null;
		BufferedWriter bw1 = null, bw2 = null;
		
		try {
			br2 = new BufferedReader(new InputStreamReader(dev_sock.getInputStream()));
			bw2 = new BufferedWriter(new OutputStreamWriter(dev_sock.getOutputStream()));
			br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
			bw1 = new BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		String temp = null;
		try {
//			br1 = new BufferedReader(new InputStreamReader(Server_sock.getInputStream()));
			temp = br1.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (temp.startsWith("/r/")) {
			temp = temp.replace("/r/", "");
			try {
				System.out.println(temp);
				bw2.write(temp);
				bw2.newLine();
				bw2.flush();
				temp = br1.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;

	}

	/*
	 * private void Communication(final Socket Server_sock) { // TODO
	 * Auto-generated method stub final BufferedReader br; final BufferedWriter
	 * bw; DataComm = new Thread("DataComm"){ public void run(){ while(true){
	 * try { br = new BufferedReader(new
	 * InputStreamReader(Server_sock.getInputStream())); bw = new
	 * BufferedWriter(new OutputStreamWriter(Server_sock.getOutputStream())); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } br.readLine();
	 * 
	 * } } }; DataComm.start(); }
	 */
}
