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

	OutputStream outputStream = null;
	PrintWriter printWriter = null;

	InputStreamReader isReader = null;
	BufferedReader bufferReader = null;
	InputStream iStream = null;

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
	public synchronized void listen() {
		// TODO Auto-generated method stub
		try {
			if (socket == null) {
				socket = serverSocket.accept();
				System.out.println("MsgServer listen at " + getIP() + ":" + getPort());
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

		try {
			if (iStream == null)
				iStream = socket.getInputStream();
			if (isReader == null)
				isReader = new InputStreamReader(iStream);
			if (bufferReader == null)
				bufferReader = new BufferedReader(isReader);
			String msg;
			while ((msg = bufferReader.readLine()) != null) {
				System.out.println(msg);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
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

		try {
			if (outputStream == null)
				outputStream = socket.getOutputStream();
			if (printWriter == null)
				printWriter = new PrintWriter(outputStream);

			printWriter.write(msg);
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
			// TODO Auto-generated catch block
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

			if (isReader != null)
				isReader.close();
			if (iStream != null)
				iStream.close();
			if (bufferReader != null)
				bufferReader.close();

			if (outputStream != null)
				outputStream.close();
			if (printWriter != null)
				printWriter.close();

			socket.shutdownInput();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
