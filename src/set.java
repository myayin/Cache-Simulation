
public class set {
	 int E=0;
	Line line[]=null;
	int index=0;
	int minLineL1I=0;
	int minLineL1D=0;//Bunun içinde yapılcak
	int minLineL2=0;
	public set(int index,int E) {
		this.E=E;
		this.line=new Line[E];
		this.index=index;
		for(int i=0;i<E;i++) {
			this.line[i]=new Line(null,0,0,false,i);
		}
		
	}

	public Line[] getLine() {
		return line;
	}

	public void setLine(Line[] line) {
		this.line = line;
	}

	public int getE() {
		return E;
	}

	public void setE(int e) {
		E = e;
	}
	
	
}
