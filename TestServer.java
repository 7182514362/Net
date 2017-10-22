import java.util.Scanner;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		MsgServer server=new MsgServer(1025);
		ReadMsgServer readMsgServer=new ReadMsgServer(server);
		SendMsgServer sendMsgServer=new SendMsgServer(server);
		
		System.out.println("read");
		readMsgServer.start();
		Thread.sleep(500);
		System.out.println("send");
		sendMsgServer.start();
	}

}

class ReadMsgServer extends Thread{
	MsgServer server=null;
	public ReadMsgServer(MsgServer server) {
		this.server=server;
		
	}
	public void run() {
		super.run();
		
		if(server!=null) {
			server.listen();
			server.read();	
		}
		
		server.close();	
	}
}

class SendMsgServer extends Thread{
	MsgServer server=null;
	public SendMsgServer(MsgServer server) {
		this.server=server;
		
	}
	public void run() {
		super.run();
		
		if(server!=null)
			server.listen();
		
		Scanner scanner=new Scanner(System.in);
		String msg=scanner.nextLine();
		while(server!=null && !msg.equals("quit")) {
			server.send(msg);
			msg=scanner.nextLine();
		}
		scanner.close();
		server.close();
	}
}