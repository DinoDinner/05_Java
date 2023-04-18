package com.multi.ex02.test_inherit;

public class Run {

	public static void main(String[] args) {
		System.out.println("SuperClass 생성");
		new SuperClass();
		System.out.println("SuperClass 생성 끝!");
		
		System.out.println("ChildClass 생성");
		new ChildClass().publicMethod();
		// 오버라이딩 전 : SuperClass의 publicMethod 입니다.
		// 오버라이딩 후 : ChildClass에서 오버라이드 된 publicMethod입니다.
		System.out.println("ChildClass 생성 끝!");
	}
	
}
