
public class TestServer {

	public static void main(String[] args) throws InterruptedException {
		MsgServer server=new MsgServer(1025);
		
		for(int i=0;i<10;i++)
			new Thread(server).start();
		
	}

}
