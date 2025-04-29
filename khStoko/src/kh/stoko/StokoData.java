package kh.stoko;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class StokoData implements Serializable, Comparable {
	private String category; // 대분류 카테고리
	private String subCategory; // 중분류 카테고리
	private int price; // 가격
	private int stock; // 재고
	private String expirationDate; // 유통기한
	
	public StokoData() {
		this(null, null, 0, 0, null);
	}

	public StokoData(String category, String subCategory, int price, int stock, String expirationDate) {
		super();
		this.category = category;
		this.subCategory = subCategory;
		this.price = price;
		this.stock = stock;
		this.expirationDate = expirationDate;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "품목 [대분류: " + category + ", 중분류: " + subCategory + ", 금액: " + price + "원, 재고: "
				+ stock + "개, 유통기한: " + expirationDate + "까지]";
	}
	
	@Override
	public int compareTo(Object object) {
	    if (object instanceof StokoData) {
	    	StokoData sto = (StokoData) object;

	        // 문자열을 LocalDate로 변환해서 비교 (String 타입이면 추천)
	        LocalDate thisDate = LocalDate.parse(this.expirationDate);
	        LocalDate otherDate = LocalDate.parse(sto.expirationDate);

	        return thisDate.compareTo(otherDate); // 날짜 비교
	    }
	    return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StokoData)) {
			return false;
		}
		StokoData stokoData = (StokoData)obj;
		
		return true;
	}

	
}
