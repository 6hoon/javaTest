package kh.edu.mini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DressShop {

	public static Scanner sc = new Scanner(System.in);

	public static ArrayList<User> uList = new ArrayList<User>();
	public static ArrayList<Cloth> cList = new ArrayList<Cloth>();
//	public static ArrayList<Integer> cQ = new ArrayList<Integer>();
//	public static ArrayList<Bag> bList = new ArrayList<Bag>();

	public static int clientNo = 0; // 현재 접속한 클라이언트의 시리얼 no

	public static void main(String[] args) {

		// -------------------------------------------------------
		while (true) {
			System.out.println("매장을 리셋 하시겠습니까(Y|N)");
			String reset = sc.nextLine();
			if (reset.toUpperCase().equals("Y")) {
				initialize();
				break;
			} else if (reset.toUpperCase().equals("N")) {
				break;
			} else {
				System.out.println("다시 입력하시오>> ");
			}
		}
		// -------------------------------------------------------

		loadAllFile();
		System.out.println("오신걸 환영합니다");
		startingMenu();
		// cList.stream().filter(t->t.getType().equals("pants")).forEach(t->System.out.println(t));
		System.out.println(clientNo);
		System.out.println("end");

	}// end of main

	public static void startingMenu() {
		int startMenu = 0;
		boolean flag = false;
		while (!flag) {
			System.out.print("1.회원가입 / 2.로그인>> ");
			try {
				startMenu = Integer.parseInt(sc.nextLine());
				if (startMenu < 1 || startMenu > 2) {
					System.out.println("지정된 메뉴를 입력해주세요 (1 or 2)");
				} else {
					switch (startMenu) {
					case 1:
						createAccount();
						login();
						flag = true;
						break;
					case 2:
						login();
						flag = true;
						break;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	private static void createAccount() {
		boolean flag = false;
		while (!flag) {
			System.out.print("(ID/PW/휴대폰 번호/주소)를 입력하시오>> ");
			String[] member = sc.nextLine().split("/");

			boolean isId = uList.stream().anyMatch(user -> user.getId().equals(member[0]));
			if (isId) {
				System.out.println("존재하는 아이디입니다");
			} else {
				User user = new User(member[0], member[1], member[2], member[3]);
				uList.add(user);
				saveUser(); // user.txt에 user 저장 및 userNo에 생성카운트 저장
				System.out.println("계정 생성이 완료되었습니다");
				flag = true;
			}
		} // end of while
	}// 회원가입

	public static void login() {
		boolean flag = false;
		List<User> list = new ArrayList<User>();
		while (!flag) {
			System.out.print("로그인을 위해 (ID/PW) 를 입력하시오>> ");
			String[] member = sc.nextLine().split("/");
			list = uList.stream().filter(t -> t.getId().equals(member[0]) && t.getPw().equals(member[1])).toList();
			if (list.isEmpty()) {
				System.out.println("일치하는 계정이 없습니다");
			} else {
				clientNo = list.get(0).getCount();
				System.out.println(member[0] + "님 정상적으로 로그인되셨습니다");
				System.out.println();
				flag = true;
			}
		}
		if (clientNo == -1) {
			System.out.println("관리자 전용 메뉴롤 이동합니다\n");
			adminMenu();
		} else {
			System.out.println("고객 전용 메뉴로 이동합니다\n");
			clientMenu();
		}
	}// 로그인

	public static void adminMenu() {
		int menu;
		boolean quit = false;
		while (!quit) {
			System.out.println("\t\t<<관리자 메뉴>>\t\t");
			System.out.println("\t1.유저 목록 확인\t2.관리자 정보 호출");
			System.out.println("\t3.상품 목록 확인\t4.상품 목록 수정");
			System.out.println("\t5.상품 추가\t6.종료");
			System.out.print("원하시는 메뉴를 선택해주세요>> ");
			try {
				menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
				case 1:
					showUserList();
					break;
				case 2:
					showAdminList();
					break;
				case 3:
					showClothList();
					break;
				case 4:
					modifyClothList();
					break;
				case 5:
					addCloth();
					break;
				case 6:
					saveCloth();
					System.out.println("프로그램을 종료합니다");
					quit = true;
					break;
				default:
					System.out.println("지정된 메뉴를 입력해주세요 (1~5)");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	public static void addCloth() {
		int menu;
		boolean quit = false;
		while (!quit) {
			try {
				System.out.println("=================================================================");
				System.out.println("1.상품 추가\t2.추가종료");
				menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
				case 1:
					System.out.println("[ 상품 타입 / 이름 / 가격 ] 을 입력하세요");
					System.out.println("(상품 타입은 [바지: P / 셔츠: S] 로 입력)");
					String[] str = sc.nextLine().split("/");
					if (str[0].toLowerCase().equals("p") || str[0].toLowerCase().equals("s")) {
						Cloth cloth = new Cloth(str[0], str[1], Integer.parseInt(str[2]));
						System.out.println("추가된 옷의 정보: " + cloth);
						cList.add(cloth);
					} else {
						System.out.println("타입 설정을 다시 해주세요");
					}
					break;
				case 2:
					quit = true;
					System.out.println("상품 추가를 종료합니다.");
					break;
				default:
					System.out.println("지정된 메뉴를 입력해주세요 (1 or 2)");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	public static void modifyClothList() {
		System.out.print("수정할 상품의 코드를 입력하시오>> ");
		String code = sc.nextLine();
		List<Cloth> codeCloth = cList.stream().filter(t -> t.getCode().equals(code)).toList();
		if (codeCloth.isEmpty()) {
			System.out.println("해당 상품이 존재하지 않습니다");
			System.out.println("상품 목록을 호출합니다");
			showClothList();
		} else {
			int menu;
			boolean quit = false;
			while (!quit) {
				System.out.println("=================================================================");
				System.out.println("현재 옷의 정보: " + codeCloth);
				try {
					System.out.println();
					System.out.println("[수정 메뉴] 1.이름\t2.가격\t3.수량\t4.수정종료");
					System.out.print("메뉴를 선택해주세요>> ");
					menu = Integer.parseInt(sc.nextLine());
					if (menu == 4) {
						quit = true;
						System.out.println("수정을 종료합니다");
						break;
					} else {
						System.out.print("!! 변동 사항을 입력하세요 !!>> ");
						String str = sc.nextLine();
						switch (menu) {
						case 1:
							cList.stream().filter(t -> t.getCode().equals(code)).forEach(t -> t.setName(str));
							break;
						case 2:
							cList.stream().filter(t -> t.getCode().equals(code))
									.forEach(t -> t.setPrice(Integer.parseInt(str)));
							break;
						case 3:
							cList.stream().filter(t -> t.getCode().equals(code))
									.forEach(t -> t.setStock(Integer.parseInt(str)));
							break;
						default:
							System.out.println("지정된 메뉴를 입력하시오 (1~4)");
							break;
						}
						System.out.println();
						System.out.println("\t\t\t!! 수정 완료 !!\\tt\t");
					}
				} catch (NumberFormatException e) {
					System.out.println("숫자로 입력바랍니다");
				}
			}
		}
	}

	public static void showClothList() {
		System.out.println("=================================================================");
		cList.stream().forEach(t -> System.out.println(t + "\n"));
		System.out.println("=================================================================");
	}

	public static void showAdminList() {
		uList.stream().filter(t -> t.getCount() == -1).forEach(t -> System.out.println(t + "\n"));
	}

	public static void showUserList() {
		System.out.println("=================================================================");
		uList.stream().filter(t -> t.getCount() != -1).forEach(t -> System.out.println(t + "\n"));
		System.out.println("=================================================================");
	}

	public static void clientMenu() {
		int menu;
		boolean quit = false;
		while (!quit) {
			System.out.println("=================================================================");
			System.out.println("\n\t1.상품 목록보기\t2.장바구니 목록 확인하기");
			System.out.println("\t3.물품 추가\t4.물품 수정");
			System.out.println("\t5.장바구니 삭제\t6.주문하기");
			System.out.println("\t7.종료");
			System.out.print("\n원하시는 메뉴를 선택해주세요>> ");
			System.out.println();
			try {
				menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
				case 1:
					showClothToClient();
					break;
				case 2:
					checkBag();
					break;
				case 3:
					boolean flag = false;
					while (!flag) {
						System.out.println("상품 목록을 보시겠습니까? (Y|N)");
						String str = sc.nextLine();
						if (str.toUpperCase().equals("Y")) {
							showClothToClient();
							addBag();
						} else if (str.toUpperCase().equals("N")) {
							addBag();
							flag = true;
						} else {
							System.out.println("다시 입력해주세요");
						}
					}
					break;
				case 4:
					// 물품 수정 메서드 호출
					break;
				case 5:
					// 장바구니 삭제 메서드 호출
					break;
				case 6:
					// 주문하기 메서드 호출
					break;
				case 7:
					quit = true;
					System.out.println("프로그램을 종료합니다.");
					break;
				default:
					System.out.println("지정된 메뉴를 입력해주세요 (1~7)");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	public static void addBag() {

		ArrayList<Content> list = new ArrayList<Content>();
		System.out.println("추가할 상품의 코드와 수량을 입력하세요(코드/수량)>> ");
		String[] add = sc.nextLine().split("/");
		try {
			Content content = new Content(add[0], Integer.parseInt(add[1]));

			uList.stream().filter(t -> t.getCount() == clientNo).forEach(t -> t.getBag().getContent().add(content));
			uList.stream().filter(t -> t.getCount() == clientNo).forEach(t -> t.getBag().setTotalPrice());
			checkBag();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("코드와 수량을 올바르게 입력하세요");
		}
	}

	public static void checkBag() {
		uList.stream().filter(t -> t.getCount() == clientNo).forEach(t -> t.getBag().toString());
	}

	public static void showClothToClient() {
		int menu;
		System.out.println("=================================================================");
		System.out.println("보고자 하는 항목을 선택해주세요");
		System.out.print("1.모두\t2.셔츠\t3.바지");
		System.out.println();
		try {
			menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				cList.forEach(t -> System.out.println(t));
				break;
			case 2:
				cList.stream().filter(t -> t.getType().equals("shirts"))
						.forEach(t -> System.out.println(t.clothToClient()));
				break;
			case 3:
				cList.stream().filter(t -> t.getType().equals("pants"))
						.forEach(t -> System.out.println(t.clothToClient()));
				break;
			default:
				System.out.println("지정된 메뉴를 입력해주세요 (1~3)");
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
		}
	}

	public static void initialize() {
		try (ObjectOutputStream oosUser = new ObjectOutputStream(new FileOutputStream("user.txt"));
				ObjectOutputStream oosUserNo = new ObjectOutputStream(new FileOutputStream("userNo.txt"));
				ObjectOutputStream oosCloth = new ObjectOutputStream(new FileOutputStream("cloth.txt"));
				ObjectOutputStream oisCo = new ObjectOutputStream(new FileOutputStream("co.txt"));) {

			ArrayList<User> userList = new ArrayList<>();
			userList.add(new User("admin", "qwer1234", -1));
			oosUser.writeObject(userList);

			oosUserNo.writeObject(User.getuCount());

			ArrayList<Cloth> clothList = new ArrayList<>();
			for (int i = 0; i < 2; i++) {
				int pPrice = (int) (Math.random() * 401) + 100;
				clothList.add(new Cloth("p", i + 1 + "번째 바지", pPrice * 100));
				int sPrice = (int) (Math.random() * 401) + 100;
				clothList.add(new Cloth("s", i + 1 + "번째 셔츠", sPrice * 100));
			}
			oosCloth.writeObject(clothList);

			oisCo.writeObject(Cloth.getCoList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadAllFile() {
		try (ObjectInputStream oisUser = new ObjectInputStream(new FileInputStream("user.txt"));
				ObjectInputStream oisUserNo = new ObjectInputStream(new FileInputStream("userNo.txt"));
				ObjectInputStream oisCloth = new ObjectInputStream(new FileInputStream("cloth.txt"));
				ObjectInputStream oisCo = new ObjectInputStream(new FileInputStream("co.txt"));) {

			uList = (ArrayList<User>) oisUser.readObject();
			User.setuCount((int) oisUserNo.readObject());
			cList = (ArrayList<Cloth>) oisCloth.readObject();
			Cloth.setCoList((int[]) oisCo.readObject());

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser() {
		try (ObjectOutputStream oosUser = new ObjectOutputStream(new FileOutputStream("user.txt"));
				ObjectOutputStream oosUserNo = new ObjectOutputStream(new FileOutputStream("userNo.txt"));) {

			oosUser.writeObject(uList);
			oosUserNo.writeObject(User.getuCount());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveCloth() {
		try (ObjectOutputStream oosCloth = new ObjectOutputStream(new FileOutputStream("cloth.txt"));
				ObjectOutputStream oosCo = new ObjectOutputStream(new FileOutputStream("co.txt"));) {

			oosCloth.writeObject(cList);
			oosCo.writeObject(Cloth.getCoList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}