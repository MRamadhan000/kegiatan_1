import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static ArrayList<String> userStudent  = new ArrayList<>();
    static String [][] bookList = { {"5","aaa","Yagami","Death Note"}, {"4","bbb","Conan","Detective Conan"},{ "3","ccc","Harry","Harry Pother"}};
    static String [][] dataPinjam = new String[7][2];
    static int poss = 0;
    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        Admin admin = new Admin();
        String input1, input2;
        boolean isRun = true;
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            System.out.print("===== Library System =====\n1. Login as Student\n2. Login as Admin\n3. Exit\nChoose : ");
            int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    poss = 0;
                    System.out.print("Enter Your NIM (input 99 untuk back): ");
                    input1 = inputObj.next();
                    if (input1.equals("99")) {
                        break;
                    }
                    boolean isFound = false;
                    for (String x : userStudent) {
                        if (x.equals(input1)) {
                            isFound = true;
                            menuStudent(poss);
                            poss++;
                            break;
                        }
                    }
                    if (!isFound) {
                        System.out.println("INVALID NOT FOUNDS");
                    }
                    break;
                case 2:
                    System.out.print("Input Username : ");
                    input1 = inputObj.nextLine();
                    System.out.print("Input Password : ");
                    input2 = inputObj.nextLine();
                    if(input1.equals(admin.adminUsername) && input2.equals(admin.adminPassword))
                        menuAdmin();
                    else
                        System.out.println("Invalid credentials for Admin");
                    break;
                case 3:
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    public static void menuStudent(int pos){
        boolean isRun = true;
        while (isRun) {
            Student.displayBooks(bookList);
            System.out.println("===== Student Menu =====\n1. Buku Terpinjam \n2. Pinjam Buku\n3. Logout\nChoose option (1-3) : ");
            Scanner inputObj = new Scanner(System.in);
            int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    if (dataPinjam[pos][0] == null)
                        System.out.println("NO BOOKS HERE");
                    else {
                        for(int i = 0 ; i < 3; i++){
                            if(bookList[i][1].equals(dataPinjam[pos][1])){
                                System.out.println(bookList[i][0] + " = " + bookList[i][1] + " = " + bookList[i][2] + " = " + bookList[i][3] + "======\n");

                            }
                        }

                    }
                    break;
                case 2:
                    System.out.print("Enter book id : ");
                    String inputId = inputObj.nextLine();
                    for(int i = 0 ; i < 3 ;i++){
                        if(bookList[i][1].equals(inputId)){
                            int pos2 = Integer.parseInt(bookList[i][0]);
                            if(pos2 > 0){
                                pos2-=1;
                                bookList[i][0] = String.valueOf(pos2);
                                dataPinjam[pos][0] = "1";
                                dataPinjam[pos][1] = bookList[i][1];
                                System.out.println("SUCCESS BORROW BOOKS");
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    Student.logout();
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    public static void menuAdmin(){
        boolean isRun = true;
        Admin admin = new Admin();
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
        System.out.print("===== Student Menu =====\n1. Add Student \n2. Display Registered Student \n3. Logout\nChoose option (1-3) : ");
        int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    String name,NIM,faculty,program;
                    System.out.print("Enter student name : ");
                    name = inputObj.nextLine();
                    do {
                        System.out.print("Enter NIM : ");
                        NIM = inputObj.nextLine();
                        if (NIM.length()!= 3){
                            System.out.println("NIM MUST 15 CHRACATERS");
                        }
                    }while (NIM.length() != 3);
                    System.out.print("Enter Faculty : ");
                    faculty = inputObj.nextLine();
                    System.out.print("Enter Student Program : ");
                    program = inputObj.nextLine();
                    Student student = checkNIM(name, NIM, faculty, program, userStudent);
                    if (student != null) {
                        admin.addStudent(student);
                        userStudent.add(NIM);
                        System.out.println("Student successfully registered ");
                    } else
                        System.out.println("Student with the same NIM already exists!");
                    break;
                case 2:
                    admin.menuStudent();
                    break;
                case 3:
                    System.out.println("LOGGING OUT FROM ADMIN ACCOUNT");
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    public static Student checkNIM(String name,String NIM,String faculty,String program,ArrayList<String> userStudent){
        for(String x : userStudent){
            if(x.equals(NIM)){
                return null;
            }
        }
        return new Student(name,faculty,program);
    }


}



// import java.util.Scanner;
//public class Main {
//    public static void main(String[] args) {
//        int choose;
//        String []NIM = {"202310370311066","202310370311067","202310370311068", "202310370311069" ,"202310370311070"};
//        String [][] listUserName = { {"admin","titan","risky","joko"},{"adm","tit","ris","jok"}};
//        boolean isRun = true;
//        Scanner inputObj = new Scanner(System.in);
//        while (isRun) {
//            System.out.print("==== LIBRARY SYSTEM ====\n1. Log in as Student\n2. Login as Admin\n3. Exit\nChoose option (1-3) : ");
//            choose = inputObj.nextInt();
//            switch (choose){
//                case 1 :
//                    option1(NIM,inputObj);
//                    break;
//                case 2:
//                    option2(listUserName,inputObj);
//                    break;
//                case 3:
//                    isRun = false;
//                    break;
//                default:
//                    System.out.println("INVALID INPUT");
//            }
//        }
//    }
//    public static void option1(String []NIM, Scanner inputObj){
//        inputObj.nextLine();
//        String inputNIM;
//        boolean isValid;
//        do{
//            System.out.print("Enter your NIM : ");
//            inputNIM = inputObj.nextLine();
//            isValid = checkValid1(NIM,inputNIM);
//            if(!isValid)
//                System.out.println("User Not Found");
//        }while (!isValid);
//        System.out.println("Successfully Login as Student");
//    }
//    public static void option2(String [][] listUserName,Scanner inputObj){
//        inputObj.nextLine();
//        String inputName,inputPass;
//        boolean isValid;
//        do {
//            System.out.print("Enter your username (admin): ");
//            inputName = inputObj.nextLine();
//            System.out.print("Enter your password (admin) : ");
//            inputPass = inputObj.nextLine();
//            isValid = checkValid2(inputName,inputPass,listUserName);
//            if(!isValid)
//                System.out.println("Admin user not found");
//        }while (!isValid);
//    }
//    public static boolean checkValid1(String []NIM, String inputNIM){
//        if (inputNIM.length() != 15)
//            return false;
//
//        for (char x : inputNIM.toCharArray()){
//            if(!Character.isDigit(x))
//                return false;
//        }
//        for(String y : NIM){
//            if(y.equals(inputNIM))
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean checkValid2(String inputName, String inputPass, String[][] userPass){
//        for (int i = 0; i < userPass[0].length - 1; i++) {
//            if (userPass[0][i].equals(inputName) && userPass[1][i].equals(inputPass))
//                return true;
//        }
//        return false;
//    }
//}