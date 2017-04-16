package hostCSMA;

public class Timer {
		
		public int randomSleepTime(int transNum) { 
			int rndom;
			int temp;
			temp = Math.min(transNum, 10);
			rndom = (int)(Math.random() * (Math.pow(2, temp) -1));
			return rndom;
		}
}
