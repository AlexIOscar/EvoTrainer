import java.util.Scanner;

public class Str {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine().replaceAll(" ", "");
        System.out.println(str);
    }
}
