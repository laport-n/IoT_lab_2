package hostCSMA;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		
		int nbClients;
		int nbPackets;
		int delay = 0;
		String patern;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of hosts");
		nbClients = scan.nextInt();
		System.out.print("Enter the number of packets you need to sent per hosts");
		nbPackets = scan.nextInt();
		System.out.print("Enter the pattern (CSMA/CD or CSMA/CA)");
		patern = scan.next();
		
		if (patern.equals("CSMA/CD")){
			System.out.print("Enter the delay time");
			delay = scan.nextInt();
		}
		scan.close();
		if (patern.equals("CSMA/CD")){
			CSMA_CD[] virtualHosts = new CSMA_CD[nbClients];
			for (int i = 0; i < nbClients; i++){
				virtualHosts[i]= new CSMA_CD("[HOST:" + i + "]", nbPackets, delay);
			}
			//Starting host threads (start sending)
			for (int j = 0; j < nbClients; j++){
				virtualHosts[j].start();
			}
		} else if (patern.equals("CSMA/CA")){
			CSMA_CA[] virtualHosts = new CSMA_CA[nbClients];
			for (int i = 0; i < nbClients; i++){
				virtualHosts[i] = new CSMA_CA("[HOST:" + i + "]", nbPackets);
			}
			
			for (int j = 0; j < nbClients; j++){
				virtualHosts[j].start();
			}
		}
	}
}
