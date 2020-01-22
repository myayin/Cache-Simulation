
public class L2 {
	static int s=0;
	static double S=Math.pow(2,s);
	static int b=0 ;
	static int E=0;
	
	static int HitsCounter=0;
	static int MissCounter=0;
	
	static int B =(int) Math.pow(2,b);
	
	
	set [] numberOfSet=new set[(int)S];
	public L2(int s,int b,int E) {
		this.s=s;
		this.b=b;
		this.E=E;
		this.S=Math.pow(2,s);
		for(int i=0;i<S;i++) {
			numberOfSet[i]=new set(i,E);
		}
	}
	public set[] getNumberOfSet() {
		return numberOfSet;
	}
	public void setNumberOfSet(set[] numberOfSet) {
		this.numberOfSet = numberOfSet;
	}
	
	public static void printL2(L2 l) {
		
		for(int i=0;i<L2.S;i++) {
			for(int j=0; j<L2.E;j++) {
				System.out.print(l.numberOfSet[i].line[j].getTag()+" "+l.numberOfSet[i].line[j].getTime()+" "+l.numberOfSet[i].line[j].isV()+" "+l.numberOfSet[i].line[j].getData() );
				System.out.println();
				
			}
		}
	}
}
