
package  org.insa.graphs.model;


public class LabelVelostar extends Label {
	protected Node dest;
	protected Node som;
	protected double cost2;
	public LabelVelostar(Node som, double cost2) {
		super(som);
		this.som= som;
		this.dest = dest;
		this.cost2 = cost2;//on reste sur de la recherche astar
        
	}
	@Override
	public int compareTo(Label other)
	{
		//Cast évite l'implementation de getTotalCost sur Label
		return Double.compare(this.getTotalCost(), ((LabelVelostar)other).getTotalCost());
		
	}
	//data
	
	public double getDestCost(){
		return this.cost2;
		}
	
	public double getTotalCost() {
		return this.getDestCost() + this.getCost();
	}
	
	//public void set(){}
}
