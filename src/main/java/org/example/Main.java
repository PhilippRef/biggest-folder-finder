package org.example;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
    public static void main(String[] args) {


//        MyThread thread = new MyThread(1);
//        MyThread thread2 = new MyThread(2);
//
//        thread.start(); //запускаем потоки
//        thread2.start();

        System.out.println(getHumanReadableSize(1025));
        System.exit(0); //на этой строке завершается программа

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
            for (int i = 0; i < sizeMultipliers.length; i++) {
                double value = size / Math.pow(1024, i);
                if(value < 1024) {
                    return Math.round (value) + "" + sizeMultipliers[i] + (i > 0 ? "b" : "");
                }
            }
            return "Very big";
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
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2multiplier.put(sizeMultipliers[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }
}