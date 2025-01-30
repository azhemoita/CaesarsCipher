package ru.javarush.zhemoita.caesarscipher.menus;

public class MenuContext {
    private MenuStrategy strategy;

    public void setStrategy(MenuStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeMenuItem(String inputFile, String outputFile, String keyOrOptionalSampleFile) {
        strategy.menu(inputFile, outputFile, keyOrOptionalSampleFile);
    }
}
