package kh.stoko;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProcessManager {

	private static ProcessManager instance = new ProcessManager();

	private ProcessManager() {
		super();
	}

	public static ProcessManager getInstance() {
		return instance;
	}

	public static void stokoFileUpload(ArrayList<StokoData> stokoList) {
		FileInputStream fi = null;
		try {
			fi = new FileInputStream("res/stoko.txt");
			Scanner scan = new Scanner(fi);
			// 첫 라인 없앤다
			if (scan.hasNextLine()) {
				StokoMain.menuTitle = scan.nextLine();
			}
			// 반복문을 한 라인씩을 가져와서 => String tokens => 형변환시켜서 => StudentData 객체 => ArrayList 입력
			while (true) {
				if (!scan.hasNextLine()) {
					break;
				}
				String data = scan.nextLine();
				String[] tokens = data.split(",");

				// category,subCategory,price,stock,expirationDate
				String category = tokens[0];
				String subCategory = tokens[1];
				int price = Integer.parseInt(tokens[2]);
				int stock = Integer.parseInt(tokens[3]);
				String expirationDate = tokens[4];

				StokoData sto = new StokoData(category, subCategory, price, stock, expirationDate);
				stokoList.add(sto);
			}
			// System.out.println("파일에서 stokoList 로드가 완료되었습니다");
			scan.close();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("로드가 문제가 있어서 완료하지 못했습니다 점검 바랍니다");
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void stokoSalesFileUpload(ArrayList<StokoSalesData> stokoSalesList) {
		FileInputStream fi = null;
		try {
			fi = new FileInputStream("res/stokoSales.txt");
			Scanner scan = new Scanner(fi);
			// 첫 라인 없앤다
			if (scan.hasNextLine()) {
				StokoMain._menuTitle = scan.nextLine();
			}
			// 반복문을 한 라인씩을 가져와서 => String tokens => 형변환시켜서 => StudentData 객체 => ArrayList 입력
			while (true) {
				if (!scan.hasNextLine()) {
					break;
				}
				String data = scan.nextLine();
				String[] tokens = data.split(",");

				// no,category,subCategory,price,stock,date
				int no = Integer.parseInt(tokens[0]);
				String category = tokens[1];
				String subCategory = tokens[2];
				int price = Integer.parseInt(tokens[3]);
				int stock = Integer.parseInt(tokens[4]);
				String date = tokens[5];

				StokoSalesData stos = new StokoSalesData(no, category, subCategory, price, stock, date);
				stokoSalesList.add(stos);
			}
			// System.out.println("파일에서 stokoSalesList 로드가 완료되었습니다");
			scan.close();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("로드가 문제가 있어서 완료하지 못했습니다 점검 바랍니다");
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void stockInput(ArrayList<StokoData> stokoList) {
		Scanner scan = new Scanner(System.in);
		FileOutputStream fo = null;
		PrintStream out = null;

		Map<String, String[]> categoryMap = new HashMap<>();
		categoryMap.put("Drinks", new String[] { "Juices", "Coffee", "Tea" });
		categoryMap.put("Snacks", new String[] { "Chips", "Cookies", "Candy" });
		categoryMap.put("Food", new String[] { "Instant Food", "Frozen Food", "Sauces" });
		categoryMap.put("Ice Cream", new String[] { "Cone", "Bar", "Cup" });
		categoryMap.put("Alcohol", new String[] { "Beer", "Soju", "Wine" });

		System.out.print("어떤 종류의 상품이 입고되었나요? (Drinks, Snacks, Food, Ice Cream, Alcohol) ");
		String category = scan.nextLine();

		// subCategory 목록 보여주기
		if (categoryMap.containsKey(category)) {
			String[] subCategories = categoryMap.get(category);
			System.out.println("선택 가능한 세부 항목:");
			for (int i = 0; i < subCategories.length; i++) {
				System.out.printf("%d. %s\n", i + 1, subCategories[i]);
			}
			System.out.print("번호를 선택하세요 > ");
			int subIndex = Integer.parseInt(scan.nextLine());
			if (subIndex < 1 || subIndex > subCategories.length) {
				System.out.println("잘못된 선택입니다. 입고를 취소합니다.");
			}
			String subCategory = subCategories[subIndex - 1];

			System.out.print("얼마나 입고되었나요? ");
			int stock = Integer.parseInt(scan.nextLine());

			System.out.print("유통기한은 얼마인가요? (0000-00-00) ");
			String expirationDate = scan.nextLine();

			boolean isExist = false;

			for (StokoData item : stokoList) {
				if (item.getCategory().equals(category) && item.getSubCategory().equals(subCategory)
						&& item.getExpirationDate().equals(expirationDate)) {

					item.setStock(item.getStock() + stock);
					isExist = true;
					System.out.printf("기존 제품 %s 재고가 %d개로 누적되었습니다\n", subCategory, item.getStock());
					break;
				}
			}

			if (!isExist) {
				// 동일 제품 없을 때: 가격 확인
				int price = 0;
				boolean foundPrice = false;
				for (StokoData item : stokoList) {
					if (item.getCategory().equals(category) && item.getSubCategory().equals(subCategory)) {
						price = item.getPrice();
						foundPrice = true;
						System.out.printf("기존 가격 %d원이 자동 적용됩니다\n", price);
						break;
					}
				}
				if (!foundPrice) {
					System.out.print("상품 가격이 얼마인가요? ");
					price = Integer.parseInt(scan.nextLine());
				}

				StokoData newSto = new StokoData(category, subCategory, price, stock, expirationDate);
				stokoList.add(newSto);
				System.out.printf("%s 새로운 제품이 입고되었습니다\n", subCategory);
			}

			// 파일 저장
			try {
				fo = new FileOutputStream("res/stoko.txt");
				out = new PrintStream(fo);
				out.printf("%s", StokoMain.menuTitle);
				for (StokoData sto1 : stokoList) {
					out.printf("\n%s,%s,%d,%d,%s", sto1.getCategory(), sto1.getSubCategory(), sto1.getPrice(),
							sto1.getStock(), sto1.getExpirationDate());
				}
				System.out.println("ArrayList 내용을 파일에 저장 완료되었습니다");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
				try {
					if (fo != null)
						fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("존재하지 않는 카테고리입니다. 입고를 취소합니다.");
		}
	}

	public void stockState(ArrayList<StokoData> stokoList) {
		Scanner scan = new Scanner(System.in);
		int page = 1;
		while (true) {
			int totalPage = stokoList.size() / 5;
			int remainPage = stokoList.size() % 5;
			if (remainPage != 0) {
				totalPage += 1;
			}

			int start = 5 * (page - 1);
			int stop = 5 * (page - 1) + 5;

			if (page == totalPage && remainPage != 0) {
				stop = 5 * (page - 1) + remainPage;
			}

			System.out.printf("전체 %d 페이지, 현재 %d 페이지 \n", totalPage, page);
			for (int i = start; i < stop; i++) {
				System.out.println(stokoList.get(i).toString());
			}
			System.out.print("페이지 선택(나가려면 -1 입력) > ");
			page = Integer.parseInt(scan.nextLine());
			if (page == -1) {
				System.out.println("재고 현황 출력을 종료합니다");
				break;
			}
		}

	}

	public void stockSearch(ArrayList<StokoData> stokoList) {
		Scanner scan = new Scanner(System.in);
		System.out.print("검색할 제품을 입력하시오 > ");
		String subCategory = scan.nextLine();
		boolean searchFlag = false;
		for (StokoData data : stokoList) {
			if (data.getSubCategory().equals(subCategory)) {
				System.out.println(data);
				searchFlag = true;
			}
		}
		if (!searchFlag) {
			System.out.printf("%s 제품은 찾을 수 없습니다 \n", subCategory);
		}
	}

	public void stockSort(ArrayList<StokoData> stokoList) {
		Scanner scan = new Scanner(System.in);
		stokoList.sort(Comparator.comparing(StokoData::getExpirationDate));

		int _page = 1;
		while (true) {
			int totalPage = stokoList.size() / 5;
			int remainPage = stokoList.size() % 5;
			if (remainPage != 0) {
				totalPage += 1;
			}

			int start = 5 * (_page - 1);
			int stop = 5 * (_page - 1) + 5;
			if (_page == totalPage && remainPage != 0) {
				stop = 5 * (_page - 1) + remainPage;
			}

			System.out.printf("\n전체 %d 페이지 중 현재 %d 페이지 \n", totalPage, _page);
			for (int i = start; i < stop && i < stokoList.size(); i++) {
				System.out.println(stokoList.get(i).toString());
			}
			System.out.println("----------------------------------");
			System.out.print("다음 페이지: 번호 입력 / 종료: -1 입력 > ");
			try {
				_page = Integer.parseInt(scan.nextLine());
				if (_page == -1) {
					System.out.println("재고 현황 출력을 종료합니다");
					break;
				}
				if (_page < 1 || _page > totalPage) {
					System.out.println("유효한 페이지 번호를 입력하세요 (1 ~ " + totalPage + ")");
					_page = 1; // 초기화
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요");
				_page = 1;
			}
		}
		stokoFileUpload(stokoList);
	}

	public void salesPrint(ArrayList<StokoSalesData> stokoSalesList) {
		Scanner scan = new Scanner(System.in);
		if (!stokoSalesList.isEmpty()) {
			StokoSalesData lastData = stokoSalesList.get(stokoSalesList.size() - 1);
			int lastNo = lastData.getNo();
			System.out.printf("출력할 영수증 번호 선택(현재 %d): ", lastNo);
		} else {
			System.out.println("리스트가 비어 있습니다.");
		}

		int no = Integer.parseInt(scan.nextLine());
		boolean searchFlag = false;
		int total = 0; // 총액 저장 변수

		System.out.println("=============== 영수증 ===============");
		for (StokoSalesData data : stokoSalesList) {
			if (data.getNo() == no) {
				System.out.println(data);
				total += data.getPrice() * data.getStock();
				searchFlag = true;
			}
		}
		if (searchFlag) {
			System.out.println("--------------------------------------");
			System.out.printf("총 금액: %d원\n", total);
			System.out.println("======================================");
		} else {
			System.out.printf("%d번은 찾을 수 없습니다.\n", no);
		}
	}

	public void salesState(ArrayList<StokoSalesData> stokoSalesList) {
		Scanner scan = new Scanner(System.in);
		int page = 1;

		// 전체 매출 미리 계산
		int totalSalesAmount = 0;
		for (StokoSalesData data : stokoSalesList) {
			totalSalesAmount += data.getPrice() * data.getStock();
		}

		while (true) {
			int totalPage = stokoSalesList.size() / 5;
			int remainPage = stokoSalesList.size() % 5;
			if (remainPage != 0) {
				totalPage += 1;
			}

			int start = 5 * (page - 1);
			int stop = 5 * (page - 1) + 5;

			if (page == totalPage && remainPage != 0) {
				stop = 5 * (page - 1) + remainPage;
			}

			System.out.printf("전체 %d 페이지, 현재 %d 페이지 \n", totalPage, page);
			System.out.println("=============== 매출표 ===============");
			for (int i = start; i < stop && i < stokoSalesList.size(); i++) {
				StokoSalesData data = stokoSalesList.get(i);
				System.out.println(data.toString());
			}
			System.out.println("======================================");
			System.out.print("페이지 선택(나가려면 -1 입력) > ");
			page = Integer.parseInt(scan.nextLine());
			if (page == -1) {
				System.out.println("--------------------------------------");
				System.out.printf("전체 총 매출: %,d원\n", totalSalesAmount);
				System.out.println("매출 영수증 출력을 종료합니다");
				break;
			} // 이 부분
		}

	}

	public void customerPurchase(ArrayList<StokoData> stokoList, ArrayList<StokoSalesData> stokoSalesList) {
		Scanner scan = new Scanner(System.in);
		FileOutputStream fo = null;
		PrintStream out = null;

		Map<String, String[]> categoryMap = new HashMap<>();
		categoryMap.put("Drinks", new String[] { "Juices", "Coffee", "Tea" });
		categoryMap.put("Snacks", new String[] { "Chips", "Cookies", "Candy" });
		categoryMap.put("Food", new String[] { "Instant Food", "Frozen Food", "Sauces" });
		categoryMap.put("Ice Cream", new String[] { "Cone", "Bar", "Cup" });
		categoryMap.put("Alcohol", new String[] { "Beer", "Soju", "Wine" });

		StokoSalesData lastData = stokoSalesList.get(stokoSalesList.size() - 1);
		int lastNo = lastData.getNo();
		int no = lastNo + 1;
		boolean continuePurchase = true;

		do {
			System.out.print("구매할 제품의 종류 (Drinks, Snacks, Food, Ice Cream, Alcohol) > ");
			String category = scan.nextLine();
			if (categoryMap.containsKey(category)) {
				String[] subCategories = categoryMap.get(category);
				System.out.println("선택 가능한 세부 제품:");
				for (String sub : subCategories) {
					System.out.println("- " + sub);
				}
			} else {
				System.out.println("잘못된 카테고리입니다. 구매를 종료합니다.");
				break;
			}

			System.out.print("구매할 제품 > ");
			String subCategory = scan.nextLine();

			int currentStock = -1; // 기본값 (-1이면 못 찾았다는 의미)
			boolean productFound = false;

			System.out.printf("현재 '%s' 제품의 등록된 금액은 다음과 같습니다:\n", subCategory);
			for (StokoData data : stokoList) {
				if (data.getSubCategory().equals(subCategory)) {
					productFound = true;
					System.out.printf("- 카테고리: %s, 가격: %d원, 재고: %d개\n", data.getCategory(), data.getPrice(),
							data.getStock());
					if (data.getCategory().equals(category)) {
						currentStock = data.getStock();
					}
					// 결과 출력
					if (productFound && currentStock == -1) {
						System.out.println("해당 제품을 찾을 수 없습니다.");
					}
				}
			}

			int price = -1;
			for (StokoData data : stokoList) {
				if (data.getCategory().equals(category) && data.getSubCategory().equals(subCategory)) {
					price = data.getPrice();
					currentStock = data.getStock(); // 재고도 자동 설정
					break;
				}
			}

			if (price == -1) {
				System.out.println("해당 제품 정보를 찾을 수 없습니다. 구매를 진행할 수 없습니다.");
				break;
			}
			System.out.print("구매할 제품 수량 > ");
			int stock = Integer.parseInt(scan.nextLine());
			System.out.print("구매한 날짜 (0000-00-00) > ");
			String date = scan.nextLine();
			StokoSalesData stos = new StokoSalesData(no, category, subCategory, price, stock, date);
			stokoSalesList.add(stos);

			System.out.printf("%s 제품이 구매되었습니다 \n", subCategory);

			// 선입선출 방식 재고 차감
			int remainingStock = stock;
			List<StokoData> matchingItems = new ArrayList<>();
			for (StokoData data : stokoList) {
				if (data.getCategory().equals(category) && data.getSubCategory().equals(subCategory)) {
					matchingItems.add(data);
				}
			}

			if (matchingItems.isEmpty()) {
				System.out.println("구매하려는 제품이 Stoko Mart에 없습니다.");
				break;
			}

			// 유통기한 기준 오름차순 정렬 (선입선출)
			matchingItems.sort(Comparator.comparing(StokoData::getExpirationDate));

			for (Iterator<StokoData> iterator = stokoList.iterator(); iterator.hasNext();) {
				StokoData data = iterator.next();
				if (data.getCategory().equals(category) && data.getSubCategory().equals(subCategory)) {
					if (remainingStock == 0)
						break;

					int itemStock = data.getStock();
					if (itemStock <= remainingStock) {
						remainingStock -= itemStock;
						System.out.printf("%s (%s) 재고 %d개 모두 소진됨. 항목 삭제됩니다.\n", subCategory, data.getExpirationDate(),
								itemStock);
						iterator.remove(); // 재고 0 되면 제거
					} else {
						data.setStock(itemStock - remainingStock);
						System.out.printf("%s (%s) 제품의 재고가 %d개로 변경되었습니다.\n", subCategory, data.getExpirationDate(),
								data.getStock());
						remainingStock = 0;
					}
				}
			}

			if (remainingStock > 0) {
				System.out.println("재고 수량이 부족합니다! 구매 불가!");
				stokoSalesList.remove(stos); // 방금 추가한 구매 항목 제거
				break;
			}

			System.out.print("제품을 더 구매하시겠습니까? (y/n) > ");
			String answer = scan.nextLine();
			if (!answer.equalsIgnoreCase("y")) {
				continuePurchase = false;
			}
		} while (continuePurchase);

		// 파일 저장
		try {
			// stoko.txt 저장
			fo = new FileOutputStream("res/stoko.txt");
			out = new PrintStream(fo);

			out.printf("%s", StokoMain.menuTitle);
			for (StokoData sto1 : stokoList) {
				out.printf("\n%s,%s,%d,%d,%s", sto1.getCategory(), sto1.getSubCategory(), sto1.getPrice(),
						sto1.getStock(), sto1.getExpirationDate());
			}

			// stokoSales.txt 저장
			FileOutputStream _fo = new FileOutputStream("res/stokoSales.txt");
			PrintStream _out = new PrintStream(_fo);

			_out.printf("%s", StokoMain._menuTitle);
			for (StokoSalesData _sto1 : stokoSalesList) {
				_out.printf("\n%d,%s,%s,%d,%d,%s", _sto1.getNo(), _sto1.getCategory(), _sto1.getSubCategory(),
						_sto1.getPrice(), _sto1.getStock(), _sto1.getDate());
			}
			_out.close();
			_fo.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (out != null)
				out.close();
			try {
				if (fo != null)
					fo.close();
			} catch (IOException e) {
			}
		}

		System.out.println("구매 완료되었습니다");

	}

	public void customerPrint(ArrayList<StokoSalesData> stokoSalesList) {
		Scanner scan = new Scanner(System.in);

		if (!stokoSalesList.isEmpty()) {
			StokoSalesData _lastData = stokoSalesList.get(stokoSalesList.size() - 1);
			int _lastNo = _lastData.getNo();
			System.out.printf("출력할 영수증 번호 선택(현재 %d): ", _lastNo);
		} else {
			System.out.println("리스트가 비어 있습니다.");
		}

		int _no = Integer.parseInt(scan.nextLine());
		boolean searchFlag = false;
		int totalAmount = 0;

		System.out.println("=============== 영수증 ===============");
		for (StokoSalesData data : stokoSalesList) {
			if (data.getNo() == _no) {
				System.out.println(data);
				int amount = data.getPrice() * data.getStock();
				totalAmount += amount;
				searchFlag = true;
			}
		}
		System.out.println("=====================================");

		if (searchFlag) {
			System.out.printf("총 금액: %,d원\n", totalAmount);
		} else {
			System.out.printf("%d번은 찾을 수 없습니다 \n", _no);
		}

	}

	public void customerRefund(ArrayList<StokoData> stokoList, ArrayList<StokoSalesData> stokoSalesList) {
		Scanner scan = new Scanner(System.in);
		
		if (stokoSalesList.isEmpty()) {
			System.out.println("리스트가 비어 있습니다.");
		}

		StokoSalesData _lastData = stokoSalesList.get(stokoSalesList.size() - 1);
		int _lastNo = _lastData.getNo();
		System.out.printf("환불할 영수증 번호 선택(현재 %d): ", _lastNo);
		int no1 = Integer.parseInt(scan.nextLine());

		boolean removeFlag = false;

		// 반복하면서 지울 요소를 임시 리스트에 담음
		List<StokoSalesData> toRemove = new ArrayList<>();

		for (StokoSalesData _stos : stokoSalesList) {
			if (_stos.getNo() == no1) {
				// 재고 복구 처리
				for (StokoData data : stokoList) {
					if (data.getCategory().equals(_stos.getCategory())
							&& data.getSubCategory().equals(_stos.getSubCategory())) {
						data.setStock(data.getStock() + _stos.getStock()); // 재고 복원
						break;
					}
				}
				toRemove.add(_stos);
				removeFlag = true;
			}
		}

		// 실제 삭제 처리
		stokoSalesList.removeAll(toRemove);

		if (removeFlag) {
			System.out.printf("영수증 %d번에 해당하는 구매가 모두 환불되었고 재고가 복원되었습니다.\n", no1);
		} else {
			System.out.printf("영수증 %d번이 없습니다\n", no1);
		}

		// 파일 저장 처리
		try {
			// stoko.txt 저장
			FileOutputStream fo = new FileOutputStream("res/stoko.txt");
			PrintStream out = new PrintStream(fo);
			out.printf("%s", StokoMain.menuTitle);
			for (StokoData sto1 : stokoList) {
				out.printf("\n%s,%s,%d,%d,%s", sto1.getCategory(), sto1.getSubCategory(),
						sto1.getPrice(), sto1.getStock(), sto1.getExpirationDate());
			}
			out.close();
			fo.close();

			// stokoSales.txt 저장
			FileOutputStream _fo = new FileOutputStream("res/stokoSales.txt");
			PrintStream _out = new PrintStream(_fo);
			_out.println(StokoMain._menuTitle); // 헤더 한 줄 저장
			for (StokoSalesData sd : stokoSalesList) {
				_out.printf("%d,%s,%s,%d,%d,%s\n", sd.getNo(), sd.getCategory(), sd.getSubCategory(),
						sd.getPrice(), sd.getStock(), sd.getDate());
			}
			_out.close();
			_fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
