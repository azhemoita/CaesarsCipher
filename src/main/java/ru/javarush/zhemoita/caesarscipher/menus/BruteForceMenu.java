package ru.javarush.zhemoita.caesarscipher.menus;

import ru.javarush.zhemoita.caesarscipher.controllers.BruteForce;

import java.util.Scanner;

public class BruteForceMenu implements MenuStrategy {
    private BruteForce bruteForce = new BruteForce();
    private Scanner console = new Scanner(System.in);

    @Override
    public void menu(String inputFile, String outputFile, String optionalSampleFile) {
        System.out.println("Brute force...");
        System.out.println("Enter the path to the file to be decoded:");
        inputFile = console.nextLine();
        System.out.println("Specify the path to save the decrypted file:");
        outputFile = console.nextLine();
        System.out.println("Enter the path to the file that contains the sample text:");
        optionalSampleFile = console.nextLine();
        bruteForce.bruteForce(inputFile, outputFile, optionalSampleFile);
        System.out.print("The end of brute force decryption.\n");
    }
}
