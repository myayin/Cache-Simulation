
public class Line  {
 private String Data=null;
 private int Tag=0;
 private int time=0;
 private boolean v=false;
int adres=0;
 int index=0;

 
 public Line(String Data,int Tag,int time, boolean v, int index) {
	this.Data=Data;
	this.Tag=Tag;
	this.time=time;
	this.v=v;
	this.index=index;
	
}

public String getData() {
	return Data;
}
public void setData(String data) {
	Data = data;
}
public int getTag() {
	return Tag;
}
public void setTag(int tag) {
	Tag = tag;
}
public int getTime() {
	return time;
}
public void setTime(int time) {
	this.time = time;
}
public boolean isV() {
	return v;
}
public void setV(boolean v) {
	this.v = v;
}






}



