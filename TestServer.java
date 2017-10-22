
public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		MsgServer server=new MsgServer(1025);
		
		new Thread(server).start();
		new Thread(server).start();
		
	}

}
