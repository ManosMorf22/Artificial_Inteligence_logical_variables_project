//Vatmanidis Nikolaos 3150009
//Morfiadakis Emmanouil 3150112
public class Count{
	private int c;
	private Clause cl;
	
	public Count(int c,Clause cl){
		this.c=c;
		this.cl=cl;
	}
	public void setCount(int c){
		this.c=c;
	}
	public void setClause(Clause cl){
		this.cl=cl;
	}
	public int getCount(){
		return c;
	}
	public Clause getClause(){
		return cl;
	}
}