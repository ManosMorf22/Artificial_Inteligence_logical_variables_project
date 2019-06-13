//Vatmanidis Nikolaos 3150009
//Morfiadakis Emmanouil 3150112
public class Clause{
	private String p;
	private boolean positive;
	
	public Clause(String p,boolean positive){
		this.p=p;
		this.positive=positive;
	}
	public String getp(){
		return p;
	}
	public boolean Pos(){
		return positive;
	}
	public boolean check(Clause c){
		if(getp().equals(c.getp())&&Pos()==!c.Pos()) return true;
		return false;
	}
	public String ToString(){
		String s="";
		if(!Pos())s=s+"!";
		s=s+p;
		return s;
	}
	public boolean same(Clause pq){
		if(p.equals(pq.getp())&&positive==pq.Pos()) return true;
		return false;
	}
}