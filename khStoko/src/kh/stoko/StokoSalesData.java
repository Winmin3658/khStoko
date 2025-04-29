package kh.stoko;

public class StokoSalesData {
	private int no;
	private String category; // 대분류 카테고리
	private String subCategory; // 중분류 카테고리
	private int price; // 가격
	private int stock; // 재고
	private String date; // 팔린 날짜
	
	public StokoSalesData() {
		this(0, null, null, 0, 0, null);
	}
	
	public StokoSalesData(int no, String category, String subCategory, int price, int stock, String date) {
		this.no = no;
		this.category = category;
		this.subCategory = subCategory;
		this.price = price;
		this.stock = stock;
		this.date = date;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "품목 [영수증 번호: " + no + ", 대분류: " + category + ", 중분류: " + subCategory + ", 금액: "
				+ price + "원, 구매 수량: " + stock + "개, 구매 날짜: " + date + "]";
	}

	
	
}
