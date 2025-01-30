package ru.javarush.zhemoita.caesarscipher.menus;

public interface MenuStrategy {
    void menu(String inputFile, String outputFile, String keyOrOptionalSampleFile);
}
