package Agreegator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main implements Runnable{
	
	public static void main(String[] args){
		
		Arduino ad = new Arduino();
		Thread t1 = new Thread(ad);
		t1.start();
		/*DataComm dc = new DataComm();
		Thread t2 = new Thread(dc);
		t2.start();*/
		
/*		Socket Server_sock = new Socket();
		try {
			
			Server_sock.connect(new InetSocketAddress("192.168.1.4", 8885));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataComm dc = new DataComm();
		dc.setServer_sock(Server_sock);*/

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
