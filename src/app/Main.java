package app;

import java.util.Random;

public class Main {

    static int[][] arrayNums;

    enum setRowCol {odd, even}

    public static void main(String[] args) {
        int loopRowIndex;
        int loopColumnIndex;
        int oddRowsSuma;
        int evenRowsSuma;
        long oddColumnsProduct;
        long evenColumnsProduct;
        String matrixText;
        String prefixMagicSquare;

        //масив для випробувань
        //int[][] arrayNums = {{5,5,5,5},{5,5,5,5},{5,5,5,5},{5,5,5,5}};

        //Створення масиву, ініціалізація Random
        arrayNums = new int[4][4];
        Random rnd = new Random();

        //Присвоєння випадкових чисел і зміна знаку випадковим способом
        for (loopRowIndex = 0; loopRowIndex < arrayNums.length; loopRowIndex++)
            for (loopColumnIndex = 0; loopColumnIndex < arrayNums.length; loopColumnIndex++)
                arrayNums[loopRowIndex][loopColumnIndex] = rnd.nextInt(50) + 1;

        //Перевірка
        matrixText = formatMatrixText(arrayNums);
        oddRowsSuma = calculateRowsItemsSuma(setRowCol.odd, arrayNums);
        evenRowsSuma = calculateRowsItemsSuma(setRowCol.even, arrayNums);
        oddColumnsProduct = calculateColumnsItemsProduct(setRowCol.odd, arrayNums);
        evenColumnsProduct = calculateColumnsItemsProduct(setRowCol.even, arrayNums);
        if (isMagicSquare(arrayNums)) prefixMagicSquare = " ";
        else prefixMagicSquare = " не ";

        //вивід
        System.out.printf("Матриця 4x4: \n" + matrixText + "\n" +
                          "Сума елементів у парних рядках (рядок 0, 2): " + evenRowsSuma + "\n" +
                          "Сума елементів у непарних рядках (рядок 1, 3): " + oddRowsSuma + "\n" +
                          "Добуток елементів у парних стовпцях (стовпець 0, 2): " + evenColumnsProduct + "\n" +
                          "Добуток елементів у непарних стовпцях (стовпець 1, 3): " + oddColumnsProduct + "\n" +
                          "\nМатриця%sє магічним квадратом.", prefixMagicSquare);

    }

    private static String formatMatrixText(int[][] arrayToCalculate) {
        int loopRowIndex;
        int loopColumnIndex;
        String textOut;
        String tempText;

        textOut = "";
        for (loopRowIndex = 0; loopRowIndex < arrayToCalculate.length; loopRowIndex++) {
            for (loopColumnIndex = 0; loopColumnIndex < arrayToCalculate.length; loopColumnIndex++) {
                tempText = "  " + arrayToCalculate[loopRowIndex][loopColumnIndex];
                //вирівнювання елементів матриці по правому краю
                tempText = tempText.substring(tempText.length() - 2);
                textOut += (tempText + " ");
            }
            textOut += "\n";
        }
        return textOut;
    }

    private static int calculateRowItemsSuma(int rowToCalculate, int[][] arrayToCalculate) {
        int loopIndex;
        int suma;

        suma = 0;

        if (rowToCalculate >= arrayToCalculate.length) return suma;
        else {
            for (loopIndex = 0; loopIndex < arrayToCalculate[rowToCalculate].length; loopIndex++)
                suma += arrayToCalculate[rowToCalculate][loopIndex];
            return suma;
        }
    }

    private static int calculateRowsItemsSuma(setRowCol setRow, int[][] arrayToCalculate) {
        int loopIndex;
        int suma;

        suma = 0;
        //взалежності від setRow (парні непарні присвоєння початкового індекса)
        if (setRow == setRowCol.even) loopIndex = 0;
        else loopIndex = 1;

        while (loopIndex < arrayToCalculate.length) {
            suma += calculateRowItemsSuma(loopIndex, arrayToCalculate);
            loopIndex += 2;
        }
        return suma;
    }

    private static long calculateColumnItemsProduct(int columnToCalculate, int[][] arrayToCalculate) {
        int loopIndex;
        long Product;

        Product = 1;

        if (arrayToCalculate.length < 1) return Product;
        else {
            for (loopIndex = 0; loopIndex < arrayToCalculate.length; loopIndex++)
                //умова на той випадок якщо двовимірний масив зубатий (неоднорозмірний)
                if (arrayToCalculate[loopIndex].length >= columnToCalculate)
                    Product *= arrayToCalculate[loopIndex][columnToCalculate];

            return Product;
        }
    }

    private static long calculateColumnsItemsProduct(setRowCol setCol, int[][] arrayToCalculate) {
        int loopIndex;
        long Product;

        Product = 1;
        //взалежності від setRow (парні непарні присвоєння початкового індекса)
        if (setCol == setRowCol.even) loopIndex = 0;
        else loopIndex = 1;

        while (loopIndex < arrayToCalculate.length) {
            Product *= calculateColumnItemsProduct(loopIndex, arrayToCalculate);
            loopIndex += 2;
        }
        return Product;
    }

    private static boolean isMagicSquare(int[][] arrayToCalculate) {
        int loopIndex;
        int loopRowIndex;
        int loopColumnIndex;
        int magicSuma;
        int tempSuma;

        //перевірка на той випадок якщо двовимірний масив зубатий (неоднорозмірний) або не квадратний
        for (loopRowIndex = 0; loopRowIndex < arrayToCalculate.length; loopRowIndex++)
            if (arrayToCalculate[loopRowIndex].length != arrayToCalculate.length) return false;

        magicSuma = 0;
        //Знаходження магічної суми на базі 1 рядка
        for (loopColumnIndex = 0; loopColumnIndex < arrayToCalculate.length; loopColumnIndex++)
            magicSuma += arrayToCalculate[0][loopColumnIndex];

        //Перевірка решти рядків, вихід якщо не співпадає
        for (loopRowIndex = 1; loopRowIndex < arrayToCalculate.length; loopRowIndex++) {
            tempSuma = 0;
            for (loopColumnIndex = 0; loopColumnIndex < arrayToCalculate.length; loopColumnIndex++)
                tempSuma += arrayToCalculate[loopRowIndex][loopColumnIndex];
            if (tempSuma != magicSuma) return false;
        }
        //Перевірка стовпців, вихід якщо не співпадає
        for (loopColumnIndex = 0; loopColumnIndex < arrayToCalculate.length; loopColumnIndex++) {
            tempSuma = 0;
            for (loopRowIndex = 0; loopRowIndex < arrayToCalculate.length; loopRowIndex++)
                tempSuma += arrayToCalculate[loopRowIndex][loopColumnIndex];
            if (tempSuma != magicSuma) return false;
        }
        //Перевірка діагоналей, вихід якщо не співпадає
        tempSuma = 0;
        for (loopIndex = 0; loopIndex < arrayToCalculate.length; loopIndex++) {
            loopRowIndex = loopIndex;
            loopColumnIndex = loopIndex;
            tempSuma += arrayToCalculate[loopRowIndex][loopColumnIndex];
        }
        if (tempSuma != magicSuma) return false;

        tempSuma = 0;
        for (loopIndex = 0; loopIndex < arrayToCalculate.length; loopIndex++) {
            loopRowIndex = loopIndex;
            loopColumnIndex = arrayToCalculate.length - 1 - loopIndex;
            tempSuma += arrayToCalculate[loopRowIndex][loopColumnIndex];
        }
        return tempSuma == magicSuma;
    }
}
