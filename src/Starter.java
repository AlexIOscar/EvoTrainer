import MiscUtils.DotFunction;

import java.util.stream.Stream;

public class Starter {
    public static void main(String[] args) {

        /*
        DFMutator dfm = new DFMutator(Environment.dotFunction1);

        System.out.println(Environment.dotFunction1);
        dfm.mutate(5, 1.5);
        System.out.println("----------------- after mut ---------------");
        System.out.println(Environment.dotFunction1);

        double[] arr1 = {7.3, 15.2, 24.8};
        double[] arr2 = {10.1, 11.2, 12.5};
        ResultComparator resultComparator = new ResultComparator(arr1, arr2);
        System.out.println(resultComparator.getDataDifference(x -> x * x * x, (y, z) -> Math.abs(1 - y / z)));
        */

        Trainer tr = new Trainer(new double[]{12312.0, 7308, 19584, 26640, 2412, 1620, 3600, 1296, 6480,
                1440, 5940, 756, 1440, 7308, 9792},
                new ProcessorWrapper(),
                new boolean[]{true, true, true, true, true, true}, false);

        //tr.pw.takeDF();
        System.out.println("настройки до обучения");
        System.out.println(tr.pw.tuningWrap);
        for (DotFunction df : tr.pw.dotFunctions) {
            System.out.println(df);
        }
        System.out.println("-------------------------------");
        //tr.setRandFunctions(100, 100);
        tr.executeTraining(10000000, 10, 0.1);

        tr.pw.takeDF();
        System.out.println("настройки после обучения");
        System.out.println(tr.pw.tuningWrap);
        for (DotFunction df : tr.pw.dotFunctions) {
            System.out.println(df);
        }
        System.out.println("-------------------------------");
    }
}
