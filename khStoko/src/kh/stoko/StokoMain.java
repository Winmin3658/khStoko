package kh.stoko;

import java.util.ArrayList;
import java.util.Scanner;

public class StokoMain {

	public static String menuTitle;
	public static String _menuTitle;

	public static void main(String[] args) {
		// 프로젝트 매니저 객체 선언
		ProcessManager pm = ProcessManager.getInstance();

		// 파일에 있는 데이터 저장하기 위한 ArrayList
		ArrayList<StokoData> stokoList = new ArrayList<StokoData>();
		ArrayList<StokoSalesData> stokoSalesList = new ArrayList<StokoSalesData>();

		// 파일에 있는 내용을 stokoList에 저장한다
		pm.stokoFileUpload(stokoList);
		pm.stokoSalesFileUpload(stokoSalesList);

		boolean _stopFlag = false;
		// 첫 번째 메뉴 출력
		while (!_stopFlag) {

			// 변수 선언(무한 반복문)
			boolean stopFlag = false;

			System.out.println("------- 버전 선택 ------");
			System.out.println("1. 관리자 2. 손님 3. 종료");
			System.out.println("----------------------");

			Scanner scan = new Scanner(System.in);
			System.out.print("버전 선택(1~3) > ");
			int verNo = Integer.parseInt(scan.nextLine());
			
			if (verNo == 3) {
				System.out.println("프로그램을 종료합니다.");
				_stopFlag = true;
				break;
			}

			while (!stopFlag) {
				switch (verNo) {
				// 관리자 두 번째 메뉴 출력
				case 1:
					System.out.println("--------- 관리자 메뉴 ---------");
					System.out.println("1. 재고 관리 2. 매출 관리 3. 뒤로");
					System.out.println("----------------------------");
					System.out.print("관리자 메뉴 선택(1~3) > ");
					int adminNo = Integer.parseInt(scan.nextLine());
					if (adminNo == 3) {
						stopFlag = true;
						System.out.println("관리자 프로그램을 종료합니다.");
						break;
					}
					switch (adminNo) {
					// 관리자 세 번째 메뉴 출력
					case 1: // 재고 관리 선택 시
						System.out.println("--------- 재고 관리 메뉴 ----------");
						System.out.println("1. 재고 입고 2. 재고 현황");
						System.out.println("3. 재고 검색 4. 재고 선입선출 정렬");
						System.out.println("-------------------------------");
						System.out.print("재고 메뉴 선택(1~4) > ");
						int stockNo = Integer.parseInt(scan.nextLine());

						switch (stockNo) {
						case 1: // 재고 입고 선택 시
							pm.stockInput(stokoList);
							break;

						case 2: // 재고 현황 선택 시(페이징 기법 사용)
							pm.stockState(stokoList);
							break;

						case 3: // 재고 검색 선택 시
							pm.stockSearch(stokoList);
							break;

						case 4: // 재고 정렬 선택 시
							pm.stockSort(stokoList);
							break;

						}
						break;

					case 2: // 매출 관리 선택 시
						System.out.println("---------- 매출 관리 메뉴 ---------");
						System.out.println("1. 영수증 출력 2. 매출 현황");
						System.out.println("-------------------------------");
						System.out.print("매출 메뉴 선택(1/2) > ");
						int salesNo = Integer.parseInt(scan.nextLine());
						switch (salesNo) {
						case 1: // 영수증 출력 선택 시
							pm.salesPrint(stokoSalesList);
							break;

						case 2: // 매출 현황 선택 시
							pm.salesState(stokoSalesList);
							break;
						}
						break;
					}
					break;

				case 2: // 손님 선택 시
					System.out.println("------------ 손님 메뉴 -----------");
					System.out.println("1. 구매 2. 영수증 요청 3. 환불 4. 뒤로");
					System.out.println("--------------------------------");
					System.out.print("사용자 메뉴 선택(1~4) > ");
					int customerNo = Integer.parseInt(scan.nextLine());
					if ((customerNo) == 4) {
						stopFlag = true;
						System.out.println("손님 메뉴를 종료합니다.");
						break;
					}

					switch (customerNo) {
					case 1: // 구매
						pm.customerPurchase(stokoList, stokoSalesList);
						break;

					case 2: // 영수증 요청
						pm.customerPrint(stokoSalesList);
						break;

					case 3: // 환불
						pm.customerRefund(stokoList, stokoSalesList);
					}
					break;
					
				default:
					System.out.println("번호를 잘못 입력하였습니다(1~3번)");
					stopFlag = true;
					break;
				}
			}
		}
	}

}