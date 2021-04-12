
package  org.insa.graphs.model;




public class Label implements Comparable <Label> {
	protected Node sommet;
	protected boolean marque;
	protected double cost;
	private Arc father;
	@Override
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
	public boolean getMark() {
		return this.marque;
	}
	public Arc getFather() {
		return this.father;
	}
	public Node getSommet() {
		return this.sommet;
	}
	
	public void setMark(Boolean bol){
		this.marque=bol;
	}
	public void setCost(Double dob){
		this.cost=dob;
	}
	public void setFather(Arc Father){
		this.father=Father;
	}
	public void setSommet(Node n){
		this.sommet=n;
	}
	//public void set(){}
}