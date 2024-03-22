import java.util.ArrayList;

public class Admin {
    String adminUsername = "admin";
    String adminPassword = "admin";
    ArrayList<Student> listStudent = new ArrayList<Student>();

    public void addStudent(Student student){

        listStudent.add(student);

    }
    public void menuStudent(){
        for (Student x : listStudent){
            System.out.println("Name : " + x.name+ "   Faculty : " + x.faculty+ "  Program : " + x.programStudi);
        }
    }
}