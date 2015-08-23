package com.hj.wwtbam.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by heiko on 19.08.15.
 */
public class FileUtil {

    public static List<List<String>> readFromCsvFile(File file) throws IOException {

        List<List<String>> rows = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            List<String> row = Arrays.asList(line.split(";"));
            rows.add(row);
        }

        return rows;
    }

}
