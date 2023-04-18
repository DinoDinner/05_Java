package com.multi.ex04.access_modifier2;

import com.multi.ex04.access_modifier.AccessModifier;

// 상속 시킬 클래스, 나중에 상속 제대로 배울 예정
public class ExtendsTestAccessModifier extends AccessModifier {
	
	public void testMethod() {
		// 상속 테스트!
		publicValue = 1;	// public OK
		protectedValue = 2;	// protected OK
//		defaultValue = 3;	// default NG!
//		privateValue = 4;	// Error!! private로 외부에서 절대 접근 불가!
	}
}
