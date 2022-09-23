import MiscUtils.DotFunction;
import Assembling.*;

import java.io.IOException;
import java.util.List;

public class ProcessorWrapper {
    //массив "универсальных функций", действующих в рамках оборачиваемого расчетного движка
    DotFunction[] dotFunctions = new DotFunction[6];
    //специальная точечная функция, оборачивающая прочие аргументы расчета
    DotFunction tuningWrap = new DotFunction();

    //экземпляр расчетного движка, содержащего ссылки на переменные расчета, на методы расчета етц
    AssemblySolver asSolver = new AssemblySolver();

    //массив деталей, на которых ведется обучение
    WeldingAssemble[] assemblesArr;

    {
        tuningWrap.addDot(0, asSolver.cd.globalCoeff);
        addAssembles("C:\\SomeDir\\TestBook2.xlsx");
        setStartConfigDF();
    }

    //установка стартовой конфигурации обучаемых функций
    public void setStartConfigDF() {
        for (int i = 0; i < dotFunctions.length; i++) {
            dotFunctions[i] = new DotFunction();
        }
        //масса листовые
        dotFunctions[0].addDot(0, 0);
        dotFunctions[0].addDot(100, 10);
        dotFunctions[0].addDot(200, 20);
        dotFunctions[0].addDot(300, 30);
        dotFunctions[0].addDot(400, 40);
        dotFunctions[0].addDot(500, 50);
        dotFunctions[0].addDot(600, 60);
        dotFunctions[0].addDot(700, 70);
        dotFunctions[0].addDot(800, 80);
        dotFunctions[0].addDot(900, 90);
        dotFunctions[0].addDot(1000, 100);
        dotFunctions[0].addDot(1100, 110);
        dotFunctions[0].addDot(1200, 120);
        dotFunctions[0].addDot(1300, 130);
        dotFunctions[0].addDot(1400, 140);
        dotFunctions[0].addDot(1500, 150);
        dotFunctions[0].addDot(1600, 160);
        dotFunctions[0].addDot(1700, 170);
        dotFunctions[0].addDot(1800, 180);
        dotFunctions[0].addDot(1900, 190);
        dotFunctions[0].addDot(2000, 200);

        //размер листовые
        dotFunctions[1].addDot(0, 1);
        dotFunctions[1].addDot(100, 1);
        dotFunctions[1].addDot(200, 1);
        dotFunctions[1].addDot(300, 1);
        dotFunctions[1].addDot(400, 1);
        dotFunctions[1].addDot(500, 1);
        dotFunctions[1].addDot(600, 1);
        dotFunctions[1].addDot(700, 1);
        dotFunctions[1].addDot(800, 1);
        dotFunctions[1].addDot(900, 1);
        dotFunctions[1].addDot(1000, 1);
        dotFunctions[1].addDot(1100, 1);
        dotFunctions[1].addDot(1200, 1);
        dotFunctions[1].addDot(1300, 1);
        dotFunctions[1].addDot(1400, 1);
        dotFunctions[1].addDot(1500, 1);
        dotFunctions[1].addDot(1600, 1);
        dotFunctions[1].addDot(1700, 1);
        dotFunctions[1].addDot(1800, 1);
        dotFunctions[1].addDot(1900, 1);
        dotFunctions[1].addDot(2000, 1);

        //масса линейные
        dotFunctions[2].addDot(0, 20);
        dotFunctions[2].addDot(5, 20);
        dotFunctions[2].addDot(10, 25);
        dotFunctions[2].addDot(15, 40);
        dotFunctions[2].addDot(25, 95);
        dotFunctions[2].addDot(30, 95);
        dotFunctions[2].addDot(35, 95);
        dotFunctions[2].addDot(40, 95);
        dotFunctions[2].addDot(45, 95);
        dotFunctions[2].addDot(50, 130);
        dotFunctions[2].addDot(55, 130);
        dotFunctions[2].addDot(60, 130);
        dotFunctions[2].addDot(65, 130);
        dotFunctions[2].addDot(70, 130);
        dotFunctions[2].addDot(75, 130);
        dotFunctions[2].addDot(80, 120);
        dotFunctions[2].addDot(85, 120);
        dotFunctions[2].addDot(90, 120);
        dotFunctions[2].addDot(95, 120);
        dotFunctions[2].addDot(100, 120);
        dotFunctions[2].addDot(110, 120);
        dotFunctions[2].addDot(120, 160);
        dotFunctions[2].addDot(130, 160);
        dotFunctions[2].addDot(140, 160);
        dotFunctions[2].addDot(150, 160);
        dotFunctions[2].addDot(160, 160);
        dotFunctions[2].addDot(170, 160);
        dotFunctions[2].addDot(180, 220);
        dotFunctions[2].addDot(190, 220);
        dotFunctions[2].addDot(200, 220);
        dotFunctions[2].addDot(210, 220);
        dotFunctions[2].addDot(220, 220);
        dotFunctions[2].addDot(230, 260);
        dotFunctions[2].addDot(240, 260);
        dotFunctions[2].addDot(250, 260);
        dotFunctions[2].addDot(260, 300);
        dotFunctions[2].addDot(270, 300);
        dotFunctions[2].addDot(280, 300);
        dotFunctions[2].addDot(290, 300);
        dotFunctions[2].addDot(300, 360);

        //размер линейные
        dotFunctions[3].addDot(0, 1);
        dotFunctions[3].addDot(1000, 1);
        dotFunctions[3].addDot(2000, 1);
        dotFunctions[3].addDot(3000, 1);
        dotFunctions[3].addDot(4000, 1);
        dotFunctions[3].addDot(5000, 1);
        dotFunctions[3].addDot(6000, 1);
        dotFunctions[3].addDot(7000, 1);
        dotFunctions[3].addDot(8000, 1);
        dotFunctions[3].addDot(9000, 1);
        dotFunctions[3].addDot(10000, 1);
        dotFunctions[3].addDot(11000, 1);
        dotFunctions[3].addDot(12000, 1);

        //разметка
        dotFunctions[4].addDot(10, 10);
        dotFunctions[4].addDot(1500, 15);
        dotFunctions[4].addDot(12000, 30);

        //коэффициент максимального размера
        dotFunctions[5].addDot(0, 1.0);
        dotFunctions[5].addDot(12000, 1.4);
    }

    //получить настройки из расчетного движка в процессор-враппер
    public void takeDF() {
        dotFunctions[0] = asSolver.cd.massDFPl;
        dotFunctions[1] = asSolver.cd.lengthDFPl;
        dotFunctions[2] = asSolver.cd.massDFLin;
        dotFunctions[3] = asSolver.cd.lengthDFLin;
        dotFunctions[4] = asSolver.cd.markUpDF;
        dotFunctions[5] = asSolver.cd.maxsizeDF;

        tuningWrap.setVal(0, asSolver.cd.globalCoeff);
    }

    //обновить настройки движка настройками из процессор-врапера
    public void updateDF() {
        asSolver.cd.massDFPl = dotFunctions[0];
        asSolver.cd.lengthDFPl = dotFunctions[1];
        asSolver.cd.massDFLin = dotFunctions[2];
        asSolver.cd.lengthDFLin = dotFunctions[3];
        asSolver.cd.markUpDF = dotFunctions[4];
        asSolver.cd.maxsizeDF = dotFunctions[5];

        asSolver.cd.globalCoeff = tuningWrap.getValByIndex(0);
    }

    //установить все настройки передачей объекта-упаковки
    public void setFPack(FunctionsPack functionsPack) {
        this.dotFunctions = functionsPack.dotFunctions;
        this.tuningWrap = functionsPack.tuningWrap;
    }

    //получить функции в виде объекта-упаковки
    public FunctionsPack getFPack() {
        return new FunctionsPack(this.dotFunctions, this.tuningWrap);
    }

    double[] getResultOfProcessing() {
        double[] out = new double[assemblesArr.length];
        int index = 0;
        for (WeldingAssemble wa : assemblesArr) {
            out[index] = asSolver.getAssemblyTime(wa);
            index++;
        }
        return out;
    }

    public void addAssembles(String fileSource) {
        try {
            WAReader waReader = new WAReader(fileSource);
            List<WeldingAssemble> assemblesList = waReader.getAssembles();
            assemblesArr = new WeldingAssemble[assemblesList.size()];
            for (int i = 0; i < assemblesList.size(); i++) {
                assemblesArr[i] = assemblesList.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //класс-упаковка для всех настроек расчетного движка (переменные + функции)
    public static class FunctionsPack {
        //массив "универсальных функций", действующих в рамках оборачиваемого расчетного движка
        DotFunction[] dotFunctions;
        //специальная точечная функция, оборачивающая прочие аргументы расчета
        DotFunction tuningWrap;

        public FunctionsPack(DotFunction[] dotFunctions, DotFunction tuningWrap) {
            this.dotFunctions = dotFunctions;
            this.tuningWrap = tuningWrap;
        }
    }
}
