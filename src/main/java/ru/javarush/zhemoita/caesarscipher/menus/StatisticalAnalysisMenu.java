package ru.javarush.zhemoita.caesarscipher.menus;

import ru.javarush.zhemoita.caesarscipher.controllers.StatisticalAnalyzer;

import java.util.Scanner;

public class StatisticalAnalysisMenu implements MenuStrategy {
    private StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();
    private Scanner console = new Scanner(System.in);

    @Override
    public void menu(String inputFile, String outputFile, String optionalSampleFile) {
        System.out.println("Statistical analysis...");
        System.out.println("Enter the path to the file to be decoded:");
        inputFile = console.nextLine();
        System.out.println("Specify the path to save the decrypted file:");
        outputFile = console.nextLine();
        System.out.println("Enter the path to the file that contains the sample text:");
        optionalSampleFile = console.nextLine();
        statisticalAnalyzer.statisticalAnalysis(inputFile, outputFile, optionalSampleFile);
        System.out.println("End of decryption using statistical analysis.");
    }
}
