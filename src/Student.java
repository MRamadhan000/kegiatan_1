import java.util.ArrayList;

public class Student extends User{
    private String name,faculty,programStudi,NIM;
    private ArrayList<Book> borrowedBooks= new ArrayList<>();
    private static ArrayList<Book> studentBook = new ArrayList<>();

    Student(String name, String NIM, String faculty, String programStudi){
        this.name = name;
        this.NIM = NIM;
        this.faculty = faculty;
        this.programStudi = programStudi;
    }


    @Override
    public void addBook(Book book){
        borrowedBooks.add(book);
    }

    public void acceptBookBorrowed(String bookId,int duration){
        for (Book book : Student.getStudentBook()){
            if(book.getBookId().equals(bookId)){
                Book borrowedBookCopy = new Book(book.getBookId(),book.getTitle(),book.getAuthor(),1);
                borrowedBookCopy.setDuration(duration);
                borrowedBookCopy.setCategory(book.getCategory());
                this.addBook(borrowedBookCopy);
            }
        }
        for (Book book : Admin.getBookList()){
            if(book.getBookId().equals(bookId)){
                book.setStock(book.getStock() - 1);
            }
        }
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
        boolean isBorrowed = false;
        for (Book book : this.getBorrowedBooks()){
            if(book.getBookId().equals(inputId)){
                this.getBorrowedBooks().remove(book);
                isBorrowed = true;
                break;
            }
        }
        if(isBorrowed){
            for (Book book : Admin.getBookList()){
                if(book.getBookId().equals(inputId)){
                    book.setStock(book.getStock()+1);
                }
            }
            for (Book book : Student.getStudentBook()){
                if(book.getBookId().equals(inputId)){
                    book.setStock(book.getStock() + 1);
                }
            }
            System.out.println("BUKU BERHASIL DIKEMBALIKAN");
        }
        else
            System.out.println("Buku dengan ID " + inputId + " tidak dipinjam oleh siswa.");
    }

    public static void logOut(){
        System.out.println("LOG OUT");
    }

    @Override
    public void displayBook() {
        super.printLine();
        System.out.printf("|| %-3s || %-19s || %-20s || %-20s || %-12s || %-6s ||\n", "No.", "Id buku", "Nama Buku", "Author", "Category", "Stock");
        super.printLine();
        int count = 1;
        for (Book x : studentBook){
            System.out.printf("|| %-3d || %-19s || %-20s || %-20s || %-12s || %-6d ||\n", count, x.getBookId(), x.getTitle(), x.getAuthor(), x.getCategory(), x.getStock());
            count++;
        }
        super.printLine();
    }

    public void displayInfo(){
        System.out.println("NAME :" + this.name + "\nNIM : " + this.NIM + "\nFakultas :" +this.faculty + "\nProgram Studi :" + this.programStudi + "\n");
    }

    public void showBorrowedBooks(){
        System.out.print("==");
        super.printLine();
        System.out.printf("|| %-3s || %-19s || %-20s || %-20s || %-12s || %-6s ||\n", "No.", "Id buku", "Nama Buku", "Author", "Category", "Duration");
        System.out.print("==");
        printLine();
        int count = 1;
        for (Book x : getBorrowedBooks()){
            System.out.printf("|| %-3d || %-19s || %-20s || %-20s || %-12s || %-6d   ||\n", count, x.getBookId(), x.getTitle(), x.getAuthor(), x.getCategory(), x.getDuration());
            count++;
        }
        System.out.print("==");
        super.printLine();
    }

    public void showTempBook(String []inputId,int amount,int [] duration){
        super.printLine();
        System.out.printf("|| %-3s || %-19s || %-19s || %-19s || %-12s || %-8s ||\n", "No.", "Id buku", "Nama Buku", "Author", "Category", "Duration");
        super.printLine();
        int count = 1;
        for (int i = 0 ; i < amount; i++){
            for (Book x : getStudentBook()){
                if(x.getBookId().equals(inputId[i])){
                    System.out.printf("|| %-3d || %-19s || %-19s || %-19s || %-12s || %-8s ||\n", count, x.getBookId(), x.getTitle(), x.getAuthor(), x.getCategory(), duration[i]);
                    count++;
                }
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
