
package  org.insa.graphs.model;




public class Label implements Comparable <Label> {
	protected Node sommet;
	protected boolean marque;
	protected double cost;
	private Arc father;
	
	public int compareTo(Label other)
	{
		return Double.compare(this.cost, other.cost);
	}
		
	public Label(Node som) {
		this.sommet=som;
		this.marque=false;
		this.cost=1.0/0.0;
		this.father=null;
	}

	public double getCost() {
		return this.cost;
	}
	//public void set(){}
}