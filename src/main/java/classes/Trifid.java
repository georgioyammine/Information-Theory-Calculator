package classes;

import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Trifid{
	public static void main(String[] args){
		boolean deb = true; // set true if you want to see the alphabet used and the steps
		Scanner scan = new Scanner(System.in);
		String inp;	
		do{
		System.out.println("Enter d for decode or e to encode");
		inp =scan.nextLine();
		inp = inp.toLowerCase();
		}while(!inp.equals("d")&&!inp.equals("e"));
	
		System.out.print("Enter text: ");
		String input = scan.nextLine();
		ArrayList<Integer> arrL = getSpaces(input);
		input = removeSpaces(input);
		int[][] arr = new int[3][input.length()];
		String output="";

		
		System.out.print("Enter Key or enter if no key: ");
		String key = scan.nextLine();
		key = removeSpaces(key);
		key = key.toLowerCase() +"abcdefghijklmnopqrstuvwxyz.";
	
		char[][][] cArr = new char[3][3][3];
		HashSet<Character> hashSet = new HashSet<>();
		int index=0, j=0;
		for(int h =0;h<arr.length;h++){		
			for(int i=0; i<cArr[0].length&&index<key.length();i++){
				j=0;
				while(j<cArr[0][0].length&&index<key.length()){
					if(!hashSet.contains(key.charAt(index))){
						cArr[h][i][j]=key.charAt(index);
						hashSet.add(key.charAt(index));
						j++;
					}
					index++;
				}
			}
		}	
		for(int i =0;i<input.length();i++){
			char c = input.charAt(i);
			int[] arr2 = getIndex(c, cArr);
			arr[0][i] = arr2[0];
			arr[1][i] = arr2[1];
			arr[2][i] = arr2[2];
		}
		
			
		int[][] out = new int[arr.length][arr[0].length];
	
		if(inp.equals("d")){	
			int ro=0,co=0;
			for(int i=0;i<arr.length;i++)
				for(j=0;j<arr[0].length;j++){
					out[i][j] = arr[ro][co];
					if(ro<2)
						ro++;
					else{
						ro=0;
						co++;
					}
				}
		}
		
		if(inp.equals("e")){
			int ro=0,co=0;
			for(int i = 0;i<arr.length;i++)
				for(j=0;j<arr[0].length;j++){
					out[ro][co]=arr[i][j];
					if(ro<2)
						ro++;
					else{
						ro=0;
						co++;
			
				}
			}
		}
		if(deb){
			System.out.println();
			print(cArr);
			System.out.println();
			print(arr);
			System.out.println();
			print(out);
			System.out.println();
		}
			
			
		for(j=0;j<out[0].length;j++)
			output+= cArr[out[0][j]][out[1][j]][out[2][j]];
		
		output = addSpaces(output,arrL);
		System.out.println(output);
	}
	public static TrifidResult solve(boolean encode, String input, String key){
		input = input.toUpperCase();
		boolean deb = true; // set true if you want to see the alphabet used and the steps
		TrifidResult trifidResult = new TrifidResult();
		ArrayList<Integer> arrL = getSpaces(input);
		input = removeSpaces(input);
		int[][] arr = new int[3][input.length()];
		String output="";
		key = removeSpaces(key);
		key = key.toLowerCase() +"abcdefghijklmnopqrstuvwxyz.";
		key = key.toUpperCase();

		char[][][] cArr = new char[3][3][3];
		HashSet<Character> hashSet = new HashSet<>();
		int index=0, j=0;
		for(int h =0;h<arr.length;h++){
			for(int i=0; i<cArr[0].length&&index<key.length();i++){
				j=0;
				while(j<cArr[0][0].length&&index<key.length()){
					if(!hashSet.contains(key.charAt(index))){
						cArr[h][i][j]=key.charAt(index);
						hashSet.add(key.charAt(index));
						j++;
					}
					index++;
				}
			}
		}
		for(int i =0;i<input.length();i++){
			char c = input.charAt(i);
			int[] arr2 = getIndex(c, cArr);
			arr[0][i] = arr2[0];
			arr[1][i] = arr2[1];
			arr[2][i] = arr2[2];
		}


		int[][] out = new int[arr.length][arr[0].length];

		if(!encode){
			int ro=0,co=0;
			for(int i=0;i<arr.length;i++)
				for(j=0;j<arr[0].length;j++){
					out[i][j] = arr[ro][co];
					if(ro<2)
						ro++;
					else{
						ro=0;
						co++;
					}
				}
		}
		else{
			int ro=0,co=0;
			for(int i = 0;i<arr.length;i++)
				for(j=0;j<arr[0].length;j++){
					out[ro][co]=arr[i][j];
					if(ro<2)
						ro++;
					else{
						ro=0;
						co++;

					}
				}
		}

		StringBuilder alpha = new StringBuilder();
		alpha.append("\t-\t0\t-\t\t\t-\t1\t-\t\t\t-\t2\t-\n");
		alpha.append("\t0\t1\t2\t\t\t0\t1\t2\t\t\t0\t1\t2\n");
		for(int i =0;i<cArr[0].length;i++){
			for(int h=0; h<cArr.length; h++){
				for(int k=0; k<cArr[0][0].length;k++) {
					if(k==0)
						alpha.append(i+"\t");
					alpha.append(cArr[h][i][k]).append("\t");
				}
				alpha.append("\t");
			}
			alpha.deleteCharAt(alpha.length()-1);
			alpha.deleteCharAt(alpha.length()-1);
			alpha.append("\n");
		}
		trifidResult.alphabet = alpha.toString();

		StringBuilder steps = new StringBuilder("  Input\t");
		for(int f =0;f<input.length();f++)
			steps.append(input.charAt(f)).append("\t");
		steps.deleteCharAt(steps.length()-1);
		steps.append("\n");

		for(int f =0;f<arr.length;f++){
			for(int k=0; k<arr[0].length;k++) {
				if(k==0) {
					if (f == 0)
						steps.append("  Table\t");
					if (f == 1)
						steps.append("Column\t");
					if (f == 2)
						steps.append("   Row \t");
				}
				steps.append(arr[f][k]).append("\t");
			}
			steps.deleteCharAt(steps.length()-1);
			steps.append("\n");
		}
		steps.append("\n");
		for(int f =0;f<out.length;f++){
			for(int k=0; k<out[0].length;k++) {
				if (k == 0) {
					if (f == 0)
						steps.append("  Table\t");
					if (f == 1)
						steps.append("Column\t");
					if (f == 2)
						steps.append("   Row \t");
				}
				steps.append(out[f][k]).append("\t");
			}
			steps.deleteCharAt(steps.length()-1);
			steps.append("\n");
		}


		for(j=0;j<out[0].length;j++)
			output+= cArr[out[0][j]][out[1][j]][out[2][j]];

		steps.append(" Output\t");
		for(int f =0;f<output.length();f++)
			steps.append(output.charAt(f)).append("\t");
		steps.deleteCharAt(steps.length()-1);
		trifidResult.steps = steps.toString();

		output = addSpaces(output,arrL);
		trifidResult.result = output;
		return  trifidResult;
	}
	private static int[] getIndex(char c,char[][][] arr){
		int[] index = new int[3];
		boolean found = true;
		for(int h=0;h<arr.length; h++)
			for(int i =0; i<arr[0].length && found;i++)
				for(int j =0; j<arr[0][0].length && found;j++)
					if(arr[h][i][j]==c){
						found=true;
						index[0]=h;
						index[1]=i;
						index[2]=j;
					}
		return index;
	}
	private static void print(int[][] arr){
		for(int i =0;i<arr.length;i++){
			for(int j=0; j<arr[0].length;j++)
				System.out.print(arr[i][j]+"  ");
			System.out.println();
		}
	}
	private static void print(char[][][] arr){
		for(int i =0;i<arr[0].length;i++){
			for(int h=0; h<arr.length; h++){			
				for(int j=0; j<arr[0][0].length;j++)
					System.out.print(arr[h][i][j]+"  ");
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	private static ArrayList<Integer> getSpaces(String str){
		ArrayList<Integer> arrList = new ArrayList<>();
		for(int i=0;i<str.length();i++)
			if(str.charAt(i)==' '){
				arrList.add(i);
			}
		return arrList;
	}
	private static String removeSpaces(String str){
		for(int i=0;i<str.length();i++)
			if(str.charAt(i)==' '){
				str=str.substring(0,i)+str.substring(i+1);
				
			}
		return str;
	}
	private static String addSpaces(String str, ArrayList<Integer> arrL){
		if(!arrL.isEmpty()){
			for(int i=0; i<arrL.size(); i++)
				str = str.substring(0,arrL.get(i))+" "+str.substring(arrL.get(i));
		}
		return str;
	}

	public static class TrifidResult {
		public String result;
		public String alphabet;
		public String steps;

		public TrifidResult(String result, String alphabet, String steps) {
			this.result = result;
			this.alphabet = alphabet;
			this.steps = steps;
		}
		public TrifidResult(){}
	}
}