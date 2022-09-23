import MiscUtils.DotFunction;
import java.util.Random;

public class Trainer {

    //private int mutCount = 1; // 1
    private double addition = 0.1; //0.5
    public final double[] aimRow;
    ProcessorWrapper pw;
    //маска, сообщающая какие функции следует мутировать а какие - нет
    boolean[] mutateMask;
    boolean maskTunes;

    double spread = 0.99;

    public void setMutCount(int mutCount) {
        //this.mutCount = mutCount;
    }

    public void setAddition(double addition) {
        this.addition = addition;
    }

    public Trainer(double[] aimRow, ProcessorWrapper pw, boolean[] mask, boolean maskTunes) {
        this.aimRow = aimRow;
        this.pw = pw;
        this.mutateMask = mask;
        this.maskTunes = maskTunes;
    }

    public void executeTraining(int iterateCounter, int timeLimit, double precisionLimit) {
        //фиксируем исходные настройки и УПФ
        DotFunction dfTune = (DotFunction) pw.tuningWrap.clone();
        DotFunction[] dfUF = new DotFunction[pw.dotFunctions.length];
        for (int i = 0; i < dfUF.length; i++) {
            dfUF[i] = (DotFunction) pw.dotFunctions[i].clone();
        }
        //вычисляем результат c применением исходных настроек
        double[] lastResultVector = pw.getResultOfProcessing();
        //контейнер для записи оценки разности векторов
        double lastDiff = Double.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        //запускаем цикл итераций обучения
        boolean stage1 = true;

        int functionsCount = pw.dotFunctions.length - 1;

        for (int i = 0; i < iterateCounter; i++) {
            //ограничительный таймер
            if ((System.currentTimeMillis() - startTime) / 1000 > timeLimit) {
                System.out.println("расчет прерван по превышении допустимого времени");
                break;
            }

            //мутируем настройки функций
            //вариант когда мутируется каждая функция перед очередной проверкой качества
            /*
            for (int j = 0; j < pw.dotFunctions.length; j++) {
                if(mutateMask[j]){
                    DFMutator dfm = new DFMutator(pw.dotFunctions[j]);
                    dfm.mutate(mutCount, addition);
                }
            }
            */

            Random r = new Random();

            boolean lock = true;
            while(lock){
                int indexOfMut = r.nextInt(functionsCount);
                if(mutateMask[indexOfMut]){
                    DFMutator dfm = new DFMutator(pw.dotFunctions[indexOfMut]);
                    //dfm.mutate(addition, true, spread);
                    dfm.groupMutate(addition, spread, 1, DFMutator.gainFunc);
                    lock = false;
                }
            }

            //вектора переменных (настроек)
            if(maskTunes){
                DFMutator dfm = new DFMutator(pw.tuningWrap);
                dfm.mutate(addition, true, spread);
            }

            //отправляем новые настройки в оборачиваемый алгоритм
            pw.updateDF();
            //вычисляем результат с новыми настройками
            double[] newResult = pw.getResultOfProcessing();
            //сравниваем векторы результатов (исходный и мутантный)
            double newDiff = new ResultComparator(aimRow, newResult).getDataDifference();

            /*
            //механизм постепенного уменьшения диапазона возможных мутаций
            if (newDiff < addition) {
                addition = newDiff;
                stage1 = false;
            }
            if(stage1){
                addition /= 1.00001; //1.0001
            }
             */

            //если вычисления достигли заданной точности, прерываем цикл
            if (newDiff < precisionLimit) {
                lastDiff = newDiff;
                System.out.println("расчет прерван по достижении необходимой точности");
                break;
            }
            //проверяем, улучшилась ли оценка, если да...
            if (newDiff < lastDiff) {
                System.out.println("solve is better!: " + newDiff);
                //фиксируем новую оценку качества
                lastDiff = newDiff;
                //фиксируем новый вектор результата
                System.arraycopy(newResult, 0, lastResultVector, 0, lastResultVector.length);
                //фиксируем мутантные точечные функции для дальнейшей тренировки
                dfTune = (DotFunction) pw.tuningWrap.clone();
                dfUF = new DotFunction[pw.dotFunctions.length];
                for (int k = 0; k < dfUF.length; k++) {
                    dfUF[k] = (DotFunction) pw.dotFunctions[k].clone();
                }
            } else {
                //иначе откатываемся к последним вариантам настроек и УПФ
                pw.tuningWrap = (DotFunction) dfTune.clone();
                pw.dotFunctions = new DotFunction[pw.dotFunctions.length];
                for (int k = 0; k < dfUF.length; k++) {
                    pw.dotFunctions[k] = (DotFunction) dfUF[k].clone();
                }
            }
        }
        System.out.println("оценка качества: " + lastDiff);

        System.out.print("целевые значения: ");
        for (double d : aimRow) {
            System.out.print(d + " ");
        }
        System.out.print("\n");

        System.out.print("достигнутые расчетные значения: ");
        for (double d : lastResultVector) {
            System.out.print(d + " ");
        }
        System.out.print("\n");
    }

    //установить случайное положение точек функций
    public void setRandFunctions(double coeffRange, double dfRange) {
        Random rand = new Random();
        for (int i = 0; i < pw.tuningWrap.getFuncSize(); i++) {
            pw.tuningWrap.setVal(i, rand.nextDouble() * coeffRange);
        }
        for (DotFunction df : pw.dotFunctions) {
            for (int i = 0; i < df.getFuncSize(); i++) {
                df.setVal(i, rand.nextDouble() * dfRange);
            }
        }
    }
}
