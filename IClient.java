
public interface IClient {
	public String getServerConfig();
	public void setServerConfig(String ip,int port);
	
	
	public void connect();
	public void restart();
	
	public void close();
	public void send(String msg);
	public void read();
}
