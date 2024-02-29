import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choose;
        String []NIM = {"202310370311066","202310370311067","202310370311068", "202310370311069" ,"202310370311070"};
        String [][] listUserName = { {"admin","titan","risky","joko"},{"adm","tit","ris","jok"}};
        boolean isRun = true;
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            System.out.print("==== LIBRARY SYSTEM ====\n1. Log in as Student\n2. Login as Admin\n3. Exit\nChoose option (1-3) : ");
            choose = inputObj.nextInt();
            switch (choose){
                case 1 :
                    option1(NIM,inputObj);
                    break;
                case 2:
                    option2(listUserName,inputObj);
                    break;
                case 3:
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
            }
        }
    }

    public static void option1(String []NIM, Scanner inputObj){
        inputObj.nextLine();
        String inputNIM;
        boolean isValid;
        do{
            System.out.print("Enter your NIM : ");
            inputNIM = inputObj.nextLine();
            isValid = checkValid1(NIM,inputNIM);
            if(!isValid)
                System.out.println("User Not Found");
        }while (!isValid);
        System.out.println("Successfully Login as Student");
    }

    public static void option2(String [][] listUserName,Scanner inputObj){
        inputObj.nextLine();
        String inputName,inputPass;
        boolean isValid;
        do {
            System.out.print("Enter your username (admin): ");
            inputName = inputObj.nextLine();
            System.out.print("Enter your password (admin) : ");
            inputPass = inputObj.nextLine();
            isValid = checkValid2(inputName,inputPass,listUserName);
            if(!isValid)
                System.out.println("Admin user not found");
        }while (!isValid);
    }

    public static boolean checkValid1(String []NIM, String inputNIM){
        if (inputNIM.length() != 15)
            return false;

        for (char x : inputNIM.toCharArray()){
            if(!Character.isDigit(x))
                return false;
        }
        for(String y : NIM){
            if(y.equals(inputNIM))
                return true;
        }
        return false;
    }

    public static boolean checkValid2(String inputName, String inputPass, String[][] userPass){
        for (int i = 0; i < userPass[0].length - 1; i++) {
            if (userPass[0][i].equals(inputName) && userPass[1][i].equals(inputPass))
                return true;
        }
        return false;
    }



}