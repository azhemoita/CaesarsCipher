package ru.javarush.zhemoita.caesarscipher.controllers;

import ru.javarush.zhemoita.caesarscipher.constants.Constants;

import java.io.FileReader;
import java.io.IOException;

public class BruteForce {
    public void bruteForce(String inputFile, String outputFile, String optionalSampleFile) {
        char[] alphabet = Constants.ALPHABET;
        Cipher cipher = new Cipher(alphabet);
        char[] shiftedAlphabet = new char[alphabet.length];
        int cipherKey = 0;

        for (int key = 0; key < alphabet.length; key++) {
            try (FileReader reader = new FileReader(inputFile.trim());
                 FileReader readerSample = new FileReader(optionalSampleFile.trim())) {

                for (int i = 0; i < key; i++) {
                    shiftedAlphabet[i] = alphabet[shiftedAlphabet.length - key + i];
                }

                for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
                    shiftedAlphabet[i] = alphabet[j];
                }

                char[] buffer = new char[65_536]; // 64Kb
                char[] bufferSample = new char[65_536];

                while (reader.ready() && readerSample.ready()) {
                    int real = reader.read(buffer);
                    int realSample = readerSample.read(bufferSample);

                    for (int i = 0; i < buffer.length; i++) {
                        for (int j = 0; j < alphabet.length; j++) {
                            if (buffer[i] == alphabet[j]) {
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
        cipher.decrypt(inputFile, outputFile, cipherKey);
    }
}
