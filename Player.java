
public class Player implements Cloneable {

	public int payoff;
	public char action;
	public char prevAction;
	public String type;

	public Player(String type) {
		if (type.equals("AD")) {
			this.action = 'd';
			this.prevAction = 'd';
		}
		else {
			this.action = 'c';
			this.prevAction = 'c';
		}
		this.type = type;
		this.payoff = 0;
	}

	public void setAction(char action) {
		this.prevAction = this.action;
		this.action = action;
	}

	public void setPayoffs(Player p) {
		if (p.action == this.action) {
			if (this.action == 'c') {
				p.payoff += 3;
				this.payoff += 3;
			}
			else {
				p.payoff += 1;
				this.payoff += 1;
			}
		}
		else {
			if (this.action == 'c') {
				p.payoff += 5;
				this.payoff += 0;
			}
			else {
				p.payoff += 0;
				this.payoff += 5;
			}
		}
	}

	public Object clone() throws CloneNotSupportedException { 
		return super.clone(); 
	} 

}
