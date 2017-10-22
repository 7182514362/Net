import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MsgClient implements Runnable{
	private static String serverIP = "localhost";
	private static int serverPort = 1025;
	private static Socket socket = null;

	public MsgClient(String ip, int port) {
		MsgClient.serverIP = ip;
		MsgClient.serverPort = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		read();
	}
	
	public void connect() {
		// TODO Auto-generated method stub
		try {
			socket = new Socket(serverIP, serverPort);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void close() {

		try {
			if (socket != null)
				socket.close();

			socket.shutdownInput();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void send(String msg) {
		OutputStream outputStream = null;
		PrintWriter printWriter = null;
		try {
			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream);

			printWriter.println(msg);
			printWriter.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {

		}

	}

	
	public void read() {

		if (socket == null) {
			System.err.println("socket null");
			return;
		}

		InputStreamReader isReader = null;
		BufferedReader bufferReader = null;
		InputStream iStream = null;
		
		try {
			iStream = socket.getInputStream();
			isReader = new InputStreamReader(iStream);
			bufferReader = new BufferedReader(isReader);
			String msg= bufferReader.readLine();
			while (msg!=null) {
				System.out.println(msg);
				msg= bufferReader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}

	
	public String getServerConfig() {
		
		return serverIP + ":" + serverPort;
	}

	
	public void setServerConfig(String ip, int port) {

		this.serverIP = ip;
		this.serverPort = port;
	}


}
