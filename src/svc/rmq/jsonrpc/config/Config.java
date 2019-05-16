package svc.rmq.jsonrpc.config;

public class Config {
	private static String NAMESERVER;
	private static int PORT;
	
	public static String getNameserver() {
		return NAMESERVER;
	}
	
	public static void setNameserver(String nNameServer) {
		NAMESERVER = nNameServer;
	}
	
	public static int getPort() {
		return PORT;
	}
	
	public static void setPort(int aPort) {
		PORT = aPort;
	}
}
