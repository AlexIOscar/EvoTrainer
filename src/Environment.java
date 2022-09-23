import MiscUtils.DotFunction;

public class Environment {
    public static DotFunction dotFunction1 = new DotFunction();
    public static DotFunction dotFunction2 = new DotFunction();

    static {
        dotFunction1.addDot(0,0);
        dotFunction1.addDot(0.2,0.04);
        dotFunction1.addDot(0.5,0.25);
        dotFunction1.addDot(1,1);
        dotFunction1.addDot(2,4);
        dotFunction1.addDot(3,9);
        dotFunction1.addDot(4,16);
        dotFunction1.addDot(6,36);
        dotFunction1.addDot(8,64);
        dotFunction1.addDot(10,100);
        dotFunction1.addDot(20,400);

        dotFunction2.addDot(0, 0);
        dotFunction2.addDot(10, 10);
    }
}
