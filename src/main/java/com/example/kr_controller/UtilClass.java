package com.example.kr_controller;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class UtilClass {

    public static void writeInTheFile(File file, List<String> list) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            list.forEach(printWriter::println);
            printWriter.flush();
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    public static Map<String, String> creatingEnvMap(File file) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            map = reader.lines()
                        .map(str -> str.split("="))
                        .collect(Collectors.toMap(e
                                -> e[0].split("_")[1]
                                .toLowerCase(Locale.ROOT), e -> e[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    public static List<String> creatingListOnFile(File file) throws FileNotFoundException {
        return createListOnReader(new FileReader(file));
    }

    public static List<String> creatingListOnInputStream(InputStream inputStream) {
        return createListOnReader(new InputStreamReader(inputStream));
    }

    private static List<String> createListOnReader(Reader reader) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br =  new BufferedReader(reader)) {
            list = br
                    .lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return list;
    }
    public static List<String> injectEnvParameterInFileAsList(List<String> fileAsList,
                                                              Map<String, String> parameterMap,
                                                              BiPredicate<String, String> predicate,
                                                              Function<String, String> function) {
        var tempList = new HashMap<Integer, String>();
        System.out.println(parameterMap);
        for (String str : fileAsList) {
            for (String key : parameterMap.keySet()) {
                if (predicate.test(str, key)) {
                    String str2 = function.apply(str);
                    int i3 = fileAsList.indexOf(str);
                    str = str.replaceAll(str2, parameterMap.get(key));
                    tempList.put(i3, str);
                }
            }
        }
        System.out.println(tempList);
        tempList.forEach((integer, s) -> {
            fileAsList.remove(integer.intValue());
            fileAsList.add(integer, s);
        });
        return fileAsList;
    }

    public List<String> injectIpInDefaultConf(List<String> fileAsList, String ip) {
        var strLine = fileAsList.stream().filter(str -> str.contains("server_name")).findFirst().get();
        strLine = strLine.replaceAll(strLine.substring(strLine.indexOf("server_name") + 12, strLine.length()), ip);
        fileAsList.remove(strLine);
        fileAsList.add(strLine);
        return fileAsList;
    }
}
