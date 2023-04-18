package com.multi.poly.model.vo;

public class CookBook extends Book {
	private boolean coupon; // 요리학원쿠폰유무

	public CookBook() {
		super();
	}

	public CookBook(String title, String author, String publisher, boolean coupon) {
		super(title, author, publisher);
		this.coupon = coupon;
	}

	public String toString() {
		return "CookBook [title=" + getTitle() + ", author=" + getAuthor() + ", publisher=" + getPublisher() + "coupon="
				+ coupon + "]";
	}

	public boolean isCoupon() {
		return coupon;
	}

	public void setCoupon(boolean coupon) {
		this.coupon = coupon;
	}

}
