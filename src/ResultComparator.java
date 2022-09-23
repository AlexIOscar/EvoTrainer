public class ResultComparator {

    double[] resultArr1;
    double[] resultArr2;

    public ResultComparator(double[] resultArr1, double[] resultArr2) {
        this.resultArr1 = resultArr1;
        this.resultArr2 = resultArr2;
    }

    public double getDataDifference() {
        double result = 0;
        for (int i = 0; i < resultArr1.length; i++) {
            result += compareSingle(resultArr1[i], resultArr2[i]);
        }
        return result;
    }

    public double getDataDifference(unarFunc ampFunc, binarFunc diffFunc) {
        double result = 0;
        for (int i = 0; i < resultArr1.length; i++) {
            result += compareSingle(resultArr1[i], resultArr2[i], ampFunc, diffFunc);
        }
        return result;
    }

    private double compareSingle(double val1, double val2, unarFunc ampFunc, binarFunc diffFunc) {
        double diff = Math.abs(diffFunc.res(val1, val2));
        return ampFunc.res(diff);
    }

    //использует квадратичный рост результата от аргумента
    private double compareSingle(double val1, double val2) {
        //вариант оценочной функции "абсолютный"
        //double diff = Math.abs(val1 - val2);
        //вариант оценочной функции "относительный"
        double diff = Math.abs(val1 / val2 - 1) * 100;
        //возвратная функция
        return diff * diff;
    }

    @FunctionalInterface
    public interface unarFunc {
        double res(double arg);
    }

    @FunctionalInterface
    public interface binarFunc {
        double res(double arg, double arg2);
    }
}
