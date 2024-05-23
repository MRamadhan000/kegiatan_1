package com.main.data;
import com.main.Main;
import com.main.books.Book;
import com.main.util.IMenu;

import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Student extends User implements IMenu {
    private String name,faculty,programStudi,NIM;
    private ArrayList<Book> borrowedBooks= new ArrayList<>();
    private static ArrayList<Book> studentBook = new ArrayList<>();
    public Student(String name, String NIM, String faculty, String programStudi){
        this.name = name;
        this.NIM = NIM;
        this.faculty = faculty;
        this.programStudi = programStudi;
    }
    public Student(){}


    @Override
    public void menu() {
        int numberBorrowed = 0;
        boolean isFound = false;
        Student student = null;
        Scanner inputObj = new Scanner(System.in);
        String inputNIM;
        while (true) {
            try {
                System.out.print("Enter Your NIM (input 99 untuk back): ");
                inputNIM = Main.inputNIM();
                if (inputNIM.equals("99"))
                    break;

                student = Student.searchStudent(inputNIM);
                if (student != null) {
                    isFound = true;
                    break;
                } else
                    System.out.println("NIM Tidak Ada");
            }catch (InputMismatchException e){
                System.err.println("Invalid input format. Please enter a valid integer.");
                inputObj.next(); // clear the in
            }
        }
        if (!isFound && !inputNIM.equals("99"))
            System.out.println("INVALID NOT FOUND");
        if (isFound) {
            String[] arrId = new String[10];
            int[] arrDuration = new int[10];
            boolean isRun = true;
            while (isRun) {
                student.displayBook(Student.getStudentBook());
                System.out.print("===== Student Menu =====\n1. Buku Terpinjam \n2. Pinjam Buku\n3. Kembalikan Buku\n4. Pinjam Buku atau Logout\n5. Update Buku\nChoose option (1-3) : ");
                try {
                    int choose = inputObj.nextInt();
                    inputObj.nextLine();
                    switch (choose) {
                        case 1:
                            student.displayBookStudent();
                            break;
                        case 2:
                            String inputId;
                            do {
                                System.out.print("Input Id buku yang ingin dipinjam (input 99 untuk kembali)\nInput : ");
                                inputId = inputObj.nextLine();
                                if (inputId.equals("99"))
                                    break;
                                Book book = Student.searchBookAll(inputId);
                                if (book == null)
                                    System.out.println("BUKU DENGAN ID " + inputId + " TIDAK DITEMUKAN");
                                else {
                                    if (book.getStock() > 0) {
                                        Book bookStudent = student.searchBookBorrowed(inputId);
                                        if (bookStudent != null || Arrays.asList(arrId).contains(inputId)) {
                                            System.out.println("ANDA TIDAK BISA MEMINJAM BUKU INI KARENA TELAH DIPINJAM");
                                        } else {
                                            int duration;
                                            do {
                                                System.out.print("Berapa lama buku akan dipinjam ? (maksimal 14 hari)\nInput lama (hari) : ");
                                                duration = inputObj.nextInt();
                                                inputObj.nextLine();
                                                if (duration > 14)
                                                    System.out.println("Masukkan durasi yang benar");
                                            } while (duration > 14);
                                            arrId[numberBorrowed] = inputId;
                                            arrDuration[numberBorrowed] = duration;
                                            numberBorrowed++;
                                            book.setStock(book.getStock() - 1);
                                        }
                                    } else
                                        System.out.println("Stok buku " + book.getTitle() + " sudah habis.");
                                }
                            } while (!inputId.equals("99"));
                            break;
                        case 3:
                            System.out.print("Masukkan Id buku yang ingin dikembalikan : ");
                            String inputAgain = inputObj.nextLine();
                            Book book = student.searchBookBorrowed(inputAgain);
                            if(book!=null)
                                student.returnBook(inputAgain);
                            else
                                System.out.println("Buku dengan ID " + inputAgain + " tidak ditemukan");
                            break;
                        case 4:
                            if (numberBorrowed > 0) {
                                student.showTempBook(arrId, numberBorrowed, arrDuration);
                                int choose2;
                                System.out.print("Apakah anda ingin meminjam buku tersebut\n1. Ya\n2. Tidak\nChoose Option : ");
                                choose2 = inputObj.nextInt();
                                inputObj.nextLine();
                                Main.addTempBook(student, numberBorrowed, choose2, arrId, arrDuration);
                            }
                            isRun = false;
                            break;
                        case 5:
                            student.updateBooks();
                            break;
                        default:
                            System.out.println("INVALID INPUT");
                            break;
                    }
                }catch (InputMismatchException e){
                    System.err.println("Invalid input format. Please enter a valid integer.");
                    inputObj.next(); // clear the in
                }
            }
        }
    }

    @Override
    public void addBook(Book book){
        borrowedBooks.add(book);
    }
    public void choiceBook(String bookId,int duration){
        Book book = Student.searchBookAll(bookId);
        Book borrowedBookCopy = new Book(book.getBookId(),book.getTitle(),book.getAuthor(),1);
        borrowedBookCopy.setDuration(duration);
        borrowedBookCopy.setCategory(book.getCategory());
        this.addBook(borrowedBookCopy);
        Book bookAdmin = Admin.searchBookAll(bookId);
        bookAdmin.setStock(bookAdmin.getStock()-1);
    }

    public static void setStudentBook() {
        studentBook.clear();
        for (Book book : Admin.getBookList()) {
            Book copiedBook = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getStock());
            copiedBook.setCategory(book.getCategory());
            studentBook.add(copiedBook);
        }
    }
    public void returnBook(String inputId){
        Book bookBorrowed = this.searchBookBorrowed(inputId);
        if (bookBorrowed != null){
            Book bookAdmin = Admin.searchBookAll(inputId);
            bookAdmin.setStock(bookAdmin.getStock()+1);
            Book allBook = Student.searchBookAll(inputId);
            allBook.setStock(allBook.getStock() + 1);
            this.getBorrowedBooks().remove(bookBorrowed);
            System.out.println("Buku dengan ID " + inputId + " berhasil dikemabalikan.");
        }else
            System.out.println("Buku dengan ID " + inputId + " tidak dipinjam oleh siswa.");
    }
    public static void logOut(){
        System.out.println("LOG OUT");
    }

    @Override
    public void updateBooks() {
        Scanner inputObj = new Scanner(System.in);
        String id;
        System.out.print("===== EDIT BOOKS ======\nMasukkan id buku yang durasinya ingin diubah : ");
        id = inputObj.nextLine();
        Book book = this.searchBookBorrowed(id);
        if(book!=null){
            int time;
            do {
                System.out.print("Masukkan tambahan durasi yang diinguinkan : ");
                time = inputObj.nextInt();
                if(time + book.getDuration()>14)
                    System.out.println("YOU ARE REACH THE MAKSIUMUM DURATION");
            }while (time + book.getDuration() > 14);
            System.out.println("EDIT SUCCESFULL");
        }
        else
            System.out.println("BUKU DENGAN ID "+  id + "TIDAK DIPINJAM");
    }

    public static Book searchBookAll(String id){
        for (Book book : studentBook)
            if(book.getBookId().equals(id))
                return book;
        return null;
    }

    public Book searchBookBorrowed(String id){
        for (Book book : this.getBorrowedBooks())
            if(book.getBookId().equals(id))
                return book;
        return null;
    }
    public static Student searchStudent(String inputNIM){
        for (Student student : Admin.getStudentData())
            if(student.getNIM().equals(inputNIM))
                return student;
        return null;
    }

    //MENAMPILKAN SEMUA DAFTAR BUKU KETIKA BERADA DI MENU STUDENT
    @Override
    public void displayBook(ArrayList<Book> arr) {
        super.displayBook(arr);
    }

    //MENAMPILKAN DAFTAR BUKU YANG DIPINJAM
    public void displayBookStudent(){
        if(!this.getBorrowedBooks().isEmpty()) {
            System.out.print("==");
            super.printLine();
            System.out.printf("|| %-3s || %-19s || %-20s || %-20s || %-12s || %-6s ||\n", "No.", "Id buku", "Nama Buku", "Author", "Category", "Duration");
            System.out.print("==");
            printLine();
            int count = 1;
            for (Book x : getBorrowedBooks()) {
                System.out.printf("|| %-3d || %-19s || %-20s || %-20s || %-12s || %-6d   ||\n", count, x.getBookId(), x.getTitle(), x.getAuthor(), x.getCategory(), x.getDuration());
                count++;
            }
            System.out.print("==");
            super.printLine();
        }else
            System.out.println("TIDAK ADA BUKU YANG DIPINJAM");
    }
    public void displayInfo(){
        System.out.println("NAME :" + this.name + "\nNIM : " + this.NIM + "\nFakultas :" +this.faculty + "\nProgram Studi :" + this.programStudi + "\n");
    }

    // MENAMPILKAN BUKU SEBELUM DIPINJAM
    public void showTempBook(String []inputId,int amount,int [] duration){
        super.printLine();
        System.out.printf("|| %-3s || %-19s || %-19s || %-19s || %-12s || %-8s ||\n", "No.", "Id buku", "Nama Buku", "Author", "Category", "Duration");
        super.printLine();
        int count = 1;
        for (int i = 0 ; i < amount; i++)
            for (Book x : getStudentBook()){
                if(x.getBookId().equals(inputId[i])){
                    System.out.printf("|| %-3d || %-19s || %-19s || %-19s || %-12s || %-8s ||\n", count, x.getBookId(), x.getTitle(), x.getAuthor(), x.getCategory(), duration[i]);
                    count++;
                }
            }
        super.printLine();
    }
    public static ArrayList<Book> getStudentBook() {
        return studentBook;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public String getNIM(){
        return this.NIM;
    }
}