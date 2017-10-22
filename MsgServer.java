import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MsgServer implements Runnable {
	private static int port = 1025;
	private static String ip = null;
	private static ServerSocket serverSocket = null;
	
	private static Map<String, Socket> clientList=new HashMap<String, Socket>();

	public MsgServer(int p) {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			
			System.out.println("create server socket failed!");
			e.printStackTrace();
		}
		
		MsgServer.ip = getIP();
		MsgServer.port = p;
	}
	
	public void run() {
		try {
			listen();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	
	public String getIP() {

		return serverSocket.getInetAddress().toString();
	}

	
	public int getPort() {
		
		return port;
	}

	
	public void setPort(int port) {
		
		MsgServer.port = port;
		
	}

	private void listen() throws IOException {
		//synchronized
		Socket socket =null;
		socket = serverSocket.accept();
		
		Socket temp = null;
		if (socket != null) {
			String remoteAddress = socket.getRemoteSocketAddress().toString();
			System.out.println(remoteAddress);
			
			synchronized(this) {
				if (clientList.containsKey(remoteAddress)&& (temp = clientList.get(remoteAddress)) != null) {
					if (!temp.isClosed() && temp.isConnected()) {
						System.out.println("Client already Connected.");
						return;
					}
				}
				clientList.put(remoteAddress, socket);
				System.out.println("client " + remoteAddress.replace('/', ' ') + " connected");
				
			}
			
			read(socket);//read input
		}
	}
	

	private void read(Socket socket) throws IOException {
		if (socket == null) {
			System.err.println("socket null");
			return;
		}

		InputStreamReader isReader = null;
		BufferedReader bufferReader = null;
		InputStream iStream = null;

		iStream = socket.getInputStream();
		isReader = new InputStreamReader(iStream);
		bufferReader = new BufferedReader(isReader);
		String msg = bufferReader.readLine();

		while (msg != null && !msg.equals("close")) {
			System.out.println("client > " + msg);
			
			sendToAll(socket,msg);
			
			msg = bufferReader.readLine();
		}
		
		//bufferReader.close();
		disconnect(socket);

	}

	private void send(Socket socket, String msg) throws IOException {

		if (socket == null) {
			System.err.println("socket null");
			return;
		}

		PrintWriter printWriter = null;

		printWriter = new PrintWriter(socket.getOutputStream());

		printWriter.println(msg);
		printWriter.flush();
	}
	
	private synchronized void sendToAll(Socket from,String msg) throws IOException {
		Set<String> set=clientList.keySet();
		for(Iterator<String> iterator=set.iterator();iterator.hasNext();) {
			String key=iterator.next();
			if(key.equals(from.getRemoteSocketAddress().toString())) {
				continue;
			}
			Socket temp=clientList.get(key);
			if(!temp.isClosed() && temp.isConnected()) {
				String newMsg=from.getRemoteSocketAddress().toString().replace('/', ' ')+" > "+msg;
				send(temp, newMsg);
			}
		}
	}
	private void disconnect(Socket socket) throws IOException {
		if (socket != null) {
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
	private void close(Socket socket) throws IOException {
			if (socket != null) {
				socket.close();
				socket = null;
			}
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
	}

}
