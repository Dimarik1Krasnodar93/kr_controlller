println '123'
String strPath = "15"
//String strPath = (System.getProperty("user.dir"))
def srtPathDbProperties = strPath + "/src/main/resources/hibernate.cfg.xml"
//
//def srtPathDbProperties = strPath + "/src/main/resources/hibernate.cfg.xml"
//
//def envPath = strPath + "/.env"
//
//BufferedReader reader
//        = new BufferedReader(new FileReader(new File(envPath)))
//
//def map = reader.lines()
//        .map(str -> str.split("="))
//        .collect(Collectors.toMap(e
//                -> e[0].split("_")[1]
//                .toLowerCase(Locale.ROOT), e -> e[1]))
//reader.close()
//
//BufferedReader br =
//        new BufferedReader(new FileReader(new File(srtPathDbProperties)))
//def list = br
//        .lines()
//        .collect(Collectors.toList());
//br.close();

//var tempList = new HashMap<Integer, String>();
//System.out.println(map);
//for (String str : list) {
//    for (String key : map.keySet()) {
//        if (str.contains(key) && str.contains("property") && key != "db") {
//            int i1 = str.indexOf('>');
//            int i2 = str.lastIndexOf('<');
//            def str2 = str.substring(i1 + 1, i2);
//            int i3 = list.indexOf(str);
//            str = str.replace(str2, map.get(key));
//            tempList.put(i3, str);
//        } else if (str.contains("property") && str.contains("url") && key == "db") {
//            println(str)
//            int i1 = str.indexOf('>');
//            int i2 = str.lastIndexOf('<');
//            def str2 = str.substring(i1 + 1, i2);
//            println(str2)
//            def str3 = str2.substring(str2.lastIndexOf("/") + 1, str2.length())
//            println(str3)
//            def str4 = str2.replaceAll(str3, map.get(key));
//            int i3 = list.indexOf(str);
//            tempList.put(i3, str.replace(str2, str4));
//        }
//    }
//}
//System.out.println(tempList);
//tempList.forEach((integer, s) -> {
//    list.remove(integer.intValue());
//    list.add(integer, s);
//});
//
//PrintWriter printWriter = new PrintWriter(new File(srtPathDbProperties))
//list.forEach(printWriter::println)
//printWriter.flush()
//printWriter.close()