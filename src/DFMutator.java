import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import MiscUtils.DotFunction;

public class DFMutator {
    DotFunction df;

    public DFMutator(DotFunction df) {
        this.df = df;
    }

    //функция "формы" области групповой мутации
    public static final DotFunction gainFunc = new DotFunction();

    static {
        gainFunc.addDot(0, 0);
        gainFunc.addDot(50, 1);
        gainFunc.addDot(100, 0);
    }

    // функция, мутирующая точечную функцию в countToMut местах на величину addition
    public void mutate(int countToMut, double addition) {
        Random rand = new Random();
        int mutWidth = Math.min(countToMut, df.getFuncSize());
        int[] alreadyMutated = new int[mutWidth];
        //указатель на позицию в массиве "уже мутированных", куда выполнится следующая запись индекса
        int amPointer = 0;
        Arrays.fill(alreadyMutated, -1);
        for (int i = mutWidth; i > 0; ) {
            int randIndex = rand.nextInt(df.getFuncSize());
            boolean checked = true;
            for (int muted : alreadyMutated) {
                if (muted == randIndex) {
                    checked = false;
                    break;
                }
            }

            if (checked) {
                double value = df.getValByIndex(randIndex) * (1 + (rand.nextDouble() - 0.5) * addition * 2);
                df.setVal(randIndex, value > 0 ? value : -value);
                alreadyMutated[amPointer] = randIndex;
                if (amPointer == mutWidth - 1) break;
                amPointer++;
                i--;
            }
        }
    }

    //функция, мутирующая только одну точку
    public void mutate(double addition, boolean useModificator, double spread) {
        Random rand = new Random();

        while (true) {
            int randIndex = rand.nextInt(df.getFuncSize());
            double value = df.getValByIndex(randIndex) * (1 + (rand.nextDouble() - 0.5) * addition * 2);
            if (useModificator) {
                if (!checkSmoothness(randIndex, value, spread)) continue;
            }
            df.setVal(randIndex, value > 0 ? value : -value);
            break;
        }
    }

    //метод групповой мутации - когда вокруг центральной точки мутируют и остальные до некоторого удаления
    //gainFunc - по аргументам от 0 до 100%, по значению - от нуля до 1
    public void groupMutate(double addition, double spreadHeight, double spreadWidth, DotFunction gainFunc) {
        Random rand = new Random();

        //ширина области определения функции
        double argRange = df.getArgByIndex(df.getFuncSize() - 1) - df.getArgByIndex(0);

        int randIndex = rand.nextInt(df.getFuncSize());
        //аргумент, на который попала мутация в этой итерации
        double CArg = df.getArgByIndex(randIndex);
        //начало области мутирования
        double startMutArg = CArg - (argRange * spreadWidth) / 2;
        //конец области мутирования
        double endMutArg = CArg + (argRange * spreadWidth) / 2;

        //индексы, попавшие в область мутирования
        List<Integer> mutIndexes = new ArrayList<>();
        for (int i = 0; i < df.getFuncSize(); i++) {
            if (df.getArgByIndex(i) >= startMutArg && df.getArgByIndex(i) <= endMutArg) {
                mutIndexes.add(i);
            }
        }

        //высота максимального отклонения в этой итерации
        double CValue = df.getValByIndex(randIndex) * ((rand.nextDouble() - 0.5) * addition * 2);

        //высоты отклонения в отдельных индексах
        List<Double> addValues = new ArrayList<>();
        for (Integer index : mutIndexes) {
            //"расстояние" от начала зоны мутации до точки, в единицах аргумента функции
            double dist = df.getArgByIndex(index) - startMutArg;
            //то же самое в процентах от ширины области мутаций
            double mutWidth = endMutArg - startMutArg;
            double distPercents = (dist / mutWidth) * 100;
            addValues.add(gainFunc.getValue(distPercents) * CValue);
        }

        int innerIndex = 0;
        for (Integer index : mutIndexes) {
            double solveVal = df.getValByIndex(index) + addValues.get(innerIndex);
            df.setVal(index, solveVal > 0 ? solveVal : - solveVal);
            innerIndex++;
        }
    }

    //проверяет монотонность функции в окрестности индекса
    private boolean checkMonotonicUp(int index, double valueCandidate) {
        //если индекс за пределами диапазона существующих в функции то возвращаем false
        if (index > df.getFuncSize() - 1) return false;

        if (df.getFuncSize() == 1) return true;

        if (index == 0) {
            return valueCandidate <= df.getValByIndex(index + 1);
        }

        if (index == df.getFuncSize() - 1) {
            return valueCandidate >= df.getValByIndex(df.getFuncSize() - 2);
        }
        return (valueCandidate <= df.getValByIndex(index + 1)) && (valueCandidate >= df.getValByIndex(index - 1));
    }

    private boolean checkSmoothness(int index, double valueCandidate, double spread) {
        //если индекс за пределами диапазона существующих в функции, то возвращаем false
        if (index > df.getFuncSize() - 1) return false;
        //если функция из одной точки состоит - вернуть true
        if (df.getFuncSize() == 1) return true;

        if (index == df.getFuncSize() - 1 || index == 0) {
            return true;
        }

        double d_arg = df.getArgByIndex(index + 1) - df.getArgByIndex(index - 1);
        double d_val = df.getValByIndex(index + 1) - df.getValByIndex(index - 1);
        double diff = d_val / d_arg;

        double d_arg2 = df.getArgByIndex(index) - df.getArgByIndex(index - 1);
        double d_val2 = valueCandidate - df.getValByIndex(index - 1);
        double diff2 = d_val2 / d_arg2;

        //TODO тут подумать, нужна другая логика назначения границ допустимых дифферентов
        return diff2 / diff < (1 + spread) && diff2 / diff > (1 - spread);
    }
}