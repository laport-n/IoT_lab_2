package hostCSMA;
import java.lang.Thread;


public class CSMA_CA extends Thread {
	
	private String name;
	private int id;
	private int packetToSent;
	
	public static int state = 0;
	public static int delay;
	public static String key = "";
	
	public CSMA_CA(String name, int idHost){
		this.name = name;
		this.id = idHost;
		this.packetToSent = id;
		delay = (2 * packetToSent) * 100;
		System.out.println("New CLIENT : ");
		System.out.println("Name : " + this.name);
		System.out.println("ID : " + this.id);
	}
	
	  public  void sendPacket(int nbPacket) {
		  System.out.println(this.name + " Transmit packet " + nbPacket);
		  if (nbPacket == this.id){
			state = 0;
			System.out.println("[Recepteur][ACK] Acknowledgement. Transmission ending for " + this.name + "\n");
		  }
	   }
	  
	  public  void waitForACK() throws InterruptedException {
	  }
	
	 public  void run(){
		int packetNb = 0;
		while (this.packetToSent > 0){
				if (state == 0 && this.packetToSent == this.id && key == ""){
					key = this.name;
					state = 1;
					if (key == this.name){
						System.out.println(this.name + "[RTS] Ready To send" + "[DATA:[Number of packets : " + this.packetToSent + "]]");
						//Here normaly the server determin the sleep delay for all other hosts
						System.out.println("[Recepteur][CTS] Clear To send " + this.packetToSent + "packets" + " From" + this.name);
						packetNb += 1;
						this.packetToSent -= 1;
						System.out.println(this.name + " send packet number : " + packetNb);
					}
				} else if (state == 1 && key == this.name){
					this.packetToSent -= 1;
					packetNb += 1;
					System.out.println(this.name + " send packet number : " + packetNb);
					if (this.packetToSent == 0){
						System.out.println("[Recepteur][ACK] acknowledgment. Transmission ending for " + this.name);
						state = 0;
						key = "";
					}
				} else {
					try	{
						System.out.println(this.name + "Waiting");
						Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(this.name + "STOP \n");
	}
}
