import java.util.Scanner;

public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		MsgServer server=new MsgServer(1025);
		ReadMsgServer readMsgServer=new ReadMsgServer(server);
		SendMsgServer sendMsgServer=new SendMsgServer(server);
		
		readMsgServer.start();
		Thread.sleep(1000);
		sendMsgServer.start();
		System.out.println("MsgServer started");
	}

}

class ReadMsgServer extends Thread{
	MsgServer server=null;
	public ReadMsgServer(MsgServer server) {
		this.server=server;
		server.listen();
	}
	public void run() {
		super.run();
		
		while(server!=null) {
			server.read();
/*			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}*/
		}
		
	}
}

class SendMsgServer extends Thread{
	MsgServer server=null;
	public SendMsgServer(MsgServer server) {
		this.server=server;
		server.listen();
	}
	public void run() {
		super.run();
		
		Scanner scanner=new Scanner(System.in);
		String msg;
		while(server!=null && (msg=scanner.nextLine())!="quit") {
			server.send(msg);
/*			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		//server.close();
	}
}