public class HistoryBook extends Book{
    private String category = "History Book";
    HistoryBook(String bookId, String title, String author, int stock){
        super(bookId,title,author,stock);
        super.setCategory(category);
    }

}
