package ru.javarush.zhemoita.caesarscipher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CaesarsCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    public void encrypt(String inputFile, String outputFile, int key) {
        char[] shiftedAlphabet = new char[ALPHABET.length];

        for (int i = 0; i < shiftedAlphabet.length - key; i++) {
            shiftedAlphabet[i] = ALPHABET[i + key];
        }

        for (int i = shiftedAlphabet.length - key, j = 0; i < shiftedAlphabet.length; i++, j++) {
            shiftedAlphabet[i] = ALPHABET[j];
        }

        try(FileReader reader = new FileReader(inputFile.trim());
            FileWriter writer = new FileWriter(outputFile.trim())) {
            char[] buffer = new char[65_536]; // 64Kb
            while (reader.ready()) {
                int real = reader.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    for (int j = 0; j < ALPHABET.length; j++) {
                        if (buffer[i] == ALPHABET[j]) {
                            buffer[i] = shiftedAlphabet[j];
                            break;
                        }
                    }
                }
                writer.write(buffer, 0, real);
            }
        } catch (IOException e) {
            System.out.println("The file at the path you specified doesn't exist! Please specify the correct path to the file.\n");
        }
    }

    public void decrypt(String inputFile, String outputFile, int key) {
        char[] shiftedAlphabet = new char[ALPHABET.length];

        for (int i = 0; i < key; i++) {
            shiftedAlphabet[i] = ALPHABET[shiftedAlphabet.length - key + i];
        }

        for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
            shiftedAlphabet[i] = ALPHABET[j];
        }

        try(FileReader reader = new FileReader(inputFile.trim());
            FileWriter writer = new FileWriter(outputFile.trim())) {
            char[] buffer = new char[65_536]; // 64Kb
            while (reader.ready()) {
                int real = reader.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    for (int j = 0; j < ALPHABET.length; j++) {
                        if (buffer[i] == ALPHABET[j]) {
                            buffer[i] = shiftedAlphabet[j];
                            break;
                        }
                    }
                }
                writer.write(buffer, 0, real);
            }
        } catch (IOException e) {
            System.out.println("The file at the path you specified doesn't exist! Please specify the correct path to the file.\n");
        }
    }

    public void bruteForce(String inputFile, String outputFile, String optionalSampleFile) {
        char[] shiftedAlphabet = new char[ALPHABET.length];
        int cipherKey = 0;

            for (int key = 0; key < ALPHABET.length; key++) {
                try (FileReader reader = new FileReader(inputFile.trim());
                     FileReader readerSample = new FileReader(optionalSampleFile.trim())) {

                    for (int i = 0; i < key; i++) {
                        shiftedAlphabet[i] = ALPHABET[shiftedAlphabet.length - key + i];
                    }

                    for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
                        shiftedAlphabet[i] = ALPHABET[j];
                    }

                    char[] buffer = new char[65_536]; // 64Kb
                    char[] bufferSample = new char[65_536];

                    while (reader.ready() && readerSample.ready()) {
                        int real = reader.read(buffer);
                        int realSample = readerSample.read(bufferSample);

                        for (int i = 0; i < buffer.length; i++) {
                            for (int j = 0; j < ALPHABET.length; j++) {
                                if (buffer[i] == ALPHABET[j]) {
                                    buffer[i] = shiftedAlphabet[j];
                                    break;
                                }
                            }
                        }

                        String bufferString = new String(buffer, 0, real);
                        String stringSample = new String(bufferSample, 0, realSample);
                        StringBuilder stringSampleFilteredBuilder = new StringBuilder();

                        String[] arrayBuffer = bufferString.split("[,.\\s]");
                        String[] arraySample = stringSample.split("[,.\\s]");

                        for (String string : arraySample) {
                            if (!string.isEmpty()) {
                                if (!stringSampleFilteredBuilder.isEmpty()) {
                                    stringSampleFilteredBuilder.append(" ");
                                }
                                stringSampleFilteredBuilder.append(string);
                            }
                        }

                        String[] stringSampleFilteredArray = stringSampleFilteredBuilder.toString().split(" ");

                        for (int i = 0; i < arrayBuffer.length; i++) {
                            for (int j = 0; j < stringSampleFilteredArray.length; j++) {
                                if (arrayBuffer[i].length() > 3 && arrayBuffer[i].equals(stringSampleFilteredArray[j])) {
                                    cipherKey = key;
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("The file at the path you specified doesn't exist! Please specify the correct path to the file.\n");
                }
            }

        decrypt(inputFile, outputFile, cipherKey);
    }

    public void statisticalAnalysis(String inputFile, String outputFile, String optionalSampleFile) {
        char[] shiftedAlphabet = new char[ALPHABET.length];
        int cipherKey = 0;
        int maxCharCounter = 0;

        for (int key = 0; key < ALPHABET.length; key++) {
            try (FileReader reader = new FileReader(inputFile.trim());
                 FileReader readerSample = new FileReader(optionalSampleFile.trim())) {
                for (int i = 0; i < key; i++) {
                    shiftedAlphabet[i] = ALPHABET[shiftedAlphabet.length - key + i];
                }

                for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
                    shiftedAlphabet[i] = ALPHABET[j];
                }

                char[] buffer = new char[65_536]; // 64Kb
                char[] bufferSample = new char[65_536];

                while (reader.ready() && readerSample.ready()) {
                    reader.read(buffer);
                    readerSample.read(bufferSample);

                    for (int i = 0; i < buffer.length; i++) {
                        for (int j = 0; j < ALPHABET.length; j++) {
                            if (buffer[i] == ALPHABET[j]) {
                                buffer[i] = shiftedAlphabet[j];
                                break;
                            }
                        }
                    }

                    int charCount = 0;
                    for (char charBuffer: buffer) {
                        if (charBuffer == ' ') charCount++;
                    }

                    if (maxCharCounter < charCount) {
                        maxCharCounter = charCount;
                        cipherKey = key;
                    }
                }
            } catch (IOException e) {
                System.out.println("The file at the path you specified doesn't exist! Please specify the correct path to the file.\n");
            }
        }

        decrypt(inputFile, outputFile, cipherKey);
    }

    public static void main(String[] args) {
        CaesarsCipher cipher = new CaesarsCipher();
        Scanner console = new Scanner(System.in);
        String inputFile = null;
        String outputFile = null;
        String optionalSampleFile = null;
        String keyMessage = String.format("Enter the encryption key from 0 to %d:", ALPHABET.length - 1);
        int key = 0;
        boolean running = true;

        while (running) {
            String menu = """
                    Please select the menu item and enter it's number:
                    1. Encoding
                    2. Decoding with a key
                    3. Brute force
                    4. Statistical analysis
                    0. Exit
                    """;

            System.out.println(menu);
            int choice = Integer.parseInt(console.nextLine());

            switch (choice) {
                case 1 -> {
                    try {
                        System.out.println("Encoding...");
                        System.out.println("Enter the path to the file to be encoded:");
                        inputFile = console.nextLine();
                        System.out.println("Specify the path to save the encrypted file:");
                        outputFile = console.nextLine();
                        System.out.println(keyMessage);
                        key = Integer.parseInt(console.nextLine());
                        cipher.encrypt(inputFile, outputFile, key);
                        System.out.print("Encoding end.\n");
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        System.out.format("The value of the key is outside the range of permissible values!\nThe key value is a natural integer in the range from 0 to %d.\nPlease enter the correct key from 0 to %d and try again.\n\n", ALPHABET.length - 1, ALPHABET.length - 1);
                    }
                }
                case 2 -> {
                    try {
                        System.out.println("Decoding...");
                        System.out.println("Enter the path to the file to be decoded:");
                        inputFile = console.nextLine();
                        System.out.println("Specify the path to save the decrypted file:");
                        outputFile = console.nextLine();
                        System.out.println(keyMessage);
                        key = Integer.parseInt(console.nextLine());
                        cipher.decrypt(inputFile, outputFile, key);
                        System.out.print("Decoding end.\n");
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        System.out.format("The value of the key is outside the range of permissible values!\nThe key value is a natural integer in the range from 0 to %d.\nPlease enter the correct key from 0 to %d and try again.\n\n", ALPHABET.length - 1, ALPHABET.length - 1);
                    }
                }
                case 3 -> {
                    System.out.println("Brute force...");
                    System.out.println("Enter the path to the file to be decoded:");
                    inputFile = console.nextLine();
                    System.out.println("Specify the path to save the decrypted file:");
                    outputFile = console.nextLine();
                    System.out.println("Enter the path to the file that contains the sample text:");
                    optionalSampleFile = console.nextLine();
                    cipher.bruteForce(inputFile, outputFile, optionalSampleFile);
                    System.out.print("The end of brute force decryption.");
                }
                case 4 -> {
                    System.out.println("Statistical analysis...");
                    System.out.println("Enter the path to the file to be decoded:");
                    inputFile = console.nextLine();
                    System.out.println("Specify the path to save the decrypted file:");
                    outputFile = console.nextLine();
                    System.out.println("Enter the path to the file that contains the sample text:");
                    optionalSampleFile = console.nextLine();
                    cipher.statisticalAnalysis(inputFile, outputFile, optionalSampleFile);
                    System.out.println("End of decryption using statistical analysis.");
                }
                case 0 -> {
                    System.out.print("Program exit...");
                    running = false;
                }
                default -> {
                    System.out.print("Unknown menu number! Please try again...\n");
                }
            }
        }
    }
}
