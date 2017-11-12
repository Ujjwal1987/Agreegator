package Agreegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Online extends Thread{
	
	private String IP_address, Device_id;
	Socket sock, ad_sock;
	Thread run, Online;

	public Online(String iP_address,Socket Ad_sock, String dev_id) {
		super();
		IP_address = iP_address;
//		sock = sock1;
		ad_sock = Ad_sock;
		Device_id = dev_id;
		run = new Thread(this, "Online");
		run.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ping();
	}
	
	public void ping(){
		Online = new Thread("online1"){
			public void run(){
				InetAddress ip = null;
				try {
					ip = InetAddress.getByName(IP_address);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(true){
					try {
						Thread.currentThread().sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if(ip.isReachable(5000)){
							continue;
						}
						
						else{
	//						BufferedWriter bw;
							//try {
								String data = "/offline/"+ Device_id;
								DataComm dc = new DataComm();
								String test = dc.send_data(data);
								ad_sock.close();
							/*	System.out.println(data);
								bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
								bw.write(data);
								bw.newLine();
								bw.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							Thread.currentThread().interrupt();
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}; Online.start();	
}

}
