import java.util.stream.Collectors

def strPath = (System.getProperty("user.dir"))

def srtPathDbProperties = strPath + "/src/main/resources/hibernate.cfg.xml"

def envPath = strPath + "/.env"

BufferedReader reader
        = new BufferedReader(new FileReader(new File(envPath)))

def map = reader.lines()
        .map(str -> str.split("="))
        .collect(Collectors.toMap(e
                -> e[0].split("_")[1]
                .toLowerCase(Locale.ROOT), e -> e[1]))
reader.close()

BufferedReader br =
        new BufferedReader(new FileReader(new File(srtPathDbProperties)))
def list = br
        .lines()
        .collect(Collectors.toList());
br.close();

var tempList = new HashMap<Integer, String>();
System.out.println(map);
for (String str : list) {
    for (String key : map.keySet()) {
        if (str.contains(key) && str.contains("property")) {
            int i1 = str.indexOf('>');
            int i2 = str.lastIndexOf('<');
            str2 = str.substring(i1 + 1, i2);
            int i3 = list.indexOf(str);
            str = str.replace(str2, map.get(key));
            tempList.put(i3, str);
        }
    }
}
System.out.println(tempList);
tempList.forEach((integer, s) -> {
    list.remove(integer.intValue());
    list.add(integer, s);
});

PrintWriter printWriter = new PrintWriter(new File(srtPathDbProperties))
list.forEach(printWriter::println)
printWriter.flush()
printWriter.close()