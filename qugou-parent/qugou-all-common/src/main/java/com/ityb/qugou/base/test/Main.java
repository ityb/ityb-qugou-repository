package com.ityb.qugou.base.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static int js(String a,String b){
		int count=0;
		for(int i=0;i<a.length();i++){
			char a1=a.charAt(i);
			char b1=b.charAt(i);
			if(a1!=b1){
				count++;
			}
		}
		return count;
	}
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String a = br.readLine().trim();
			String b = br.readLine().trim();
			int sum=0;
			if(a.length()!=b.length()){
				String max=a.length()>b.length()?a:b;
				String min=a.length()>b.length()?b:a;
				String temp="";
				for (int i = 0; i < max.length(); i++) {
					if(i<=(max.length()-min.length())){
						temp=max.substring(i, (min.length()+i));
						sum+=(js(temp,min));
					}
				}
			}else{
				sum+=js(a,b);
			}
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}