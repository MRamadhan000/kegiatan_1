package com.main.data;

import com.main.Main;
import com.main.books.Book;
import com.main.books.HistoryBook;
import com.main.books.StoryBook;
import com.main.books.TextBook;
import com.main.exception.custom.illegalAdminAcces;
import com.main.util.IMenu;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Admin extends User implements IMenu {
    Scanner inputObj = new Scanner(System.in);
    private final String adminUserName = "admin";
    private final String adminPassword = "admin";
    private static final ArrayList<Student> studentData = new ArrayList<>();
    private static final ArrayList<String> studentList = new ArrayList<>();

    public static Book searchBookAll(String id){
        for (Book book : Admin.getBookList())
            if(book.getBookId().equals(id))
                return book;
        return null;
    }

    @Override
    public void menu() {
        boolean isRun = true;
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            System.out.print("===== Admin Menu =====\n1. Add Student\n2. Add Book \n3. Display Registered Student \n4. Display Available Books\n5. Update Book\n6. Logout\nChoose option (1-5) : ");
            try {
                int choose = inputObj.nextInt();
                inputObj.nextLine();
                switch (choose) {
                    case 1:
                        Main.addTempStudent();
                        break;
                    case 2:
                        this.inputBook();
                        break;
                    case 3:
                        this.displayStudent();
                        break;
                    case 4:
                        this.displayBook(Admin.getBookList());
                        break;
                    case 5:
                        this.updateBooks();
                        break;
                    case 6:
                        isRun = false;
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                        break;
                }
            }catch (InputMismatchException e){
                System.err.println("Invalid input format. Please enter a valid integer.");
                inputObj.next();
            }
        }
    }

    public static void updateBooksStudent(String id,String changes,int choose){
        for (Student student : Admin.getStudentData()){
            for (Book book : student.getBorrowedBooks()){
                if(book.getBookId().equals(id)){
                    switch (choose){
                        case 1:
                            book.setTitle(changes);
                            break;
                        case 2:
                            book.setAuthor(changes);
                            break;
                        case 3:
                            book.setCategory(changes);
                            break;
                    }
                }
            }

        }
    }

    @Override
    public void updateBooks() {
        Scanner inputObj = new Scanner(System.in);
        String id,changes = null,choose,chooseCategory;
        this.displayBook(Admin.getBookList());
        System.out.print("===== EDIT BOOKS ======\nMasukkan id buku yang ingin diubah : ");
        id = inputObj.nextLine();
        Book book = Admin.searchBookAll(id);
        if(book!=null){
            System.out.print("1. Title\n2. Author\n3. Category\n4. Stock\nPlease choose that you want change : ");
            choose = inputObj.nextLine();
            switch (Integer.parseInt(choose)){
                case 1:
                    System.out.print("ENTER NEW TITLE : ");
                    changes = inputObj.nextLine();
                    book.setTitle(changes);
                    break;
                case 2:
                    System.out.print("ENTER NEW AUTHOR : ");
                    changes = inputObj.nextLine();
                    book.setAuthor(changes);
                    break;
                case 3:
                    System.out.print("1. Story\n2. History\n3. Text Book\nCHOOSE CATAEGORY :");
                    chooseCategory = inputObj.nextLine();
                    switch (Integer.parseInt(chooseCategory)){
                        case 1:
                            changes = "Story";
                            break;
                        case 2:
                            changes = "History";
                            break;
                        case 3:
                            changes = "Text Book";
                            break;
                        default:
                            break;
                    }
                    book.setCategory(changes);
                    break;
                case 4:
                    System.out.print("ENTER NEW STOCK : ");
                    changes = inputObj.nextLine();
                    book.setStock(Integer.parseInt(changes));
                    break;
                default:
                    System.out.print("INVALID CHOOSE");
                    break;
            }
            Admin.updateBooksStudent(id,changes,Integer.parseInt(choose));
            System.out.println("EDIT SUCCESFULL");
        }
        else
            System.out.println("BUKU DENGAN ID "+  id + " TIDAK DIPINJAM");
    }

    public void addStudent(Student student){
        studentData.add(student);
        studentList.add(student.getNIM());
    }

    public void inputBook(){
        String newTitle,newAuthor,newStock,choose;
        do {
            System.out.print("Select book category:\n1. Story\n2. History\n3. Text Book\nChoose Category : ");
            choose = inputObj.nextLine();
            if (!choose.matches("[1-3]")) {
                System.out.println("Invalid category. Please choose between 1 to 3.");
            }
        } while (!choose.matches("[1-3]"));
        System.out.print("Input book title : ");
        newTitle = inputObj.nextLine();
        System.out.print("Input book Author : ");
        newAuthor = inputObj.nextLine();
        do {
            System.out.print("Input book stock : ");
            newStock = inputObj.nextLine();

            if (!newStock.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (!newStock.matches("\\d+"));
        Book newBook = null;
        switch (Integer.parseInt(choose)){
            case 1:
                newBook = new StoryBook(generateId(),newTitle,newAuthor,Integer.parseInt(newStock));
                break;
            case 2:
                newBook = new HistoryBook(generateId(),newTitle,newAuthor,Integer.parseInt(newStock));
                break;
            case 3:
                newBook = new TextBook(generateId(),newTitle,newAuthor,Integer.parseInt(newStock));
                break;
            default:
                break;
        }
        super.addBook(newBook);
        System.out.println("Book sucessfully added to the library");
    }
    @Override
    public void displayBook(ArrayList<Book> arr){
        super.displayBook(arr);
    }
    public void displayStudent(){
        for (Student x : studentData){
            x.displayInfo();
        }
    }
    public boolean isAdmin() throws illegalAdminAcces {
        String username, pass;
        System.out.print("Input username: ");
        username = inputObj.nextLine();
        System.out.print("Input password: ");
        pass = inputObj.nextLine();
        if (username.equals(getAdminUserName()) && pass.equals(getAdminPassword()))
            return true;
        else
            throw new illegalAdminAcces("Invalid Credentials");
    }


    public String generateId(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int part = random.nextInt(0xFFFF + 1);
            sb.append(String.format("%04x", part));
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public String getAdminUserName() {
        return adminUserName;
    }
    public static ArrayList<Student> getStudentData() {
        return studentData;
    }
    public static ArrayList<String> getStudentList() {
        return studentList;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
}