package ru.javarush.zhemoita.caesarscipher;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class CaesarsCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    //TODO Methods for brute force, statistical analysis

    public void encrypt(String inputFile, String outputFile, int key) {
        char[] shiftedAlphabet = new char[ALPHABET.length];
        int iterator = 0;

        for (int i = 0; i < shiftedAlphabet.length - key; i++) {
            shiftedAlphabet[i] = ALPHABET[i + key];
        }

        for (int i = shiftedAlphabet.length - key; i < shiftedAlphabet.length; i++) {
            shiftedAlphabet[i] = ALPHABET[iterator];
            iterator++;
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
            throw new RuntimeException(e.getMessage());
        }
    }

    public void decrypt(String inputFile, String outputFile, int key) {
        char[] shiftedAlphabet = new char[ALPHABET.length];
        int iterator = 0;

        for (int i = 0; i < key; i++) {
            shiftedAlphabet[i] = ALPHABET[shiftedAlphabet.length - key + i];
        }

        for (int i = key; i < shiftedAlphabet.length; i++) {
            shiftedAlphabet[i] = ALPHABET[iterator];
            iterator++;
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
            throw new RuntimeException(e.getMessage());
        }
    }

    public void bruteForce(String inputFile, String outputFile, String optionalSampleFile) {
        //TODO Brute force realization
        char[] shiftedAlphabet = new char[ALPHABET.length];

        for (int key = 0; key < ALPHABET.length; key++) {
            for (int i = 0; i < key; i++) {
                shiftedAlphabet[i] = ALPHABET[shiftedAlphabet.length - key + i];
            }

            for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
                shiftedAlphabet[i] = ALPHABET[j];
            }

            try(FileReader reader = new FileReader(inputFile.trim());
                FileReader readerSample = new FileReader(optionalSampleFile.trim());
                FileWriter writer = new FileWriter(outputFile.trim())) {
//                char[] buffer = new char[65_536]; // 64Kb
//                char[] bufferSample = new char[65_536];
                char[] buffer = new char[1024];
                char[] bufferSample = new char[1024];

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
                    // If buffer contains
                    String bufferString = new String(buffer);
                    int realSample = readerSample.read(bufferSample);
                    String stringSample = new String(bufferSample);

                    String[] arrayBuffer = bufferString.split(" ");
                    String[] arraySample = stringSample.split(" ");

//                    for (int i = 0; i < arraySample.length; i++) {
//                        if (bufferString.contains(arraySample[i])) {
//                            writer.write(buffer, 0, real);
//                            break;
//                        }
//                    }


                    for (int i = 0; i < arrayBuffer.length; i++) {
                        for (int j = 0; j < arraySample.length; j++) {
                            if (arrayBuffer[i].equals(arraySample[j])) {
//                                newAlphabet = shiftedAlphabet;
//                                newKey = key;
//                                writer.write(buffer,0, real);
//                                break;
                            }
                        }
                    }
                }

                decrypt(inputFile, outputFile, key);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void statisticalAnalysis(String inputFile, String outputFile, String optionalSampleFile) {
        //TODO Statistical analysis realization
    }

    //TODO Auxiliary methods: validateInput(), createAlphabet(), shiftCharacter(), readFile(), writeFile()

    public static void main(String[] args) {
        CaesarsCipher cipher = new CaesarsCipher();
        //TODO Menu logic
        // 1. Encoding
        // 2. Decoding with a key
        // 3. Brute force
        // 4. Statistical analysis
        // 0. Exit

        // Example of call method encoding
        // cipher.encrypt("input.txt", "output.txt", 3);

        CaesarsCipher cypher = new CaesarsCipher();
        Scanner console = new Scanner(System.in);
        String inputFile = null;
        String outputFile = null;
        String optionalSampleFile = null;
        String keyMessage = String.format("Enter the encryption key from 0 to %d:", ALPHABET.length - 1);
        int key = 0;
        boolean toggle = true;
        String menu = """
                Please select the menu item and enter it's number:
                1. Encoding
                2. Decoding with a key
                3. Brute force
                4. Statistical analisys
                0. Exit
                """;

        System.out.println(menu);
        int choose = Integer.parseInt(console.nextLine());

        switch (choose) {
            case 1 -> {
                while (true) {
                    System.out.println("Decoding...");
                    System.out.println("Enter the path to the file to be encoded:");
                    inputFile = console.nextLine();
                    System.out.println("Specify the path to save the encrypted file:");
                    outputFile = console.nextLine();
                    System.out.println(keyMessage);
                    key = Integer.parseInt(console.nextLine());
//                    key = console.nextInt();

                    if (key < 0 && key > ALPHABET.length - 1) {
                        System.out.format("Enter the key from 0 to %d", ALPHABET.length - 1);
                    } else {
                        break;
                    }
                }
                // C:\\Users\\zhem_\\Desktop\\file.txt
                // C:\\Users\\zhem_\\Desktop\\filec.txt
                cypher.encrypt(inputFile, outputFile, key);
                System.out.print("File successfully encrypted!");
            }
            case 2 -> {
                System.out.println("Decoding...");
                System.out.println("Enter the path to the file to be decoded:");
                inputFile = console.nextLine();
                System.out.println("Specify the path to save the decrypted file:");
                outputFile = console.nextLine();
                System.out.println(keyMessage);
                key = Integer.parseInt(console.nextLine());
                cypher.decrypt(inputFile, outputFile, key);
                System.out.print("File successfully decrypted!");
            }
            case 3 -> {
                System.out.println("Brute force ...");
                System.out.println("Enter the path to the file to be decoded:");
                inputFile = console.nextLine();
                System.out.println("Specify the path to save the decrypted file:");
                outputFile = console.nextLine();
                System.out.println("Enter the path to the file that contains the sample text:");
                optionalSampleFile = console.nextLine();
                cypher.bruteForce(inputFile, outputFile, optionalSampleFile);
                System.out.print("File successfully decrypted with brute force!");
            }
            case 4 -> {
                System.out.println("Stat analysis");
            }
            case 0 -> {
                System.out.print("Program exit...");
                System.exit(0);
            }
            default -> {
                System.out.print("Unknown menu number! Program exit...");
                System.exit(0);
            }
        }
    }
}
