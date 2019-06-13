//Vatmanidis Nikolaos 3150009
//Morfiadakis Emmanouil 3150112
public class Inferred{
	private char p;
	private boolean b;
	public Inferred(char p,boolean b){
		this.p=p;
		this.b=b;
	}
	public void setSymbol(char p){
		this.p=p;
	}
	public void setBool(boolean b){
		this.b=b;
	}
	public char getSymbol(){
		return p;
	}
	public boolean getBool(){
		return b;
	}
}