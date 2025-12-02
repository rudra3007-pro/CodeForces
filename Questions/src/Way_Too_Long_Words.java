import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        while (n-- > 0) {
            String word = sc.next();
            int len = word.length();

            if (len > 10) {
                System.out.println(word.charAt(0) + "" + (len - 2) + word.charAt(len - 1));
            } else {
                System.out.println(word);
            }
        }
    }
}
