public class TextBook extends Book{
    private String category = "TextBook";
    TextBook(String bookId, String title, String author, int stock){
        super(bookId,title,author,stock);
        super.setCategory(category);
    }
}
