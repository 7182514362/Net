import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MsgServer implements IServer {
	private int port = 1025;
	private String ip = null;
	private ServerSocket serverSocket = null;
	private Socket socket = null;

	public MsgServer(int p) {

		this.ip = getIP();
		this.port = p;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("create server socket failed!");
			e.printStackTrace();
		}

	}

	@Override
	public String getIP() {

		InetAddress inetAddr = null;
		try {
			inetAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("unknown host!");
			e.printStackTrace();
		}
		if (inetAddr != null)
			ip = inetAddr.getHostAddress();
		else
			System.out.println("Server: get ip failed!");

		return ip;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub
		this.port = port;
	}

	@Override
	public void listen() {
		// TODO Auto-generated method stub
		try {
			if (socket == null) {
				socket = serverSocket.accept();
				System.out.println("client connected");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
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
			String msg=bufferReader.readLine();
			
			while (msg!=null && !msg.equals("kill")) {
				System.out.println("client > "+msg);
				msg=bufferReader.readLine();
			}
			bufferReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	@Override
	public void send(String msg) {

		if (socket == null) {
			System.err.println("socket null");
			return;
		}

		PrintWriter printWriter = null;
		
		try {
			printWriter = new PrintWriter(socket.getOutputStream());

			printWriter.println(msg);
			printWriter.flush();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {

		}

	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub

		try {
			close();
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		try {
			if (socket != null) {
				socket.close();
				socket = null;
			}
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}

			socket.shutdownInput();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
