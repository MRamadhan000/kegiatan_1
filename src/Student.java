public class Student {
    String name, faculty, programStudi;

    Student(String name, String faculty, String programStudi) {
        this.name = name;
        this.faculty = faculty;
        this.programStudi = programStudi;
    }

    public static void logout(){
        System.out.println("System Logut");
    }
    

    public static void displayBooks(String [][]arr){
        for(int i = 0 ; i < 3 ;i++){
            System.out.println("\t="+arr[i][0] + " = " + arr[i][1] + " = "+ arr[i][2] + " = " + arr[i][3]);
        }
    }

//    public String getName(){
//        return this.name;
//    }
//    public String getFaculty(){
//        return this.faculty;
//    }
//    public String getProgramStudi(){
//        return this.programStudi;
//    }

}
