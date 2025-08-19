public class task {
    public static void main(String[] args){
     int[] myarr = {10,20,30,40,50};
     try {  
         System.out.println("Element is: " + myarr[4]);
     } catch (ArrayIndexOutOfBoundsException e) {
       System.out.println("Something gone wrong! " + e.getMessage());
     } finally{
        System.out.println("Program continues after exception.");
     }
    }
}

// Take an array of 5 integers.
// Try to access an invalid index (e.g., arr[10]).
// Handle the exception (ArrayIndexOutOfBoundsException).
// Print "Program continues after exception.".