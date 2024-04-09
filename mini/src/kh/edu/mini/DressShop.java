package kh.edu.mini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DressShop {

	public static Scanner sc = new Scanner(System.in);
	public static ArrayList<User> uList = new ArrayList<User>();
	public static ArrayList<Cloth> cList = new ArrayList<Cloth>();
	public static int clientNo = 0;

	public static void main(String[] args) {

		while (true) {
			System.out.println("매장을 리셋 하시겠습니까(Y|N)");
			String reset = sc.nextLine();
			if (reset.toLowerCase().equals("y")) {
				initialize();
				break;
			} else if (reset.toLowerCase().equals("n")) {
				break;
			} else {
				System.out.println("다시 입력하시오>> ");
			}
		}

		loadAllFile();
		System.out.println("오신걸 환영합니다");

		startingMenu();
		uList.stream().forEach(t -> System.out.println(t));
		cList.stream().forEach(t -> System.out.println(t));
//		cList.stream().filter(t->t.getType().equals("pants")).forEach(t->System.out.println(t));

		System.out.println("end");
	}// end of main

	private static void startingMenu() {
		System.out.print("1.회원가입 / 2.로그인>> ");
		int startMenu = sc.nextInt();
		sc.nextLine();
		switch (startMenu) {
		case 1:
			createAccount();
			login();
			break;
		case 2:
			login();
			break;
		}
	}// 시작

	private static void createAccount() {
		boolean flag = false;
		while (!flag) {
			System.out.print("(ID/PW/휴대폰 번호/주소)를 입력하시오>> ");
			String[] member = sc.next().split("/");

			List<User> list = uList.stream().filter(t -> t.getId().equals(member[0])).toList();
			if (!list.isEmpty()) {
				System.out.println("존재하는 아이디입니다");
			} else {
				User user = new User(member[0], member[1], member[2], member[3]);
				uList.add(user);
				saveUser();
				System.out.println("계정 생성이 완료되었습니다");
				flag = true;
			}
		} // end of while
	}// 회원가입

	private static void login() {
		boolean flag = false;
		List<User> list = new ArrayList<User>();
		while (!flag) {
			System.out.print("로그인을 위해 (ID/PW) 를 입력하시오>> ");
			String[] member = sc.next().split("/");
			int i = 0;
			list = uList.stream().filter(t -> t.getId().equals(member[0]) && t.getPw().equals(member[1])).toList();
			list.forEach(t -> clientNo = t.getCount());
			if (list.isEmpty()) {
				System.out.println("일치하는 계정이 없습니다");
			} else {
				System.out.println(member[0] + "님 정상적으로 로그인되셨습니다");
				flag = true;
			}
		}
		if (clientNo == 0) {
//			adminMenu();
		} else {
			clientMenu();
		}

	}// 로그인

	public static void clientMenu() {
		boolean quit = false;
		while (!quit) {
			System.out.println("원하시는 메뉴를 선택해주세요>>");
			System.out.print("1.상품 목록보기 2.장바구니 목록 확인하기 3.물품 추가 4.물품 수정 5.장바구니 삭제 6.주문하기 7.종료");
			int menu = sc.nextInt();
			sc.nextLine();
			if (menu < 1 || menu > 7) {
				System.out.println("지정된 메뉴를 입력해주세요");
			} else {
				switch (menu) {
				case 1:

					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					quit = true;
					break;
				}
			}

		}

	}// 고객 메뉴

	public static void initialize() {
		ObjectOutputStream oos = null;
		ArrayList<User> userList = new ArrayList<User>();
		ArrayList<Cloth> clothList = new ArrayList<Cloth>();
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File("user.txt")));
			userList.add(new User("admin", "qwer1234", 0)); // admin
			oos.writeObject(userList);

			oos = new ObjectOutputStream(new FileOutputStream(new File("cloth.txt")));
			for (int i = 0; i < 10; i++) {
				int pPrice = (int) (Math.random() * 401) + 100;
				clothList.add(new Cloth("pants", i + 1 + "번째 바지", pPrice * 100));
				int sPrice = (int) (Math.random() * 401) + 100;
				clothList.add(new Cloth("shirts", i + 1 + "번째 셔츠", sPrice * 100));
			}
			oos.writeObject(clothList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// 초기화

	public static void loadAllFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File("user.txt")));
			uList = (ArrayList<User>) ois.readObject();

			ois = new ObjectInputStream(new FileInputStream(new File("cloth.txt")));
			cList = (ArrayList<Cloth>) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}// cloth.txt 에서 cList로 로드

	public static void saveUser() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File("user.txt")));
			oos.writeObject(uList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// 초기화

}