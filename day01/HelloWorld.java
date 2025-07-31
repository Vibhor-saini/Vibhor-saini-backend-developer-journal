public class HelloWorld {
    public static void main(String[] args) {

        //Java Variables & Data Types==============
        int age = 24;           // integer
        System.out.println("Age: " + age);
        double pi = 3.14;       // decimal
        System.out.println("PI: " + pi);
        boolean isValid = true; // true/false
        System.out.println("isValid: " + isValid);
        char grade = 'A';       // single character
        System.out.println("Grade: " + grade);
        String name = "John";   // text
        System.out.println("Name: " + name);


        //Conditional Statements============
        if (age > 30) {
            System.out.println("Age is greater than 30");
        } else if (age > 10 && age <= 30) {
            System.out.println("Age is between 10 and 30");
        } else {
            System.out.println("Age is 10 or below");
        }


        //loops==============

        for (int i = 1; i <= age; i++) {
            if (i == 20) {
                continue;
            }

            if (i == 22) {
                break;
            }
            System.out.println("Number is: " + i);
        }


        //while loop==================

        int n = 1;
        do {
            System.out.println("Number always run by : " + n);
            n++;
        } while (n < 0);


        //The for-each Loop======================

        //String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        //for (String i : cars) {
        //System.out.println(i);
        //}

        int[] cars = {10, 20, 30, 40};
        for (int i : cars) {
        System.out.println(i);
        }


        //Multidimensional Arrays================
        int[][] myNumbers = { {1, 2, 3, 4}, {5, 6, 7} };
        for (int i = 0; i < myNumbers.length; ++i) {
            for (int j = 0; j < myNumbers[i].length; ++j) {
                System.out.println(myNumbers[i][j]);
            }
        }
    }
}



