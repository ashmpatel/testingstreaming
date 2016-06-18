package com.test;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by ash on 18/06/16.
 */
public class TestCsvFileOutput {

    public static Stream<String[]> csvStream(InputStream in) {
        final CSVReader cr = new CSVReader(new InputStreamReader(in));
        return StreamSupport.stream(new CsvSpliterator(cr), false).onClose(() -> {
            try { cr.close(); } catch (IOException e) { throw new UncheckedIOException(e); }
        });
    }


}
