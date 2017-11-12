package Agreegator;

import java.io.Serializable;
import java.net.Socket;

public class Device_sock_details implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String device_id;
	public Socket device_sock;
	
	
	public Device_sock_details(String device_id, Socket device_sock) {
		super();
		this.device_id = device_id;
		this.device_sock = device_sock;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public Socket getDevice_sock() {
		return device_sock;
	}
	public void setDevice_sock(Socket device_sock) {
		this.device_sock = device_sock;
	}
}
