package com.xinlan.matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * 
 * @author Panyi
 *
 */
public class Demo {
	public static void main(String[] args){
		System.out.println("ÇëÊäÈë×Ö·û´®:");
		Scanner scan = new Scanner(System.in);
		String data = scan.nextLine();
		
		Map<Character,Integer> record = new HashMap<Character,Integer>();
		for(int i=0;i<data.length();i++){
			Integer number = record.get(data.charAt(i));
			if(number==null){
				record.put(data.charAt(i), 1);
			}else{
				number++;
				record.put(data.charAt(i), number);
			}
		}//end for 
		for(Object o : record.keySet()){  
			System.out.println(o+"--->"+record.get(o));  
		}  
	}
}
