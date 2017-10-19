import java.util.Scanner;

public class TestClient {
	public static void main(String[] args) {
			MsgClient msgClient=new MsgClient("127.0.0.1", 1025);
			msgClient.connect();
			Scanner scanner=new Scanner(System.in);
			String msg;
			while((msg=scanner.nextLine())!="quit") {
				msgClient.send(msg);
				msgClient.read();
/*				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
	}
}
