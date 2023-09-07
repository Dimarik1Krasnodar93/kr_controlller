import com.example.kr_controller.UtilClass

import java.util.stream.Collectors

def strPath = (System.getProperty("user.dir"))
def strPathDbProperties = strPath + "/src/main/resources/db.properties"
def envPath = strPath + "/.env"

BufferedReader reader
        = new BufferedReader(new FileReader(new File(envPath)))

def map = map = reader.lines()
        .map(str -> str.split("="))
        .collect(Collectors.toMap(e
                -> e[0].split("_")[1]
                .toLowerCase(Locale.ROOT), e -> e[1]))
reader.close()

BufferedReader br =
        new BufferedReader(new FileReader(new File(strPathDbProperties)))
list = br
            .lines()
            .collect(Collectors.toList());
br.close();

var tempList = new HashMap<Integer, String>();
System.out.println(map);
for (String str : list) {
    for (String key : map.keySet()) {
        if (str.contains(key)) {
            int i1 = str.indexOf('=');
            str2 =  str.substring(i1 + 1, str.length());
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

PrintWriter printWriter = new PrintWriter(new File(strPathDbProperties))
list.forEach(printWriter::println)
printWriter.flush()
printWriter.close()