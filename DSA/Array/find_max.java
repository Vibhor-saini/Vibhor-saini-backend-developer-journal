package DSA.Array;

public class find_max {
    public static void main(String[] args) {
       int[] myarr = {2, 5, 1, 7, 3, 33, 56};
       int max = myarr[0];
       for (int i : myarr) {
           if (i > max) {
               max = myarr[i + 1];
           }
       }
       System.out.println("Max Element is: " + max);
    }
}
