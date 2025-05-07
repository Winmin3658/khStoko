# Stoko 재고/매출 관리 시스템

Java 기반의 콘솔 프로그램으로, **재고 관리**와 **매출 관리**를 지원하는 간단한 시스템입니다. **관리자**와 **손님** 모드로 나뉘어 사용자가 다양한 기능을 수행할 수 있도록 구성되어 있습니다.

## 📌 기능 소개

### 🔐 관리자 모드
- **재고 관리**
  - 재고 입고 등록
  - 재고 현황 확인 (페이징 처리 지원)
  - 재고 검색
  - 선입선출(FIFO) 정렬
- **매출 관리**
  - 영수증 출력
  - 매출 현황 보기

### 👤 손님 모드
- 상품 구매
- 영수증 요청
- 환불 처리

## 🗂️ 프로젝트 구조
- `StokoMain.java`: 프로그램 실행의 진입점이며, 전체적인 메뉴 흐름을 담당
- `ProcessManager.java`: 비즈니스 로직이 포함된 싱글턴 객체
- `StokoData.java`, `StokoSalesData.java`: 재고 및 매출 데이터를 담는 모델 클래스

![image](https://github.com/user-attachments/assets/d6e26fe0-55c0-4b3a-ba8e-7ed505862f97)

![image](https://github.com/user-attachments/assets/95f4e6f4-7fb7-4576-aefa-94504b8bdd7a)


## 🔧 사용 방법
1. Java 환경에서 프로젝트 실행
2. 콘솔에 출력되는 메뉴를 통해 관리자 또는 손님 모드를 선택
3. 숫자 입력 및 글자 입력을 통해 기능 사용

## 📁 파일 입출력
- 프로그램 시작 시, 파일에서 기존 재고 및 매출 데이터를 불러와 `ArrayList`에 저장
- 사용자의 행동에 따라 리스트가 갱신됨

## 💡 개발 환경
- 콘솔 기반 프로그램
- 텍스트 파일을 통한 데이터 저장/로드
- 개발 OS : WINDOW 11 home
- JDK 버전 : jdk-18
- 개발 언어 : JAVA
- 개발Tool : Eclipse IDE for Java Developers – 2024-12

### 📅 개발 기간
2025.04.21 ~ 2025.04.27
