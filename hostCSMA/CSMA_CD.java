package hostCSMA;

import java.lang.Thread;

public class CSMA_CD extends Thread {
	/**
	 * static variables
	 * @totalCollisions -> nb total of collisions
	 * @state -> state of currents clients
	 * @faillures -> nb total of faillure
	 */
	public static int totalCollisions = 0;//Static nb of collisions for all clients
	public static int state = 0; //state indicator, 3 possibilities, 0 = not idle, 1 = sent, 2 = collisions
	public static int faillures = 0; //Static nb of faillure for all clients
	
	private int countCollisions = 0; //Count the number of collisions
	private String name; //Private name of hostCSMA
	private int id; //Private ID of hostCSMA
	private int delay; //sending delay
	private long timeChrono = 0; //Intern chrono
	
	public CSMA_CD(String name, int idHost, int propDelay){
		this.name = name;
		this.id = idHost;
		this.delay = propDelay;
		System.out.println("New CLIENT : ");
		System.out.println("Name : " + this.name);
		System.out.println("ID : " + this.id);
		System.out.println("Delay : " + this.delay);
	}
	
	void goChrono() { 
		timeChrono = java.lang.System.currentTimeMillis();
	}
	
	void stopChrono(){
		long timeChrono2 = java.lang.System.currentTimeMillis();
		timeChrono = timeChrono2 - timeChrono;
		System.out.println(name + " Delay " + timeChrono + " ms");
	}
	
	public void run() {
		goChrono();
		for (int packetNumber = 1; packetNumber <= id; packetNumber++) {
			countCollisions = 0;
			while (countCollisions < 10) {
				if (state == 0) {
					System.out.println(name + " is state 0");
					try {
						Thread.sleep(delay);
						System.out.println(name + " Enter in sleep mode for " + delay);
					} catch (InterruptedException e) {
						System.err.println("Inturrupted: Interrupt exception");
					}
					state++;
				}	
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					System.err.println("Inturrupted: Interrupt exception");
				}
				if (state == 1){
					System.out.println(name + " is state 1");
					System.out.println(name + " Packet n°" + packetNumber + " sent with success");
					state = 0;
					break;
				}
				if (state >= 2) {
					System.out.println(name + " is state 2");
					countCollisions++;
					totalCollisions++;
					System.out.println(name + " Packet n°" + packetNumber + " Collision!");
					state = 0;
					try {
						//Set a timer depending of how many collisions packet enconter
						Timer timer = new Timer();
						int sleepingTimeRandom = (2* delay * timer.randomSleepTime(countCollisions));
						Thread.sleep(sleepingTimeRandom);
						System.out.println(name + " Enter in sleeping mode, depend of number of collisions for " + sleepingTimeRandom);
					} catch (InterruptedException e) {
						System.err.println("Inturrupted: Interrupt exception");
					}
					continue;
				}
			}
			if (countCollisions >= 10){
				System.out.println("Host computer[" + name + "]Packet " + packetNumber + " transmission failure.");
				faillures++;
			}
		}
		stopChrono();
		System.out.println(name + " All packet send ! with success");
		System.out.println("Nb Total of collisions : "  + totalCollisions);
		System.out.println("Nb total of faillure : " + faillures);
	}
	
}
