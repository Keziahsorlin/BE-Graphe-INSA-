
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
		if(other instanceof LabelStar) {
			if (Double.compare(this.getTotalCost(), ((LabelStar)other).getTotalCost())==0) {
				return Double.compare(this.getDestCost(), ((LabelStar)other).getDestCost());
			}else {
				return Double.compare(this.getTotalCost(), ((LabelStar)other).getTotalCost());
			}
			//Cast évite l'implementation de getTotalCost sur Label
			
				
		}else {
			return super.compareTo(other);
			
		}
		
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
