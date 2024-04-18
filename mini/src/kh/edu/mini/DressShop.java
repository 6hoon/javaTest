package kh.edu.mini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DressShop {

	public static Scanner sc = new Scanner(System.in);

	public static ArrayList<User> uList = new ArrayList<User>();
	public static ArrayList<Cloth> cList = new ArrayList<Cloth>();
	public static ArrayList<Cloth> ccList = new ArrayList<Cloth>();

	public static int clientNo = 0; // 현재 접속한 클라이언트의 시리얼 no
	public static User client = new User();

	public static void main(String[] args) {

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

		loadAllFile();
		startingMenu();
		System.out.println("The End");

	}// end of main

	public static void startingMenu() {
		int startMenu = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.println("\s1.회원가입\t2.로그인 ");
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.print("입력>> ");
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
			System.out.println();
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.println("(ID/PW/휴대폰 번호/주소)를 입력");
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.print("입력>> ");
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
			System.out.println();
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.println("로그인을 위해 (ID/PW)를 입력");
			System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
			System.out.print("입력>> ");
			String[] member = sc.nextLine().split("/");
			list = uList.stream().filter(t -> t.getId().equals(member[0]) && t.getPw().equals(member[1])).toList();
			if (list.isEmpty()) {
				System.out.println("일치하는 계정이 없습니다");
			} else {
				clientNo = list.get(0).getCount();
				client = uList.get(clientNo);
				System.out.println(member[0] + "님 정상적으로 로그인되셨습니다");
				System.out.println();
				flag = true;
			}
		}
		if (clientNo == 0) {
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
			System.out.println("=============================================");
			System.out.println("\t\t<<관리자 메뉴>>\t\t");
			System.out.println("\t1.유저 목록 확인\t2.관리자 정보 호출");
			System.out.println("\t3.상품 목록 확인\t4.상품 목록 수정");
			System.out.println("\t5.상품 추가\t6.종료");
			System.out.println("\t7.초기화");
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
				case 7:
					adminInit();
				default:
					System.out.println("지정된 메뉴를 입력해주세요 (1~7)");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	public static void adminInit() {
	}

	public static void addCloth() {
		int menu;
		boolean quit = false;
		while (!quit) {
			try {
				System.out.println("=============================================");
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
				System.out.println("=============================================");
				System.out.println("현재 옷의 정보: " + codeCloth);
				try {
					System.out.println();
					System.out.println("[수정 메뉴] 1.이름\t2.가격\t3.수량\t4.수정종료");
					System.out.println("=============================================");
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
		System.out.println("=============================================");
		cList.stream().forEach(t -> System.out.println(t + "\n"));
		System.out.println("=============================================");
	}

	public static void showAdminList() {
		uList.stream().filter(t -> t.getCount() == 0).forEach(t -> System.out.println(t + "\n"));
	}

	public static void showUserList() {
		System.out.println("=============================================");
		uList.stream().filter(t -> t.getCount() > 0).forEach(t -> System.out.println(t + "\n"));
		System.out.println("=============================================");
	}

	public static void clientMenu() {
		int menu;
		boolean quit = false;
		while (!quit) {
			System.out.println("===================================================");
			System.out.println("\t1.상품 목록보기\t2.장바구니 목록 확인하기");
			System.out.println("\t3.물품 추가\t4.장바구니 항목 삭제");
			System.out.println("\t5.주문 하기\t6.종료");
			System.out.println("===================================================");
			System.out.print("\n원하시는 메뉴를 선택해주세요>> ");
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
							flag = true;
						} else if (str.toUpperCase().equals("N")) {
							addBag();
							flag = true;
						} else {
							System.out.println("다시 입력해주세요");
						}
					}
					break;
				case 4:
					removeBag();
					break;
				case 5:
					orderCloth();
					break;
				case 6:
					saveAll();
					System.out.println("프로그램을 종료합니다.");
					quit = true;
					break;
				default:
					System.out.println("지정된 메뉴를 입력해주세요 (1~6)");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력바랍니다");
			}
		}
	}

	public static void orderCloth() {
		System.out.println();
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
		checkBag();
		System.out.println("장바구니의 옷을 모두 구매하시겠습니까(Y|N)>> ");
		String str = sc.nextLine();
		if (str.toUpperCase().equals("Y")) {
			System.out.println("(휴대폰 번호 / 주소) 를 입력하시오");
			String[] strArr = sc.nextLine().split("/");
			try {
				boolean isUser = client.getPhone().equals(strArr[0]) && client.getAddress().equals(strArr[1]);
				if (isUser == true) {
					checkBag();
					System.out.println("주문이 완료되었습니다");
					client.setBag(new Bag());
					cList = ccList;
					saveAll();
				} else {
					System.out.println("주문자의 정보가 일치하지 않아, 주문을 종료합니다");
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
	}

	public static void removeBag() {
		System.out.println();
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
		System.out.println("제거할 상품의 코드를 입력하시오(장바구니 비우기: All)>> ");
		String str = sc.nextLine();
		if (str.toLowerCase().equals("all")) {
			client.setBag(new Bag());
			cList = ccList;
		} else {
			checkBag();
			String[] code = new String[client.getBag().getContent().size()];
			for (int i = 0; i < client.getBag().getContent().size(); i++) {
				code[i] = client.getBag().getContent().get(i).getCloth().getCode();
			}

		}
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
	}

	public static void addBag() {
		System.out.println();
		checkBag();
		System.out.println("추가할 상품의 코드와 수량을 입력하세요(코드/수량)>> ");
		String[] add = sc.nextLine().split("/");
		try {
			ArrayList<Content> list = client.getBag().getContent();
			int bagQuant = Integer.parseInt(add[1]);
			Content content = new Content(add[0], bagQuant);
			if (!cList.stream().filter(t -> t.getCode().equals(add[0])).filter(t -> t.getStock() < bagQuant).toList()
					.isEmpty()) {
				System.out.println("재고가 부족해, 상품 추가를 종료합니다");
			} else {
				boolean isInBag = !list.stream().filter(t -> t.getCloth().getCode().equals(add[0])).toList().isEmpty();
				if (isInBag) {
					client.getBag().getContent().stream().filter(t -> t.getCloth().getCode().equals(add[0]))
							.forEach(t -> t.setQuantity(t.getQuantity() + bagQuant));
					client.getBag().getContent().forEach(t -> t.calPrice());
				} else if (client.getBag().getTotalPrice() == 0) {
					list.remove(0);
					list.add(content);
					client.getBag().setContent(list);
				} else {
					list.add(content);
					client.getBag().setContent(list);
				}
				cList.stream().filter(t -> t.getCode().equals(add[0]))
						.forEach(t -> t.setStock(t.getStock() - bagQuant));
			}
			checkBag();
			saveAll();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("코드와 수량을 올바르게 입력하세요");
		}
	}

	public static void checkBag() {
		System.out.println();
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
		if (client.getBag().getTotalPrice() == 0) {
			System.out.println("장바구니가 비어있습니다");
			ccList = (ArrayList<Cloth>) cList.clone();
		} else {
			client.getBag().calTotalPrice();
			System.out.println(client.getBag().toString());
		}
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
		System.out.println();
	}

	public static void showClothToClient() {
		System.out.println();
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
		int menu;
		System.out.print("\t1.모두\t2.셔츠\t3.바지>> ");
		try {
			menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				cList.forEach(t -> System.out.println(t));
				break;
			case 2:
				cList.stream().filter(t -> t.getType().equals("shirts")).forEach(t -> System.out.println(t));
				break;
			case 3:
				cList.stream().filter(t -> t.getType().equals("pants")).forEach(t -> System.out.println(t));
				break;
			default:
				System.out.println("지정된 메뉴를 입력해주세요 (1~3)");
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
		}
		System.out.println("▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩▩");
	}

	public static void initialize() {
		try (BufferedReader brAdmin = new BufferedReader(new FileReader("admin.txt"));
				ObjectOutputStream oosUser = new ObjectOutputStream(new FileOutputStream("user.txt"));
				ObjectOutputStream oosUserNo = new ObjectOutputStream(new FileOutputStream("userNo.txt"));
				ObjectOutputStream oosCloth = new ObjectOutputStream(new FileOutputStream("cloth.txt"));
				ObjectOutputStream oosCo = new ObjectOutputStream(new FileOutputStream("co.txt"));
				ObjectOutputStream oosCcl = new ObjectOutputStream(new FileOutputStream("ccl.txt"));) {

			ArrayList<User> userList = new ArrayList<>();
			String[] admin = brAdmin.readLine().split("/");
			userList.add(new User(admin[0], admin[1]));
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
			oosCcl.writeObject(clothList);

			oosCo.writeObject(Cloth.getCoList());

			System.out.println("초기화 완료");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadAllFile() {
		try (ObjectInputStream oisUser = new ObjectInputStream(new FileInputStream("user.txt"));
				ObjectInputStream oisUserNo = new ObjectInputStream(new FileInputStream("userNo.txt"));
				ObjectInputStream oisCloth = new ObjectInputStream(new FileInputStream("cloth.txt"));
				ObjectInputStream oisCo = new ObjectInputStream(new FileInputStream("co.txt"));
				ObjectInputStream oisCcl = new ObjectInputStream(new FileInputStream("ccl.txt"));) {

			uList = (ArrayList<User>) oisUser.readObject();
			User.setuCount((int) oisUserNo.readObject());
			cList = (ArrayList<Cloth>) oisCloth.readObject();
			Cloth.setCoList((int[]) oisCo.readObject());
			ccList = (ArrayList<Cloth>) oisCcl.readObject();

			System.out.println("로드 완료");

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser() {
		try (ObjectOutputStream oosUser = new ObjectOutputStream(new FileOutputStream("user.txt"));
				ObjectOutputStream oosUserNo = new ObjectOutputStream(new FileOutputStream("userNo.txt"));) {

			uList.set(clientNo, client);
			oosUser.writeObject(uList);
			oosUserNo.writeObject(User.getuCount());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveCloth() {
		try (ObjectOutputStream oosCloth = new ObjectOutputStream(new FileOutputStream("cloth.txt"));
				ObjectOutputStream oosCo = new ObjectOutputStream(new FileOutputStream("co.txt"));
				ObjectOutputStream oosCcl = new ObjectOutputStream(new FileOutputStream("ccl.txt"));) {
			oosCcl.writeObject(ccList);
			oosCloth.writeObject(cList);
			oosCo.writeObject(Cloth.getCoList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveAll() {
		saveUser();
		saveCloth();
	}
}