import java.io.IOException;
import java.util.Scanner;

public class TestClient {
	public static void main(String[] args) throws IOException {

		MsgClient msgClient = new MsgClient("127.0.0.1", 1026);
		msgClient.connect();
		
		new Thread(msgClient).start();
		
		Scanner scanner = new Scanner(System.in);
		String msg;
		msg = scanner.nextLine();
		while (!msg.equals("close")) {

			msgClient.send(msg);

			msg = scanner.nextLine();
		}
		msgClient.send("close");
		msgClient.close();
		scanner.close();
		System.out.println("Bye.");
	}
}
