import java.io.IOException;

public interface IServer {
	public String getIP();
	
	public int getPort();
	public void setPort(int port);
	
	public void restart();
	
	public void listen() throws IOException;
	public void close();
	public void send(String msg);
}
