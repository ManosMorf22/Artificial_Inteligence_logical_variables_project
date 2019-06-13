//Vatmanidis Nikolaos 3150009
//Morfiadakis Emmanouil 3150112
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Iterator;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Assigment2{
public static void main(String Args[]){
System.out.println("Hello.If you want PL resolution type 1.If you want forward chaining type another integer");
Scanner in=new Scanner(System.in);
int x=in.nextInt();
System.out.println("Please type the file");
String file=in.next();
String wh="";
if(x==1)
	wh="negation of the";
System.out.println("Type the "+wh+ " form you want to be proved");
String form=in.next();
if(x!=1){
	System.out.println(example2(file,form));
}else{
	System.out.println(example1(file,form));
}
}
public static void makeC(ArrayList<Clause> p,String x){
	if(Character.isLetter(x.charAt(0)))
		p.add(new Clause(x.charAt(0)+"",true));
	int sizx=x.length();
	boolean c=true;
	for(int i=1; i<sizx; i++){
		if(Character.isLetter(x.charAt(i))){
			if(x.charAt(i-1)=='!'){
				c=false;
			}
			p.add(new Clause(x.charAt(i)+"",c));
			c=true;
		}
	}
}
public static HashSet<String> Resolve(String x,String y){
	HashSet<String> re=new HashSet<String>();
	ArrayList<Clause>xp=new ArrayList<Clause>();
	ArrayList<Clause>yp=new ArrayList<Clause>();
	makeC(xp,x);
	makeC(yp,y);
	int sizx=xp.size();
	int sizy=yp.size();
	for(int i=0; i<sizx; i++){
		for(int j=0; j<sizy; j++){
			if(xp.get(i).check(yp.get(j))){
				xp.remove(i);
				yp.remove(j);
			//
			ArrayList<Clause>z=new ArrayList<Clause>();	
			z.addAll(xp);
			z.addAll(yp);
				for(int ai=0; ai<z.size(); ai++){
					for(int aj=ai+1; aj<z.size(); aj++)
						if(z.get(ai).same(z.get(aj))){  //we do not want duplicates a|a ....a|b|a
							z.remove(aj);
							aj--;
						}
				}
				int sz=z.size();
				String s="";
				for(int ui=0; ui<sz; ui++){
					s=s+z.get(ui).ToString();
					if(ui!=sz-1)
						s=s+"|";
				}
			    re.add(s);
			    xp.clear();
			    yp.clear();
			    makeC(xp,x);
			    makeC(yp,x);
			    sizx=xp.size();
			    sizy=yp.size();
			    
			}
		}
	}
	return re;
	}
public static void start(HashSet<String> base,String s,boolean bl){
	try{
	FileReader fr=new FileReader(s);
	BufferedReader br=new BufferedReader(fr);
	String line=br.readLine();
	String bo;
	while(line!=null){
		bo=line;
		if(bl)
		while(bo!=null){
		if(bo.indexOf('&')>0){
			base.add(bo.substring(0,bo.indexOf('&')));
			bo=bo.substring(bo.indexOf('&')+1);
		}else{
			if(!base.contains(bo))
			base.add(bo);
			bo=null;
		}
		}
		line=br.readLine();
	}
}catch(IOException e){
	System.out.println("Wrong with the file");
}
}

public static boolean example1(String s,String form){
	HashSet<String> base=new HashSet<String>();
	start(base,s,true);
		base.add(form);
	//we made the base logic
	HashSet<String>New=new HashSet<String>();
	HashSet<String>resolvment;
 int size=base.size();
 //CNF resolution Algorithm lecture 8
 int fu=0;
 while(fu==0){
	 size=base.size();
	 Iterator it=base.iterator();
	 String[] basen=new String[size];
	 for(int u=0; u<size; u++)
		 basen[u]=(String)it.next();
 for(int i=0; i<size; i++){
	 for(int j=i+1; j<size; j++){
		 resolvment=Resolve(basen[i],basen[j]);
		 if(resolvment.contains("")) return true;//empty clause
			 New.addAll(resolvment); 
	 }
 }
		 if(New.isEmpty())
			 return false;
		Iterator it2=New.iterator();
		boolean b=false;
		while(it2.hasNext()){
			if(!base.contains((String)it2.next())) b=true;
		}
		if(!b) return false;
		base.addAll(New);	 


 }
return false;
}
public static boolean example2(String s,String h){
	//lecture 9 PL-FC entails
	HashSet<String> base=new HashSet<String>();
	start(base,s,false);
	Iterator ag=base.iterator();
	ArrayList<String> agenda=new ArrayList<String>();
	for(int i=0; i<base.size(); i++){
		agenda.add((String)ag.next());
	}
	ArrayList<Clause> cl=new ArrayList<Clause>();
	for(int i=0; i<agenda.size(); i++){
		makeC(cl,agenda.get(i));
	}
	ArrayList<Count> count=new ArrayList<Count>();
	for(int i=0; i<cl.size(); i++){
		count.add(new Count(base.size(),cl.get(i)));
	}
	ArrayList<Inferred> inferred=new ArrayList<Inferred>();
	for(int i=0; i<cl.size(); i++){
		inferred.add(new Inferred(cl.get(i).getp().charAt(0),false));
	}
	String p="";
	Inferred inf;
	while(!agenda.isEmpty()){
	p=agenda.remove(0);
	inf=find(inferred,p.charAt(0));
	if(!inf.getBool()){
		inf.setBool(true);
	}
	//continue
	int v=0;
	while(v<count.size()){
	v=decrement(count,p,v);
	if(count.get(v).getCount()==0){
		//continue
		if(count.get(v).getClause().getp().equals(h)) return true;
		base.add(h);
	}
	}
	}
	
	return false;
}

public static Inferred find(ArrayList<Inferred> ci,char p){
	for(int i=0; i<ci.size(); i++){
		if(ci.get(i).getSymbol()==p){		
			return ci.get(i);
		}
	}
	return null;
}

public static int decrement(ArrayList<Count>ci,String p,int v){
	for(int i=v; i<ci.size(); i++){
		if(ci.get(i).getClause().getp().equals(p)){		
			 ci.get(i).setCount(ci.get(i).getCount()-1);
			 return i;
		}
	}
	return 0;
}

}