package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        try (Stream<String[]> stream = TestCsvFileOutput.csvStream(new FileInputStream("/home/ash/testSreams/src/data.csv"))) {
            stream.parallel()
                    .map(fields -> String.join("$$", fields))
                    .forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (FileInputStream fis = new FileInputStream("/home/ash/testSreams/src/data.csv")) {
            TestCsvFileOutput.csvStream(fis).parallel()
                    .map(fields -> String.join("$$", fields))
                    .forEach(System.out::println);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String[]> stream = TestCsvFileOutput.csvStream(new FileInputStream("/home/ash/testSreams/src/data.csv"))) {
            stream.map(fields -> String.join("$$", fields))
                    .forEach(System.out::println);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
