package com.multi.hw.member.controller;

import java.util.Arrays;

import com.multi.hw.member.model.vo.Member;

public class MemberController {
	// Controller의 책임 범위(Responsibility Scope)
	// - View로 부터 사용자의 요청을 전달 받아 요청/답에 대한 흐름을 제어하는 역할
	// - 사용자의 입력 데이터를 전달받고, Model(DB)로 부터 데이터 확인을 거치고 성공/실패 페이징을 제어
	
	// Controller(Back-end)로서 책임져야하는 부분!
	// - 현재 코드 안에서 밖에서 발생할수 있는 다양한 에러나 문제를 미리 해결해야하고, (예외 처리)
	//   View가 단순화 될수 있도록 일도 많이 해야한다!!
	//   ★★★★★ 매우 중요한 개념
	// - 이유 : View에서는 5개의 언어가 섞임으로 프로그래밍의 한계가 생김.
	
	// Controller 레벨에서 array가공하여 View에서 null 체크 안하게끔 짜는게 코드관점 유리하다.
	
	public static int MAX_SIZE = 10; // 배열의 최대 크기
	private Member[] mArray = new Member[MAX_SIZE]; // Member 배열
	private int size = 0; // 배열의 현재 크기

	
	public MemberController() {
		insertMember(new Member("test1", "홍길동", "1234", "test1@test.com", 'M', 31));
		insertMember(new Member("test2", "김길동", "4321", "test2@test.com", 'M', 32));
		insertMember(new Member("test3", "이길동", "1234", "test3@test.com", 'M', 24));
		insertMember(new Member("test4", "최길순", "4321", "test4@test.com", 'F', 27));
		insertMember(new Member("test5", "홍길동", "1234", "test1@test.com", 'M', 41));
	}

	// 멤버의 수를 돌려주는 메소드
	public int existMemberNum() {
		return size;
	}
	
	
	/**
	 * @desc id를 입력 받고, array에서 탐색하여 index로 결과를 알려주는 메소드
	 * @param id : 입력받은 id
	 * @return index를 반환, 0 이상이면 검색 성공, -1인 경우 검색 실패
	 */
	private int searchIdForIndex(String id) {
		for(int i = 0; i < size; i++) { // MAX_SIZE = null 체크 필수!
//			if(mArray[i] != null) { // MAX_SIZE = null 체크 필수!
//				continue;
//			}
			if(mArray[i].getId().equals(id)) { // 무조건 equals!!!!
				return i;
			}
		}
		return -1;
	}

	
	// inputId가 들어오면 중복여부를 판단하여 되돌려주는 메소드
	public Boolean checkId(String inputId) {
		int index = searchIdForIndex(inputId);
		
		if(index >= 0) { // id가 존재하는 경우
			return true;
		} else { // id 존재하지 않는 경우
			return false;
		}
	}
	
	public Member searchId(String id) {
		int index = searchIdForIndex(id);
		if(index >= 0) { // id가 존재하는 경우
			return mArray[index];
		} else { // id 존재하지 않는 경우
			return null;
		}
	}
 
	public Member[] searchName(String name) {
		Member[] tempArray = new Member[MAX_SIZE];
		int count = 0;
		for(int i = 0; i < size; i++) {
			if(mArray[i].getName().contains(name)) { // contains : 포함
				tempArray[count++] = mArray[i];
			}
		}
		
		return Arrays.copyOf(tempArray, count);  // 검색 크기 만큼 짤라주는 코드
		// null 체크 없어지는 코드, 단점 성능적 하락, 장점은 프론트 코드가 간결해진다.
	}

	public Member[] searchEmail(String email) {
		Member[] tempArray = new Member[MAX_SIZE];
		int count = 0;
		for(int i = 0; i < size; i++) {
			if(mArray[i].getEmail().contains(email)) {
				tempArray[count++] = mArray[i];
			}
		}
		
		return Arrays.copyOf(tempArray, count); 
	}

	// 6개 -> 인자 엄청 많다. -> 객체화 해서 받는게 유리하다.
//	public void insertMember(String id, String name, String password, String email, char gender, int age) {
	public void insertMember(Member member) {
		if(size + 1 >= MAX_SIZE) {
			MAX_SIZE = MAX_SIZE * 2; // 배열이 꽉 찬상태면 2배씩 공간을 늘리고
			mArray = Arrays.copyOf(mArray, MAX_SIZE); // 늘린 공간을 만들고 초기화 시켜주는 문장
 		}
		
		mArray[size++] = member;
	}
	
	public Boolean updatePassword(String id, String password) {
		int index = searchIdForIndex(id);
		if(index >= 0) {
			mArray[index].setPassword(password);
			return true;
		} else { 
			return false;
		}
	}

	public Boolean updateName(String id, String name) {
		int index = searchIdForIndex(id);
		if(index >= 0) {
			mArray[index].setName(name);;
			return true;
		} else { 
			return false;
		}
	}

	public Boolean updateEmail(String id, String email) {
		int index = searchIdForIndex(id);
		if(index >= 0) {
			mArray[index].setEmail(email);
			return true;
		} else { 
			return false;
		}
	}

	// insert 정책 : 배열에 들어온 순서대로 member 넣는 정책 
	// delete 정책 : 1개나 마지막은 문제없고, 중간에 삭제되는 경우는 중간에 공백이 없고 순서가 유지 되게끔 뒤에서 당긴다.
	// size = 5;
	// [홍길동][전길동][최길동][홍길동][김길동] // step0 // 전길동삭제 요청
	// [홍길동][    ][최길동][홍길동][김길동] // step1 // 중간에 삭제 완료, 그리고 하나씩 당긴다.
	// [홍길동][최길동][    ][홍길동][김길동] // step2
	// [홍길동][최길동][홍길동][    ][김길동] // step3
	// [홍길동][최길동][홍길동][김길동][    ] // step4 끝 -> size를 4로 줄인다.
	public Boolean delete(String id) {
		int index = searchIdForIndex(id);
		if(index == -1) { // 필터링
			return false; 
		}
		// 당기는 로직 시작!
		for(int i = index; i < size - 1; i++) {
			mArray[i] = mArray[i + 1];
		}
		mArray[size - 1] = null; 
		size--;
		return true;
	}

	public void delete() {
		MAX_SIZE = 10;
		size = 0;
		mArray = new Member[MAX_SIZE]; // 새로 만든다. 
	}

	static int count = 0;
	public Member[] printAll() {
		return Arrays.copyOf(mArray, size);
	}
	
	// step by step -> 한단계씩 코딩, 
	// 항상 중간에 검증 하는 습관 -> TDD
	public static void main(String[] args) {
		MemberController mc = new MemberController();
		System.out.println(Arrays.toString(mc.printAll()).replace("],", "],\n")); // print 코드
		mc.insertMember(new Member("test6","홍길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test7","홍길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test8","홍길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test9","홍길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test11","홍길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test12","이길동","1234","test1@test.com",'M',41));
		mc.insertMember(new Member("test13","홍길동","1234","test1@test.com",'M',41)); // OK.
		System.out.println("--------------------------------------------------------");
		System.out.println(Arrays.toString(mc.printAll()).replace("],", "],\n")); // print 코드
		mc.updateName("test11", "박길동"); // OK
		mc.updateEmail("test11", "test222222@test.com"); // OK
		mc.updatePassword("test11", "12222222222222"); // OK
		System.out.println("--------------------------------------------------------");
		System.out.println(Arrays.toString(mc.printAll()).replace("],", "],\n")); // print 코드
		System.out.println(mc.existMemberNum());
		mc.delete("test11");
		System.out.println("--------------------------------------------------------");
		System.out.println(Arrays.toString(mc.printAll()).replace("],", "],\n")); // print 코드
		System.out.println(mc.existMemberNum());
		System.out.println("--------------------------------------------------------");
		System.out.println(Arrays.toString(mc.searchName("홍길동")).replace("],", "],\n")); // print 코드
		System.out.println("--------------------------------------------------------");
		System.out.println(Arrays.toString(mc.searchEmail("test1")).replace("],", "],\n")); // print 코드
		System.out.println("--------------------------------------------------------");
		System.out.println(mc.checkId("test1")); // true
		System.out.println(mc.checkId("test41")); // false
	}

}
