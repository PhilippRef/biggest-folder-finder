package org.example;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {


//        MyThread thread = new MyThread(1);
//        MyThread thread2 = new MyThread(2);
//
//        thread.start(); //запускаем потоки
//        thread2.start();

        System.out.println(getSizeFromHumanReadable("235K"));
        System.exit(0); //на этой строке завершается программа

        String folderPath = "D:/Programs/BITZER/";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool(); //позволяет запускать множество потоков. Управляет количеством потоков.
        long size = pool.invoke(calculator); //возвращает размер
        System.out.println(size);
        //       System.out.println(getFolderSize(file));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");

        System.out.println("экспонент " + Math.getExponent(5000 / 1024));

    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        int index = Math.getExponent(size / 1024);
        if (index < 10) {
            double value = size / 1024;
            System.out.printf("%.2f Kb\n", value);
        }
        if (index >= 10 && index < 20) {
            double value = size / Math.pow(1024, 2);
            System.out.printf("%.2f Mb\n", value);
        }
        if (index >= 20 && index < 30) {
            double value = size / Math.pow(1024, 3);
            System.out.printf("%.2f Gb\n", value);
        }
        return "";
    }

    public static long getSizeFromHumanReadable(String size) {
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+", "")
                .charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.valueOf(size
                .replaceAll("[^0-9]", "")); //убираем все, что не цифры
        return length;
    }

    private static HashMap<Character, Integer> getMultipliers() {
        char[] multipliers = {'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < multipliers.length; i++) {
            char2multiplier.put(multipliers[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }
}