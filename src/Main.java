import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }
    static int numberBorrowed;
    public static void menu() {
        numberBorrowed = 0;
        boolean isRun = true;
        String input1;
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            System.out.print("==== Library System ====\n1. Login as Student\n2. Login as Admin\n3. Exit\nChoose option (1-3) : ");
            int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    System.out.print("Enter Your NIM (input 99 untuk back): ");
                    input1 = inputObj.next();
                    if (input1.equals("99")) {
                        break;
                    }
                    boolean isFound = false;
                    for (Student student : Admin.getStudentData()) {
                        if (student.getNIM().equals(input1)) {
                            isFound = true;
                            menuStudent(numberBorrowed, student);
                            break;
                        }
                    }
                    if (!isFound) {
                        System.out.println("INVALID NOT FOUNDS");
                    }
                    break;
                case 2:
                    Admin admin = new Admin();
                    boolean isValid = admin.isAdmin();
                    if (isValid)
                        menuAdmin();
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

    public static void menuAdmin() {
        boolean isRun = true;
        Admin admin = new Admin();
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            System.out.print("===== Admin Menu =====\n1. Add Student\n2. Add Book \n3. Display Registered Student \n4. Display Available Books\n5. Logout\nChoose option (1-5) : ");
            int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    addTempStudent();
                    break;
                case 2:
                    admin.inputBook();
                    break;
                case 3:
                    admin.displayStudent();
                    break;
                case 4:
                    admin.displayBook();
                    break;
                case 5:
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    public static void addTempStudent() {
        Admin admin = new Admin();
        Scanner inputObj = new Scanner(System.in);
        String name, NIM, faculty, program;
        System.out.print("Enter student name : ");
        name = inputObj.nextLine();
        do {
            System.out.print("Enter NIM : ");
            NIM = inputObj.nextLine();
            if (NIM.length() != 15) {
                System.out.println("NIM MUST 15 CHRACATERS");
            }
        } while (NIM.length() != 15);
        System.out.print("Enter Faculty : ");
        faculty = inputObj.nextLine();
        System.out.print("Enter Student Program : ");
        program = inputObj.nextLine();
        Student student = checkNIM(name, NIM, faculty, program);
        if (student != null) {
            admin.addStudent(student);
            System.out.println("Student successfully registered ");
        } else
            System.out.println("Student with the same NIM already exists!");
    }

    public static Student checkNIM(String name, String NIM, String faculty, String program) {
        ArrayList<Student> studentList = Admin.getStudentData();
        for (Student x : studentList) {
            if (x.getNIM().equals(NIM)) {
                return null;
            }
        }
        return new Student(name, NIM, faculty, program);
    }

    public static void menuStudent(int numberBorrowed, Student student) {
        Student.setStudentBook();
        String[] arrId = new String[10];
        int[] arrDuration = new int[10];
        boolean isRun = true;
        while (isRun) {
            student.displayBook();
            System.out.print("===== Student Menu =====\n1. Buku Terpinjam \n2. Pinjam Buku\n3. Kembalikan Buku\n4. Pinjam Buku atau Logout\nChoose option (1-3) : ");
            Scanner inputObj = new Scanner(System.in);
            int choose = inputObj.nextInt();
            inputObj.nextLine();
            switch (choose) {
                case 1:
                    if (student.getBorrowedBooks().isEmpty())
                        System.out.println("Tidak ada buku yang dipinjam");
                    else
                        student.showBorrowedBooks();
                    break;
                case 2:
                    boolean isFound = false;
                    String inputId;
                    do {
                        System.out.print("Input Id buku yang ingin dipinjam (input 99 untuk kembali)\nInput : ");
                        inputId = inputObj.nextLine();
                        if (inputId.equals("99")) {
                            break;
                        }
                        for (Book book : student.getBorrowedBooks()) {
                            if (book.getBookId().equals(inputId)) {
                                System.out.println("ANDA TIDAK BISA MEMINJAM BUKU INI KARENA TELAH DIPINJAM");
                                isFound = true;
                            }
                        }
                        if (!isFound) {
                            for (Book book : Student.getStudentBook()) {
                                if (book.getBookId().equals(inputId) && book.getStock() > 0) {
                                    isFound = true;
                                    System.out.print("Berapa lama buku akan dipinjam ? (maksimal 14 hari)\nInput lama (hari) : ");
                                    int duration = inputObj.nextInt();
                                    inputObj.nextLine();
                                    arrId[numberBorrowed] = inputId;
                                    arrDuration[numberBorrowed] = duration;
                                    numberBorrowed++;
                                    book.setStock(book.getStock() - 1);
                                }
                            }
                            if (!isFound)
                                System.out.println("Buku dengan ID + " + inputId + " tidak tersedia ");
                        }
                    } while (!inputId.equals("99"));
                    break;
                case 3:
                    isFound = false;
                    System.out.print("Masukkan Id buku yang ingin dikembalikan : ");
                    String inputAgain = inputObj.nextLine();
                    for (Book book : Admin.getBookList()) {
                        if (book.getBookId().equals(inputAgain)) {
                            isFound = true;
                            break;
                        }
                    }
                    if (isFound) {
                        student.returnBook(inputAgain);
                    } else
                        System.out.println("Buku dengan ID " + inputAgain + " tidak ditemukan");
                    break;
                case 4:
                    if (numberBorrowed > 0) {
                        student.showTempBook(arrId, numberBorrowed, arrDuration);
                        int choose2;
                        System.out.print("Apakah anda ingin meminjam buku tersebut\n1. Ya\n2. Tidak\nChoose Option : ");
                        choose2 = inputObj.nextInt();
                        inputObj.nextLine();
                        addTempBook(student, numberBorrowed, choose2, arrId, arrDuration);
                    }
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    public static void addTempBook(Student student,int numberBorrowed, int choose, String[] arrId, int[] arrDuration) {
        if (choose == 1) {
            for (int i = 0; i < numberBorrowed; i++) {
                student.acceptBookBorrowed(arrId[i], arrDuration[i]);
            }
        } else if (choose == 2 && numberBorrowed > 0) {
            for (int i = 0; i < numberBorrowed; i++) {
                for (Book book : Student.getStudentBook()) {
                    if (book.getBookId().equals(arrId[i])) {
                        book.setStock(book.getStock() + 1);
                    }
                }
            }
        } else
            Student.logOut();
    }
}