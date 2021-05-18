
package  org.insa.graphs.model;


public class LabelStar extends Label {
	protected Node dest;
	protected Node som;
	protected double cost2;
	public LabelStar(Node som, double cost2) {
		super(som);
		this.som= som;
		this.dest = dest;
		this.cost2 = cost2;
        
	}
	@Override
	public int compareTo(Label other)
	{
		//Cast évite l'implementation de getTotalCost sur Label
		return Double.compare(this.getTotalCost(), ((LabelStar)other).getTotalCost());
		
	}
	//data
	
	public double getDestCost(){
		return this.cost2; //som.getPoint().distanceTo(dest.getPoint()); 
		}
	
	public double getTotalCost() {
		return this.getDestCost() + this.getCost();
	}
	
	//public void set(){}
}
