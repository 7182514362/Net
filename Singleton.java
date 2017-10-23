/*import javax.sound.midi.VoiceStatus;

public class Singleton {
	private volatile static MsgServer server=null;
	
	public static MsgServer getServer() {
		if(server==null) {
			synchronized (server) {
				if(server==null)
					server=new MsgServer(1026);
			}
		}
		return server;
	}
}*/


//better implementation
public class Singleton {
	
	private static class SingletonServer {
		private static final MsgServer server=new MsgServer(1026);
	}
	
	//can not create instance outside
	private Singleton(){
		
	}
	
	public static MsgServer getServer() {
		return SingletonServer.server;
	}
}