package ru.javarush.zhemoita.caesarscipher.controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cipher {
    private char[] alphabet;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public void encrypt(String inputFile, String outputFile, int key) {
        char[] shiftedAlphabet = new char[alphabet.length];

        for (int i = 0; i < shiftedAlphabet.length - key; i++) {
            shiftedAlphabet[i] = alphabet[i + key];
        }

        for (int i = shiftedAlphabet.length - key, j = 0; i < shiftedAlphabet.length; i++, j++) {
            shiftedAlphabet[i] = alphabet[j];
        }

        try(FileReader reader = new FileReader(inputFile.trim());
            FileWriter writer = new FileWriter(outputFile.trim())) {
            char[] buffer = new char[65_536]; // 64Kb
            while (reader.ready()) {
                int real = reader.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    for (int j = 0; j < alphabet.length; j++) {
                        if (buffer[i] == alphabet[j]) {
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
        char[] shiftedAlphabet = new char[alphabet.length];

        for (int i = 0; i < key; i++) {
            shiftedAlphabet[i] = alphabet[shiftedAlphabet.length - key + i];
        }

        for (int i = key, j = 0; i < shiftedAlphabet.length; i++, j++) {
            shiftedAlphabet[i] = alphabet[j];
        }

        try(FileReader reader = new FileReader(inputFile.trim());
            FileWriter writer = new FileWriter(outputFile.trim())) {
            char[] buffer = new char[65_536]; // 64Kb
            while (reader.ready()) {
                int real = reader.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    for (int j = 0; j < alphabet.length; j++) {
                        if (buffer[i] == alphabet[j]) {
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

}
