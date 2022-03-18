public class Main {
    public static void main(String []args) {

        System.out.println("Q1");

        for(int i = 0; i <= 2; i++) {
            for(int j = i; j < 3; j++) {
                System.out.print(i);
            }
        }

        System.out.println("Q2");

        int i = 0;
        do {
            System.out.println(i);
        } while (i < 0);

        System.out.println("Q3");

        boolean b = false;
        System.out.println(b = true);

        int a = 0;
        System.out.println(a = 10);

        System.out.println("Q4");
        System.out.println((int) 5 + 7.10);

        System.out.println((int)(5 + 7.10));
        System.out.println((int) 7.10 + 5);

        System.out.println("Q4");
        System.out.println(100/2*5);

        System.out.println("Q5");
        System.out.println(2.0 / 3.0 + 1/3);

        System.out.println("Q6");
        System.out.println("1+1="+1+1);

        System.out.println("Q7");
        boolean c = false;
        /*do {
            System.out.print("fin");
        } while(c=true);*/
        // finfinfin...

        System.out.println("Q8");
        System.out.println(1.0/0);

        System.out.println("Q9");
        System.out.println(true || (1/0) < 1);

        System.out.println("Q10");
        int x = 0;
        System.out.println("++x = " + ++x);
        x = 0;
        System.out.println("x++ = " + x++);

        System.out.println("Q11");
        x = 1;
        int y = 1;
        x = ++x;
        y = y++;
        System.out.println(x + " " + y);

    }
}