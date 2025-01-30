package ru.javarush.zhemoita.caesarscipher;

import ru.javarush.zhemoita.caesarscipher.menus.*;

import java.util.Scanner;

public class CaesarsCipher {
    private MenuContext context = new MenuContext();
    private Scanner console = new Scanner(System.in);
    private String inputFile = null;
    private String outputFile = null;
    private String keyOrOptionalSampleFile = null;
    private boolean running = true;

    public CaesarsCipher() {
        while (running) {
            String menuList = """
                    Please select the menu item and enter it's number:
                    1. Encoding
                    2. Decoding with a key
                    3. Brute force
                    4. Statistical analysis
                    0. Exit
                    """;

            System.out.println(menuList);
            int choice = Integer.parseInt(console.nextLine());

            switch (choice) {
                case 1 -> {
                    context.setStrategy(new EncodingMenu());
                    context.executeMenuItem(inputFile, outputFile, keyOrOptionalSampleFile);
                }
                case 2 -> {
                    context.setStrategy(new DecodingMenu());
                    context.executeMenuItem(inputFile, outputFile, keyOrOptionalSampleFile);
                }
                case 3 -> {
                    context.setStrategy(new BruteForceMenu());
                    context.executeMenuItem(inputFile, outputFile, keyOrOptionalSampleFile);
                }
                case 4 -> {
                    context.setStrategy(new StatisticalAnalysisMenu());
                    context.executeMenuItem(inputFile, outputFile, keyOrOptionalSampleFile);
                }
                case 0 -> {
                    System.out.print("Program exit...");
                    running = false;
                }
                default ->
                        System.out.print("Unknown menu number! Please try again...\n");
            }
        }
    }
}
