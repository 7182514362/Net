import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MsgClient implements IClient {
	private String serverIP = "localhost";
	private int serverPort = 1025;
	private Socket socket = null;

	InputStreamReader isReader = null;
	BufferedReader bufferReader = null;
	InputStream iStream = null;

	OutputStream outputStream = null;
	PrintWriter printWriter = null;

	public MsgClient(String ip, int port) {
		this.serverIP = ip;
		this.serverPort = port;
	}

	@Override
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

	@Override
	public void restart() {
		close();
		connect();
	}

	@Override
	public void close() {

		try {
			if (socket != null)
				socket.close();
			if (iStream != null)
				iStream.close();
			if (isReader != null)
				isReader.close();
			if (bufferReader != null)
				bufferReader.close();

			socket.shutdownInput();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send(String msg) {

		try {
			if (outputStream == null)
				outputStream = socket.getOutputStream();
			if (printWriter == null)
				printWriter = new PrintWriter(outputStream);

			printWriter.write(msg);
			printWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

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
			try {
				/*
				 * if (iStream != null) iStream.close(); if (isReader != null) isReader.close();
				 * if (bufferReader != null) bufferReader.close();
				 */

				socket.shutdownInput();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	public String getServerConfig() {
		// TODO Auto-generated method stub
		return serverIP + ":" + serverPort;
	}

	@Override
	public void setServerConfig(String ip, int port) {
		// TODO Auto-generated method stub
		this.serverIP = ip;
		this.serverPort = port;
	}

}
