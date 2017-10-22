import java.util.Scanner;

public class TestClient {
	public static void main(String[] args) {

		MsgClient msgClient = new MsgClient("127.0.0.1", 1025);
		msgClient.connect();

		Scanner scanner = new Scanner(System.in);
		String msg;
		msg = scanner.nextLine();
		while (!msg.equals("quit")) {

			msgClient.send(msg);
			//msgClient.read();

/*			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/

			//System.out.println("***" + msg);
			msg = scanner.nextLine();
		}
		scanner.close();
	}
}
