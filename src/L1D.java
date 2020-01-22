
public class L1D {
	static int s=0;
	static double S=Math.pow(2,s);
	static int b=0 ;
	static int E=0;
	
	static int HitsCounter=0;
	static int MissCounter=0;
	
	static int B =(int) Math.pow(2,b);
	
	
	set [] numberOfSet=new set[(int)S];
	public L1D(int s,int b,int E) {
		this.s=s;
		this.b=b;
		this.E=E;
		
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
	
	public static void printL1D(L1D l) {
		for(int i=0;i<L1D.S;i++) {
			for(int j=0; j<L1D.E;j++) {
				System.out.print(l.numberOfSet[i].line[j].getTag()+" "+l.numberOfSet[i].line[j].getTime()+" "+l.numberOfSet[i].line[j].isV()+" "+l.numberOfSet[i].line[j].getData() );
				System.out.println();
				
			}
		}
	}

}
