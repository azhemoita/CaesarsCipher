package ru.javarush.zhemoita.caesarscipher.controllers;

import ru.javarush.zhemoita.caesarscipher.constants.Constants;

import java.io.FileReader;
import java.io.IOException;

public class StatisticalAnalyzer {
    public void statisticalAnalysis(String inputFile, String outputFile, String optionalSampleFile) {
        char[] alphabet = Constants.ALPHABET;
        Cipher cipher = new Cipher(alphabet);
        char[] shiftedAlphabet = new char[alphabet.length];
        int cipherKey = 0;
        int maxCharCounter = 0;

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
                    reader.read(buffer);
                    readerSample.read(bufferSample);

                    for (int i = 0; i < buffer.length; i++) {
                        for (int j = 0; j < alphabet.length; j++) {
                            if (buffer[i] == alphabet[j]) {
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

        cipher.decrypt(inputFile, outputFile, cipherKey);
    }

}
