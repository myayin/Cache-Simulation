import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
	static String forl1 = "";
	static boolean isStore=false;
	static boolean isl1I=false;
	static String forl1D = "";
	static int numberOfLines = 0;
	static int aMinLine = 0;
	private static BufferedReader br2;
	static int timeL1 = 0;
	static int timeL2 = 0;
	static int evictionTimeL1 = 0;
	static int evictionTimeL1D = 0;
	static int evictionTimeL2 = 0;
	static int isStoreL1I=0;
	static boolean isStoreL2=false;
	private static String forL2;
	Line minLineL1D = new Line(null, 0, 0, false, 0);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		L1I.s = Integer.parseInt(args[1]);
		L1I.b = Integer.parseInt(args[5]);
		L1I.E = Integer.parseInt(args[3]);

		L1I.B = (int) Math.pow(2, L1I.b);
		L1I.S = (int) Math.pow(2, L1I.s);
		L1I l = new L1I(L1I.s, L1I.b, L1I.E);
		L1I lResult = null;
		L1D.s = Integer.parseInt(args[1]);
		L1D.b = Integer.parseInt(args[5]);
		L1D.E = Integer.parseInt(args[3]);

		L1D.B = (int) Math.pow(2, L1D.b);
		L1D.S = (int) Math.pow(2, L1D.s);
		L1D ld = new L1D(L1D.s, L1D.b, L1D.E);
		L1D ldresult = null;

		L2.s = Integer.parseInt(args[7]);
		L2.b = Integer.parseInt(args[11]);
		L2.E = Integer.parseInt(args[9]);

		L2.B = (int) Math.pow(2, L2.b);
		L2.S = (int) Math.pow(2, L2.s);
		L2 l2 = new L2(L2.s, L2.b, L2.E);

		L2 l2Result = null;
		// System.out.println("length"+l.numberOfSet[0].line.length);
		// decimalToBinary(32);
		// System.out.println("length line"+l.numberOfSet[0].line.length);
		File f = new File(args[13]);
		FileReader fr = new FileReader(f);

		BufferedReader br = new BufferedReader(fr);
		String line = "";
		for (int i = 0; i >= 0; i++) {
			line = br.readLine();
			if (line == null) {
				// System.out.println("end of the file");
				break;
			} else {
				numberOfLines++;

			}
		}

		File f2 = new File(args[13]);
		FileReader fr2 = new FileReader(f2);
		br2 = new BufferedReader(fr2);
		int j = 0;
		int count = 0;
		Trace[] trace = new Trace[numberOfLines];
		for (int i = 0; i >= 0; i++) {
			line = br2.readLine();

			if (line == null) {

				break;
			} else {
				count = 0;
				// System.out.println("Line"+line);
				char someChar = ' ';

				for (int z = 0; z < line.length(); z++) {
					// System.out.println("chars"+line.charAt(z));
					if (line.charAt(z) == someChar) {
						count++;
					}
				}

				// System.out.println();

				String operation = line.split(" ")[0].replaceFirst(",", "");

				String address = line.split(" ")[1].replaceFirst(",", "");

				String size = line.split(" ")[2].replaceFirst(",", "");
				
				if(size.equals("0"))
					continue;
				// trace i alýyorum
				trace[j] = new Trace(address.replaceFirst(",", ""), operation, size);
				// System.out.println("lengthdkfdf:"+line.length());
				if (count == 3) {
					String data = line.split(" ")[3].replaceFirst(",", "");

					// System.out.println("daataa:"+data);
					trace[j].setData(data);
				}
				// System.out.println("adress"+trace[j].data);
				
				System.out.println(line);
				lResult = setOperation(l, trace[j]);
				ldresult = setOperation(ld, trace[j]);
				l2Result = setOperation(l2, trace[j]);
				isStoreL2=false;
				isStore=false;
				forl1 = "";
				forl1D = "";
				forL2="";
				isl1I=false;
				j++;
			/*	System.out.println("Result of L1I");
				L1I.printL1I(lResult);
				System.out.println();
				System.out.println("Result of L1D");
				L1D.printL1D(ldresult);
				System.out.println();
				System.out.println("Result of L2");*
				L2.printL2(l2Result);*/
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------------------");

			}
		}
		// System.out.println(returnData(trace[0].address.toUpperCase()));
		System.out.println("L1I-hits:"+L1I.HitsCounter+"  L1I-misses: "+L1I.MissCounter+"  L1I-evictions:"+evictionTimeL1);
		System.out.println("L1D-hits:"+L1D.HitsCounter+"  L1D-misses: "+L1D.MissCounter+"  L1D-evictions:"+evictionTimeL1D);
		System.out.println("L2-hits:"+L2.HitsCounter+"  L2-misses: "+L2.MissCounter+"  L2-evictions:"+evictionTimeL2);
		
		writeToFile(lResult,l2Result,ldresult);
	}

	public static String[] goRAM(String address) throws IOException { // string array döndürür birincide adres
		int decadd = hexadecimalToDecimal(address);

		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		
		RandomAccessFile raf = new RandomAccessFile("ram.txt", "rw");
		raf.seek(fark*3); // BU YANLIÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞ
		byte [] bytes=new byte[3*L1I.B];
		raf.read(bytes);
		String s=new String(bytes);
		String[] ret = new String[2];
		
		ret[1]=s;
		raf.close();
		
	
		
		
		ret[0] = Integer.toString(fark);
		
		return ret;
	}

	static int hexadecimalToDecimal(String hexVal) {
		int len = hexVal.length();

		// Initializing base value to 1, i.e 16^0
		int base = 1;

		int dec_val = 0;

		// Extracting characters as digits from last character
		for (int i = len - 1; i >= 0; i--) {

			if (hexVal.charAt(i) >= '0' && hexVal.charAt(i) <= '9') {
				dec_val += (hexVal.charAt(i) - 48) * base;

				// incrementing base by power
				base = base * 16;
			}

			// if character lies in 'A'-'F' , converting
			// it to integral 10 - 15 by subtracting 55
			// from ASCII value
			else if ((hexVal.charAt(i) >= 'A') && (hexVal.charAt(i) <= 'F')) {
				dec_val += (hexVal.charAt(i) - 55) * base;

				// incrementing base by power
				base = base * 16;
			} else if (hexVal.charAt(i) >= 'a' && hexVal.charAt(i) <= 'f') {
				dec_val += (hexVal.charAt(i) - 87) * base;

				// incrementing base by power
				base = base * 16;

			}
		}
		return dec_val;
	}

	public static L1I setOperation(L1I l, Trace t) throws IOException {
		switch (t.operation) {
		case ("I"):

			l = LoadInstruction(l, t.address);
			return l;

		/*
		 * case("L"):
		 * 
		 * return l;
		 */

		case ("S"):
			// String[] d=new String[2];
			// d=goRAM(t.address);
			
			modifyData("ram.txt", t.address, t.data);
			l = updateCache(l, t.address, t.data);
			return l;
		// CACHETE DE MODIFY EDÝLECEKKK!!!!!!

		case ("M"):
			isStore=true;
			modifyData("ram.txt", t.address, t.data);
			l = updateCacheForM(l, t.address, t.data);
			return l;

		}
		return l;
	}

	public static L2 setOperation(L2 l, Trace t) throws IOException {
		switch (t.operation) {
		case ("I"):
			l = LoadInstruction(l, t.address);
		
			return l;

		case ("L"):
			l = LoadDataL2(l, t.address);
		
			return l;

		case ("S"):

			l = updateCache(l, t.address, t.data);
			return l;
		// CACHETE DE MODIFY EDÝLECEKKK!!!!!!

		case ("M"):
			// modifyData("ram.txt",t.address,t.data);
			isStore=true;
			l = updateCacheForM(l, t.address, t.data);
		
			return l;

		}
		return l;
	}

	public static L1D setOperation(L1D l, Trace t) throws IOException {
		switch (t.operation) {
		/*
		 * case("I"): l=LoadInstruction(l,t.address); return l;
		 */

		case ("L"):
			l = LoadData(l, t.address);

			return l;

		case ("S"):
			// modifyData("ram.txt",t.address,t.data);
			l = updateCache(l, t.address, t.data);
			return l;
		// CACHETE DE MODIFY EDÝLECEKKK!!!!!!

		case ("M"):
			// modifyData("ram.txt",t.address,t.data);
			isStore=true;
			l = updateCacheForM(l, t.address, t.data);
			return l;

		}
		return l;
	}

	public static L1D LoadData(L1D l, String add) throws IOException {
		String[] result = goRAM(add);
		String adress = result[0];
		String data = result[1];

		int cnt = 0;
		// int setnum=0;
		int setIndex = getSetIndex(adress);
		
		// System.out.println(setIndex);
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String forTag="";
		forTag+=fark;
		int realTag=getTag(forTag);
		for (int j = 0; j < l.numberOfSet[setIndex].line.length; j++) {
			if (l.numberOfSet[setIndex].line[j].adres == fark) {
				System.out.print("L1D hit, ");
				forl1D += "store in L1D";
				L1D.HitsCounter++;
				cnt++;
				break;
			}
			if (l.numberOfSet[setIndex].line[j].getData() == null) {
				forl1D += "L1D";
				System.out.print("L1D miss, ");
              L1D.MissCounter++;
				
				
				cnt++;
				timeL1++;
				
				l.numberOfSet[setIndex].line[j].adres = fark;
				l.numberOfSet[setIndex].line[j].setV(true);
				l.numberOfSet[setIndex].line[j].setTag(getTag(add));
				l.numberOfSet[setIndex].line[j].setData(data);
				l.numberOfSet[setIndex].line[j].setTime(timeL1);
				if (l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].getTime() > l.numberOfSet[setIndex].line[j].getTime()) {
					l.numberOfSet[setIndex].minLineL1D = l.numberOfSet[setIndex].line[j].index;
					
					// l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setTime(a);
					// l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setV(true);
					// l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setTag(getTag(adress));

				}
				break;
			}
		}
		// System.out.println(l.numberOfSet[setIndex].minLineL1I);
		if (cnt == 0) { // evictionnnn !!!!
			System.out.print("L1D miss, ");
			int index = 0;
			L1D.MissCounter++;
			int t = 0 ;
			int timer=0;
			for (int b = 0; b < l.numberOfSet[setIndex].line.length; b++) {
				if(timer==0) {
				if(l.numberOfSet[setIndex].line[b].getTime()!=l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].getTime()) {
					t=l.numberOfSet[setIndex].line[b].getTime();
					index=l.numberOfSet[setIndex].line[b].index;
					timer++;
				}
				}
				else if(l.numberOfSet[setIndex].line[b].getTime()!=l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].getTime()){
					if(l.numberOfSet[setIndex].line[b].getTime()<t) {
						t=l.numberOfSet[setIndex].line[b].getTime();
						index=l.numberOfSet[setIndex].line[b].index;
					}
					
					
					
				}
				}
			
             
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].adres = fark;
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].setTag(getTag(add));
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].setData(data);
			timeL1++;
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1D].setTime(timeL1);
			evictionTimeL1D++;
			l.numberOfSet[setIndex].minLineL1D = index;

		}

		return l;

	}

	public static L2 LoadDataL2(L2 l2, String add) throws IOException {
		String[] result = goRAM(add);
		String adress = result[0];
		String data = result[1];
		int cnt = 0;
		// int setnum=0;
		int setIndex = getSetIndexForL2(add);
		
		// System.out.println(setIndex);
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		
		String forTag="";
		forTag+=fark;
				
		
		
		
		int realTag=getTagForL2(forTag);
		for (int j = 0; j < l2.numberOfSet[setIndex].line.length; j++) {
			if (l2.numberOfSet[setIndex].line[j].adres == fark) {
				System.out.print("L2 hit, ");
				cnt++;
				L2.HitsCounter++;
				break;
			}
			if (l2.numberOfSet[setIndex].line[j].getData() == null) {
				System.out.println("L2 miss, ");
				L2.MissCounter++;
				cnt++;
				timeL2++;
				l2.numberOfSet[setIndex].line[j].index=j;
				l2.numberOfSet[setIndex].line[j].setTag(getTagForL2(add));
				l2.numberOfSet[setIndex].line[j].adres = fark;
				l2.numberOfSet[setIndex].line[j].setV(true);
				l2.numberOfSet[setIndex].line[j].setData(data);
				l2.numberOfSet[setIndex].line[j].setTime(timeL2);
				if (l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].getTime() > l2.numberOfSet[setIndex].line[j].getTime()) {
					l2.numberOfSet[setIndex].minLineL2 = l2.numberOfSet[setIndex].line[j].index;
				

				}
				break;
			}
		}
		// System.out.println(l.numberOfSet[setIndex].minLineL1I);
		if (cnt == 0) { // evictionnnn !!!!
			System.out.println("L2 miss");
			L2.MissCounter++;
			int index = 0;
			int t = l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].getTime() + 1;
			for (int b = 0; b < l2.numberOfSet[setIndex].line.length; b++) {
				if (l2.numberOfSet[setIndex].line[b].getTime() == t) {
					index = l2.numberOfSet[setIndex].line[b].index;
					break;

				}
			}

			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setTag(getTagForL2(add));
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].adres = fark;
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setData(data);
			timeL2++;
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setTime(timeL2);
			evictionTimeL2++;
			l2.numberOfSet[setIndex].minLineL2 = index;

		}

			if(isStore==false) {
			
			
			System.out.println( "Place  in  L2 set, " +setIndex+ ", L1D");}

		
		return l2;

	}

	public static L1I LoadInstruction(L1I l, String add) throws IOException {

		String[] result = goRAM(add);
		String adress = result[0];
		String data = result[1];
		int cnt = 0;
		int setIndex = getSetIndex(add);
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		int fark = decadd - reamainder;
		String forTag="";
		forTag+=fark;
				
		
		int realTag=getTag(forTag);
		for (int j = 0; j < l.numberOfSet[setIndex].line.length; j++) {
			if (l.numberOfSet[setIndex].line[j].adres == fark) { // her seti deðil set indexinin setini dolaþacakkkk???
				System.out.print("L1I hit, ");
				L1I.HitsCounter++;
				cnt++;
				break;
			}

			if (l.numberOfSet[setIndex].line[j].getData() == null) {
				L1I.MissCounter++;
				
				System.out.print("L1I miss, ");
				forl1 += "L1I";
				cnt++;
				timeL1++;
				l.numberOfSet[setIndex].line[j].index=j;
				l.numberOfSet[setIndex].line[j].setTag(getTag(add));
				l.numberOfSet[setIndex].line[j].adres = fark;
				l.numberOfSet[setIndex].line[j].setV(true);
				l.numberOfSet[setIndex].line[j].setData(data);
				l.numberOfSet[setIndex].line[j].setTime(timeL1);
				if (l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].getTime() > l.numberOfSet[setIndex].line[j].getTime()) {
					l.numberOfSet[setIndex].minLineL1I = l.numberOfSet[setIndex].line[j].index;

				}
				break;
			}
		}

		if (cnt == 0) { // evictionnnn !!!!
			System.out.print("L1I miss, ");
			int index = 0;
			L1I.MissCounter++;
			int t = 0 ;
			int timer=0;
			for (int b = 0; b < l.numberOfSet[setIndex].line.length; b++) {
				if(timer==0) {
				if(l.numberOfSet[setIndex].line[b].getTime()!=l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].getTime()) {
					t=l.numberOfSet[setIndex].line[b].getTime();
					index=l.numberOfSet[setIndex].line[b].index;
					timer++;
				}
				}
				else if(l.numberOfSet[setIndex].line[b].getTime()!=l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].getTime()) {
					if(l.numberOfSet[setIndex].line[b].getTime()<t) {
						t=l.numberOfSet[setIndex].line[b].getTime();
						index=l.numberOfSet[setIndex].line[b].index;
					}
					
					
					
				}
				}
			
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].index=l.numberOfSet[setIndex].minLineL1I;
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setTag(getTag(add));
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].adres = fark;
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setData(data);
			timeL1++;
			l.numberOfSet[setIndex].line[l.numberOfSet[setIndex].minLineL1I].setTime(timeL1);
			evictionTimeL1++;
			l.numberOfSet[setIndex].minLineL1I = index;

		}
		


		return l;

	}

	// GÝDÝP L2 YA DA YAZMASI LAZIMMM!!!!!

	public static L2 LoadInstruction(L2 l2, String add) throws IOException { // hem ý hem d için yaparr
		// System.out.println("L2 OF ADRESS"+add);
		String[] result = goRAM(add);
		String adress = result[0];
		String data = result[1];
		int cnt = 0;

		int setIndex = getSetIndexForL2(add);
		// System.out.println(setIndex);
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L2.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String forTag="";
		forTag+=fark;
				
		
		int realTag=getTag(forTag);
		for (int j = 0; j < l2.numberOfSet[setIndex].line.length; j++) {
			if (l2.numberOfSet[setIndex].line[j].adres == fark) {
				System.out.println("L2 hit");
cnt++;
L2.HitsCounter++;
break;
			}

			if (l2.numberOfSet[setIndex].line[j].getData() == null) {
				System.out.println("L2 miss");
			L2.MissCounter++;
				
				cnt++;
				timeL2++;
				l2.numberOfSet[setIndex].line[j].index=j;
				l2.numberOfSet[setIndex].line[j].setTag(getTagForL2(add));
				l2.numberOfSet[setIndex].line[j].adres = fark;
				l2.numberOfSet[setIndex].line[j].setV(true);
				l2.numberOfSet[setIndex].line[j].setData(data);
				l2.numberOfSet[setIndex].line[j].setTime(timeL2);
				if (l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2]
						.getTime() > l2.numberOfSet[setIndex].line[j].getTime()) {
					l2.numberOfSet[setIndex].minLineL2 = l2.numberOfSet[setIndex].line[j].index;

				}
				break;
			}
		}

		if (cnt == 0) { // evictionnnn !!!!
			System.out.println("L2 miss");
			L2.MissCounter++;
			int index = 0;
			
			int t = l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].getTime() + 1;
			for (int b = 0; b < l2.numberOfSet[setIndex].line.length; b++) {
				if (l2.numberOfSet[setIndex].line[b].getTime() == t) {
					index = l2.numberOfSet[setIndex].line[b].index;
					break;

				}
			}

			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setTag(getTagForL2(add));
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].adres = fark;
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setData(data);
			timeL2++;
			l2.numberOfSet[setIndex].line[l2.numberOfSet[setIndex].minLineL2].setTime(timeL2);
			evictionTimeL2++;
			l2.numberOfSet[setIndex].minLineL2 = index;

		}
	

			
		



if(isStore==false) {
			
			
			System.out.println( "Place  in  L2 set, " +setIndex+ ", L1I");}





	


		return l2;

	}

	public static void modifyData(String file, String place, String hex) throws IOException {
		String newHex = "";
		int count = 0;
		for (int i = 0; i < hex.length(); i++) {
			if (count == 2) {
				newHex += " ";
				count = 0;
				if (i == hex.length() - 1)
					break;
			}
			newHex += hex.charAt(i);

			count++;

		}
		// System.out.println("newHex "+newHex);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(3 * hexadecimalToDecimal(place)); // BU YANLIÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞÞ
		raf.write(newHex.getBytes());
		raf.close();

	}

	public static int[] decimalToBinary(int Decimal) {

		// counter for binary array
		int counter = 0;
		int number = Decimal;
		while (number > 0) {
			number = number / 2;
			counter++;
		}
		int size = 0;
		if (counter > (L1I.b + L1I.s)) {
			size = counter;
		} else {
			size = (L1I.b + L1I.s) + 1;
		}
		int[] binaryNum = new int[size];
		int i = 0;
		while (Decimal > 0) {
			// storing remainder in binary array
			binaryNum[i] = Decimal % 2;
			Decimal = Decimal / 2;
			i++;

		}

		int[] newBinaryNum = new int[size];
		int j = 0;
		for (i = newBinaryNum.length - 1; i >= 0; i--) {
			newBinaryNum[j] = binaryNum[i];
			j++;

		}

		return newBinaryNum;
	}

	public static int getSetIndex(String ad) {
		// adress.substring(adress.length()-(l.b+l.s),adress.length()-(l.b+l.s)+l.s)
		int setIndexint = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(setIndexint);
		if (L1I.s == 0) {
			return 0;
		} else {
			int[] setIndex = new int[L1I.s];
			int[] blockOff = new int[L1I.b];
			int[] tag = new int[address.length - (L1I.s + L1I.b)];

			int j = 0;
			for (int i = address.length - L1I.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L1I.s + L1I.b); i < address.length - L1I.b; i++) {
				setIndex[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = 0; i < address.length - (L1I.s + L1I.b) - 1; i++) {
				tag[j] = address[i];
				j++;
			}
			String set = null;
			set = Arrays.toString(setIndex);

			String newSet = set.substring(1, set.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);
			// System.out.println("settt"+newSet);
			// System.out.println("Decimal SET INDEX"+s);

			return s;
		}
	}

	public static int getBlockOf(String ad) {
		int BlockOf = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(BlockOf);
		if (L1I.b == 0) {
			return 0;
		} else {
			int[] setIndex = new int[L1I.s];
			int[] blockOff = new int[L1I.b];
			int[] tag = new int[address.length - (L1I.s + L1I.b)];

			int j = 0;
			for (int i = address.length - L1I.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L1I.s + L1I.b); i < address.length - L1I.s; i++) {
				setIndex[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = 0; i < address.length - (L1I.s + L1I.b); i++) {
				tag[j] = address[i];
				j++;
			}
			String block = null;
			block = Arrays.toString(blockOff);

			String newSet = block.substring(1, block.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);
			return s;
		}
	}

	public static int getTag(String ad) {
		int tagg = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(tagg);

		if (address.length -L1I.b - L1I.s  == 0) {
			return 0;
		}else {
			int[] setIndex = new int[L1I.s];
			int[] blockOff = new int[L1I.b];
			int[] tag = new int[address.length - (L1I.s + L1I.b)];

			int j = 0;
			for (int i = address.length - L1I.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L1I.s + L1I.b); i < address.length - L1I.b; i++) {
				setIndex[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = 0; i < address.length - (L1I.s + L1I.b) - 1; i++) {
				tag[j] = address[i];
				j++;
			}
			String block = null;
			block = Arrays.toString(tag);

			String newSet = block.substring(1, block.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);

			return s;
		}
	}

	public static int getSetIndexForL2(String ad) {
		// adress.substring(adress.length()-(l.b+l.s),adress.length()-(l.b+l.s)+l.s)
		int setIndexint = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(setIndexint);
		if (L2.s == 0) {
			return 0;
		} else {
			int[] setIndex = new int[L2.s];
			int[] blockOff = new int[L2.b];
			int[] tag = new int[address.length - (L2.s + L2.b)];

			int j = 0;
			for (int i = address.length - L2.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L2.s + L2.b); i < address.length - L2.b; i++) {
				setIndex[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = 0; i < address.length - (L2.s + L2.b) - 1; i++) {
				tag[j] = address[i];
				j++;
			}
			String set = null;
			set = Arrays.toString(setIndex);

			String newSet = set.substring(1, set.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);
			// System.out.println("settt"+newSet);
			// System.out.println("Decimal SET INDEX"+s);
			// System.out.println("VALUE OF SET INDEX"+ad);

			return s;
		}
	}

	public static int getBlockOfForL2(String ad) {
		int BlockOf = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(BlockOf);
		if (L2.b == 0) {
			return 0;
		} else {
			int[] setIndex = new int[L2.s];
			int[] blockOff = new int[L2.b];
			int[] tag = new int[address.length - (L2.s + L2.b)];

			int j = 0;
			for (int i = address.length - L2.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L2.s + L2.b); i < address.length - L2.s; i++) {
				setIndex[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = 0; i < address.length - (L2.s + L2.b); i++) {
				tag[j] = address[i];
				j++;
			}
			String block = null;
			block = Arrays.toString(blockOff);

			String newSet = block.substring(1, block.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);
			return s;
		}
	}

	public static int getTagForL2(String ad) {
		int tagg = hexadecimalToDecimal(ad);
		int[] address = decimalToBinary(tagg);
		if (address.length - L2.b - L2.s  == 0) {
			return 0;
		} else {
			int[] setIndex = new int[L2.s];
			int[] blockOff = new int[L2.b];
			int[] tag = new int[address.length - (L2.s + L2.b)];

			int j = 0;
			/*for (int i = address.length - L2.s; i < address.length; i++) {
				blockOff[j] = address[i];
				j++;
			}
			j = 0;
			for (int i = address.length - (L2.s + L2.b); i < address.length - L2.s; i++) {
				setIndex[j] = address[i];
				j++;
			}*/
			j = 0;
			for (int i = 0; i < address.length - (L2.s + L2.b)-1; i++) {
				tag[j] = address[i];
				j++;
			}
			String block = null;
			block = Arrays.toString(tag);

			String newSet = block.substring(1, block.length() - 1);
			newSet = newSet.replaceAll(",", "");
			int s = Integer.parseInt(newSet.replaceAll(" ", ""), 2);

			return s;
		}

	}

	public static L1I updateCache(L1I l1, String add, String hex) {
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String newData = "";
		int count = 0;
		int sizeOfHex = 0;
		int cnt=0;
		// System.out.println("value of b"+L1I.B);
		int setIndex=getSetIndex(add);
			for (int j = 0; j < l1.getNumberOfSet()[setIndex].getLine().length; j++) {
				if (fark == l1.getNumberOfSet()[setIndex].getLine()[j].adres) {
					cnt++;
					System.out.print("L1I hit,   "); 
					L1I.HitsCounter++;
					forl1+="L1I";
					for (int z = 0; z < reamainder; z++) {

						newData += l1.getNumberOfSet()[setIndex].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += l1.getNumberOfSet()[setIndex].getLine()[j].getData().charAt(m);
					l1.getNumberOfSet()[setIndex].getLine()[j].setData(newData);
				break;}

			
		}
//if(cnt==0)
//	L1I.MissCounter++;
		return l1;
	}

	public static L1D updateCache(L1D ld, String add, String hex) {
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String newData = "";
		int count = 0;
		int sizeOfHex = 0;
		int cnt=0;
		// System.out.println("value of b"+L1I.B);
		for (int i = 0; i < ld.getNumberOfSet().length; i++) {
			for (int j = 0; j < ld.getNumberOfSet()[i].getLine().length; j++) {
				if (fark == ld.getNumberOfSet()[i].getLine()[j].adres) {
					System.out.print("L1D hit,   "); 
					L1D.HitsCounter++;
					forl1D+="L1D";
					cnt++;
					for (int z = 0; z < reamainder; z++) {

						newData += ld.getNumberOfSet()[i].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += ld.getNumberOfSet()[i].getLine()[j].getData().charAt(m);
					ld.getNumberOfSet()[i].getLine()[j].setData(newData);
				break;}

			}
		}
		if(cnt==0)
			L1D.MissCounter++;
		return ld;
	}

	public static L2 updateCache(L2 l2, String add, String hex) {
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L2.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String newData = "";
		int count = 0;
		int sizeOfHex = 0;
		int cnt=0;
		// System.out.println("value of b"+L1I.B);
		for (int i = 0; i < l2.getNumberOfSet().length; i++) {
			for (int j = 0; j < l2.getNumberOfSet()[i].getLine().length; j++) {
				if (fark == l2.getNumberOfSet()[i].getLine()[j].adres) {
					cnt++;
					System.out.println("L2 hit"); 
					forL2+="L2";
					L2.HitsCounter++;
					for (int z = 0; z < reamainder; z++) {

						newData += l2.getNumberOfSet()[i].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += l2.getNumberOfSet()[i].getLine()[j].getData().charAt(m);
					l2.getNumberOfSet()[i].getLine()[j].setData(newData);
			break;	}

			}
		}
		if(cnt==0)
			L2.MissCounter++;
		System.out.println(" Store in "+forl1D+", "+forL2+", RAM ");
		return l2;
	}

	public static L2 updateCacheForM(L2 l2, String add, String hex) throws IOException {

		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L2.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String newData = "";
		int count = 0;
		int sizeOfHex = 0;
		int isExistInTheCache = 0;
		// System.out.println("value of b"+L1I.B);
		for (int i = 0; i < l2.getNumberOfSet().length; i++) {
			for (int j = 0; j < l2.getNumberOfSet()[i].getLine().length; j++) {
				if (fark == l2.getNumberOfSet()[i].getLine()[j].adres) {
					L2.HitsCounter++;
					System.out.println("L2 hit,   "); 
					isExistInTheCache++;
					for (int z = 0; z < reamainder; z++) {

						newData += l2.getNumberOfSet()[i].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += l2.getNumberOfSet()[i].getLine()[j].getData().charAt(m);
					l2.getNumberOfSet()[i].getLine()[j].setData(newData);
			break;	}

			}
		}

		if (isExistInTheCache == 0) {
			l2 = LoadInstruction(l2, add);
			L2.HitsCounter++;

		}
		if(isl1I==true) {
			System.out.println(" Store in L1D, L1I L2, RAM ");
		}
		else
		System.out.println(" Store in L1D, L2, RAM ");
		return l2;
	}

	public static L1D updateCacheForM(L1D ld, String add, String hex) throws IOException {

		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		int isExistInCache = 0;
		String newData = "";
		int count = 0;
		int sizeOfHex = 0;
		// System.out.println("value of b"+L1I.B);
		for (int i = 0; i < ld.getNumberOfSet().length; i++) {
			for (int j = 0; j < ld.getNumberOfSet()[i].getLine().length; j++) {
				if (fark == ld.getNumberOfSet()[i].getLine()[j].adres) {
					System.out.print("L1D hit,   "); 
					L1D.HitsCounter++;
					isExistInCache++;
					for (int z = 0; z < reamainder; z++) {

						newData += ld.getNumberOfSet()[i].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += ld.getNumberOfSet()[i].getLine()[j].getData().charAt(m);
					ld.getNumberOfSet()[i].getLine()[j].setData(newData);
				break;}

			}
		}
		if (isExistInCache == 0) {
			ld = LoadData(ld, add);
			L1D.HitsCounter++;
		}
		return ld;
	}

	public static L1I updateCacheForM(L1I l1, String add, String hex) throws IOException {
		int decadd = hexadecimalToDecimal(add);
		int reamainder = decadd % L1I.B;
		// System.out.println(Line.B);
		int fark = decadd - reamainder;
		String newData = "";
		int count = 0;
		int isExistInCache = 0;
		int sizeOfHex = 0;
		// System.out.println("value of b"+L1I.B);
		for (int i = 0; i < l1.getNumberOfSet().length; i++) {
			for (int j = 0; j < l1.getNumberOfSet()[i].getLine().length; j++) {
				if (fark == l1.getNumberOfSet()[i].getLine()[j].adres) {
					isl1I=true;
					L1I.HitsCounter++;
					isExistInCache++;
					System.out.print("L1I hit,   "); 
					for (int z = 0; z < reamainder; z++) {

						newData += l1.getNumberOfSet()[i].getLine()[j].getData().charAt(z);
					}
					for (int n = 0; n < hex.length(); n++) {
						if (count == 2) {
							newData += " ";
							count = 0;
							sizeOfHex++;
							if (n == hex.length() - 1)
								break;
						}
						sizeOfHex++;
						newData += hex.charAt(n);

						count++;

					}
					for (int m = reamainder + sizeOfHex + 1; m < L1I.B * 3; m++)
						newData += l1.getNumberOfSet()[i].getLine()[j].getData().charAt(m);
					l1.getNumberOfSet()[i].getLine()[j].setData(newData);
					break;
				}

			}
		}

		return l1;
	}
public static void writeToFile(L1I l,L2 l2,L1D ld) throws FileNotFoundException {
	PrintWriter outputL1I = new PrintWriter("L1I.dat");
	PrintWriter outputL1D = new PrintWriter("L1D.dat");
	PrintWriter outputL2 = new PrintWriter("L2.dat");
	for(int i=0;i<L1I.S;i++) {
		for(int j=0; j<L1I.E;j++) {
			outputL1I.write(l.numberOfSet[i].line[j].getTag()+" "+l.numberOfSet[i].line[j].getTime()+" "+l.numberOfSet[i].line[j].isV()+" "+l.numberOfSet[i].line[j].getData() );
			outputL1I.println();
			
		}
	}
	for(int i=0;i<L1D.S;i++) {
		for(int j=0; j<L1D.E;j++) {
			outputL1D.write(ld.numberOfSet[i].line[j].getTag()+" "+ld.numberOfSet[i].line[j].getTime()+" "+ld.numberOfSet[i].line[j].isV()+" "+ld.numberOfSet[i].line[j].getData() );
			outputL1D.println();
			
		}
	}
	for(int i=0;i<L2.S;i++) {
		for(int j=0; j<L2.E;j++) {
			outputL2.write(l2.numberOfSet[i].line[j].getTag()+" "+l2.numberOfSet[i].line[j].getTime()+" "+l2.numberOfSet[i].line[j].isV()+" "+l2.numberOfSet[i].line[j].getData() );
			outputL2.println();
			
		}
	}
	outputL1I.close();
	outputL2.close();
	outputL1D.close();
	
}
}
