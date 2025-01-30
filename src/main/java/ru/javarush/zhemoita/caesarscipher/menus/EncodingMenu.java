package ru.javarush.zhemoita.caesarscipher.menus;

import ru.javarush.zhemoita.caesarscipher.controllers.Cipher;
import ru.javarush.zhemoita.caesarscipher.constants.Constants;

import java.util.Scanner;

public class EncodingMenu implements MenuStrategy {
    private Cipher cipher = new Cipher(Constants.ALPHABET);
    private Scanner console = new Scanner(System.in);

    @Override
    public void menu(String inputFile, String outputFile, String encodingKey) {
        try {
            System.out.println("Encoding...");
            System.out.println("Enter the path to the file to be encoded:");
            inputFile = console.nextLine();
            System.out.println("Specify the path to save the encrypted file:");
            outputFile = console.nextLine();
            System.out.println(String.format("Enter the encryption key from 0 to %d:", Constants.ALPHABET.length - 1));
            encodingKey = console.nextLine();
            int key = Integer.parseInt(encodingKey);
            cipher.encrypt(inputFile, outputFile, key);
            System.out.print("Encoding end.\n");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.format("The value of the key is outside the range of permissible values!\nThe key value is a natural integer in the range from 0 to %d.\nPlease enter the correct key from 0 to %d and try again.\n\n", Constants.ALPHABET.length - 1, Constants.ALPHABET.length - 1);
        }
    }
}
