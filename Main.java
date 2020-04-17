import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream; 

public class Main {

	public static double numPlayers = 1;
	public static int numRepeats;
	public static int p;
	public static int k;

	public static int T4Toverall = 0;
	public static int Goverall = 0;
	public static int ACoverall = 0;
	public static int ADoverall = 0;

	public static void main(String[] args) throws CloneNotSupportedException  {

		Scanner scanner = new Scanner(System.in);

		// get values for n, m, p, and k
		while (numPlayers % 4 != 0) {
			System.out.print("How many players would you like to have?   ");
			String input = scanner.nextLine();
			numPlayers = Integer.parseInt(input);
		}

		System.out.print("How many repeated games should there be?   ");
		String input = scanner.nextLine();
		int numRepeats = Integer.parseInt(input);

		System.out.print("What is the cutoff percent?   ");
		input = scanner.nextLine();
		int p = Integer.parseInt(input);

		System.out.print("How many generations would you like?   ");
		input = scanner.nextLine();
		int k = Integer.parseInt(input);

		scanner.close();

		// instantiate our arrays
		Player[][] generations = new Player[(int) numPlayers][k];

		double numPlayersPerGroup = numPlayers / 4;

		Player[] allPlayers = new Player[(int) numPlayers];

		//		// divide n among the 4 types of players
		//		for (int i = 0; i < numPlayers; i++) {
		//			if (i < numPlayersPerGroup) {
		//				allPlayers[i] = new Player("T4T");
		//			}
		//			else if (i >= numPlayersPerGroup && i < 2 * numPlayersPerGroup) {
		//				allPlayers[i] = new Player("G");
		//			}
		//			else if (i >= 2 * numPlayersPerGroup && i < 3 * numPlayersPerGroup) {
		//				allPlayers[i] = new Player("AC");
		//			}
		//			else {
		//				allPlayers[i] = new Player("AD");
		//			}
		//		}

		//		// DISTRIBUTION 1: 20% AD, 15% AC, 30% G, 35% T4T
		//		for (int i = 0; i < numPlayers; i++) {
		//			if (i < (0.35 * numPlayers)) {
		//				allPlayers[i] = new Player("T4T");
		//			}
		//			else if (i >= (0.35 * numPlayers) && i < (0.65 * numPlayers)) {
		//				allPlayers[i] = new Player("G");
		//			}
		//			else if (i >= (0.65 * numPlayers) && i < (0.8 * numPlayers)) {
		//				allPlayers[i] = new Player("AC");
		//			}
		//			else {
		//				allPlayers[i] = new Player("AD");
		//			}
		//		}

				// DISTRIBUTION 2: 10% AD, 70% AC, 10% G, 10% T4T
				for (int i = 0; i < numPlayers; i++) {
					if (i < (0.1 * numPlayers)) {
						allPlayers[i] = new Player("T4T");
					}
					else if (i >= (0.1 * numPlayers) && i < (0.2 * numPlayers)) {
						allPlayers[i] = new Player("G");
					}
					else if (i >= (0.2 * numPlayers) && i < (0.9 * numPlayers)){
						allPlayers[i] = new Player("AC");
					}
					else {
						allPlayers[i] = new Player("AD");
					}
				}

		// DISTRIBUTION 3: 0% AD, 33% AC, 33% G, 34% T4T
//		for (int i = 0; i < numPlayers; i++) {
//			if (i < (0.34 * numPlayers)) {
//				allPlayers[i] = new Player("T4T");
//			}
//			else if (i >= (0.34 * numPlayers) && i < (0.67 * numPlayers)) {
//				allPlayers[i] = new Player("G");
//			}
//			else {
//				allPlayers[i] = new Player("AC");
//			}
//		}

		ArrayList<Double> T4Tavg = new ArrayList<Double>();
		ArrayList<Double> Gavg = new ArrayList<Double>();
		ArrayList<Double> ACavg = new ArrayList<Double>();
		ArrayList<Double> ADavg = new ArrayList<Double>();

		ArrayList<Double> T4Tsum = new ArrayList<Double>();
		ArrayList<Double> Gsum = new ArrayList<Double>();
		ArrayList<Double> ACsum = new ArrayList<Double>();
		ArrayList<Double> ADsum = new ArrayList<Double>();
		ArrayList<Double> totalSum = new ArrayList<Double>();

		ArrayList<Double> T4Tpop = new ArrayList<Double>();
		ArrayList<Double> Gpop = new ArrayList<Double>();
		ArrayList<Double> ACpop = new ArrayList<Double>();
		ArrayList<Double> ADpop = new ArrayList<Double>();

		// do this for k generations
		for (int x = 0; x < k; x++) {

			// run nC2 rounds of the game
			for (int i = 0; i < numPlayers-1; i++) {
				for (int j = i + 1; j < numPlayers; j++) {
					play(allPlayers[i], allPlayers[j], numRepeats);
					reset(allPlayers[i]);
					reset(allPlayers[j]);
				}
			}

			// print out the stats for this generation
			double[] T4Tinfo = getTypeInfo("T4T", allPlayers, T4Toverall);
			double[] Ginfo = getTypeInfo("G", allPlayers, Goverall);
			double[] ACinfo = getTypeInfo("AC", allPlayers, ACoverall);
			double[] ADinfo =getTypeInfo("AD", allPlayers, ADoverall);

			//			System.out.println("\n Percentage of population of each type of player");

			T4Tpop.add(T4Tinfo[0]);
			Gpop.add(Ginfo[0]);
			ACpop.add(ACinfo[0]);
			ADpop.add(ADinfo[0]);
			//			System.out.println("GEN" + (x + 1) + ":    " + "T4T: " + T4Tinfo[0] + "%   G: " + Ginfo[0] + "%   "
			//					+ "AC: " + ACinfo[0] + "%   AD: " + ADinfo[0] + "%");

			int total = T4Toverall + Goverall + ACoverall + ADoverall;
			//			System.out.println("\n Sum of payoff for each type of player");
			T4Tsum.add((double) T4Toverall);
			Gsum.add((double) Goverall);
			ACsum.add((double) ACoverall);
			ADsum.add((double) ADoverall);
			totalSum.add((double) total);
			//			System.out.println("GEN" + (x + 1) + ":    " + "T4T: " + T4Toverall + "   G: " + Goverall + "   "
			//					+ "AC: " + ACoverall + "   AD: " + ADoverall + "   Total: " + total);


			T4Tavg.add(T4Tinfo[1]);
			Gavg.add(Ginfo[1]);
			ACavg.add(ACinfo[1]);
			ADavg.add(ADinfo[1]);
			//			System.out.println("\n Average payoff for each type of player");
			//			System.out.println("GEN" + (x + 1) + ":    " + "T4T: " + T4Tinfo[1] + "   G: " + Ginfo[1] + "   "
			//					+ "AC: " + ACinfo[1] + "   AD: " + ADinfo[1]);

			// sort the players by score
			Arrays.sort(allPlayers, new Comparator<Player>() {
				public int compare(Player p1, Player p2) {
					if (p1.payoff > p2.payoff) {
						return 1;
					}
					else if (p1.payoff < p2.payoff) {
						return -1;
					}
					else {
						return 0;
					}
				}
			});

			// get the bottom p% of players
			double numToRemove = Math.ceil(p * 0.01 * numPlayers);

			// remove the bottom p% of players and replace with clones of the top p% of players
			int backIt = allPlayers.length - 1;
			for (int i = 0; i < (int) numToRemove; i++) {
				allPlayers[i] = (Player) allPlayers[backIt].clone();
				backIt--;
			}

			for (int i = 0; i < numPlayers; i++) {
				generations[i][x] = (Player) allPlayers[i].clone();
			}

			// reset the cumulative payoffs for the next generation
			T4Toverall = 0;
			Goverall = 0;
			ACoverall = 0;
			ADoverall = 0;

		}

		System.out.println(" ");

		System.out.println("T4T sums: " + T4Tsum);
		System.out.println("G sums: " + Gsum);
		System.out.println("AC sums: " + ACsum);
		System.out.println("AD sums: " + ADsum);
		System.out.println("total sums: " + totalSum);

		System.out.println("T4T avg: " + T4Tavg);
		System.out.println("G avg: " + Gavg);
		System.out.println("AC avg: " + ACavg);
		System.out.println("AD avg: " + ADavg);

		System.out.println("T4T pop: " + T4Tpop);
		System.out.println("G" + Gpop);
		System.out.println("AC pop: " + ACpop);
		System.out.println("AD pop: " + ADpop);

	}

	// play numRepeats repeated rounds of the game
	public static void play(Player p1, Player p2, int numRepeats) {
		p1.setPayoffs(p2);
		updateCumulativePayoff(p1);
		updateCumulativePayoff(p2);
		for (int i = 1; i < numRepeats; i++) {
			if (p1.type == "T4T") {
				p1.action = p2.prevAction;
			}
			if (p2.type == "T4T") {
				p2.action = p1.prevAction;
			}
			if (p1.type == "G" && p2.prevAction == 'd') {
				p1.action = 'd';
			}
			if (p2.type == "G" && p1.prevAction == 'd') {
				p2.action = 'd';
			}
			p1.setPayoffs(p2);
			updateCumulativePayoff(p1);
			updateCumulativePayoff(p2);
		}
	}

	public static void updateCumulativePayoff(Player p) {
		if (p.type == "T4T") {
			T4Toverall += p.payoff;
		}
		if (p.type == "G") {
			Goverall += p.payoff;
		}
		if (p.type == "AC") {
			ACoverall += p.payoff;
		}
		if (p.type == "AD") {
			ADoverall += p.payoff;
		}
	}

	public static void reset(Player p) {
		if (p.type == "AD") {
			p.setAction('d');
		}
		else {
			p.setAction('c');
		}
	}

	public static double[] getTypeInfo(String type, Player[] allPlayers, int payoff) {
		double count = 0;
		for (int i = 0; i < numPlayers; i++) {
			if (allPlayers[i].type == type) {
				count++;
			}
		}
		double perc = (count / numPlayers) * 100;

		double[] info = new double[2];
		info[0] = perc;
		if (payoff != 0) {
			info[1] = payoff / count;
		}
		else {
			info[1] = 0;
		}

		return info;
	}


}
