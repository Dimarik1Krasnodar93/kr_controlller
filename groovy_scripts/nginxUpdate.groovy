import java.nio.charset.Charset
import java.util.stream.Collectors

def processController = Runtime.runtime.exec("docker inspect kr_controller_master")

def file = new File(System.getProperty("user.dir") + "/nginx/conf.d/default.conf")

BufferedReader br =
        new BufferedReader(new FileReader(file))
def listDefaultConf = br
        .lines()
        .collect(Collectors.toList());
br.close();

String osName = System.getProperty("os.name")

if (osName.toLowerCase(Locale.ROOT).contains("Windows".toLowerCase(Locale.ROOT))) {
    def processIfConfig = Runtime.runtime.exec("ipconfig")
    Charset charset = Charset.forName("866");
    BufferedReader br1 =
            new BufferedReader
                    (new InputStreamReader(processIfConfig.getInputStream(), charset))
    def list = br1
            .lines()
            .collect(Collectors.toList());
    br1.close();
    String sOne = "127.0.0.1";
    boolean b = false;
    for (String s1 : list) {
        if (s1.contains("Адаптер Ethernet Ethernet 2:") || s1.contains("Adapter Ethernet Ethernet 2:")) {
            b = true;
        }
        if (b && s1.contains("IPv4")) {
            sOne = s1.split(": ")[1]
            break;
        }
    }
    var strLine = listDefaultConf.stream().filter(str -> str.contains("server_name")).findFirst().get();
    int i = listDefaultConf.indexOf(strLine);
    listDefaultConf.remove(strLine);
    strLine = strLine.replace(strLine.substring(strLine.indexOf("server_name") + 12, strLine.length()), sOne) + ";";
    listDefaultConf.add(i, strLine)
} else {
    def processIfConfig = Runtime.runtime.exec("ifconfig")
    BufferedReader br1 =
            new BufferedReader
                    (new InputStreamReader(processIfConfig.getInputStream()))
    def list = br1
            .lines()
            .collect(Collectors.toList());
    br1.close();
    String sOne = "127.0.0.1"
    for (String s1 : list) {
        if (s1.contains("eth0")) {
            int i1 = list.indexOf(s1)
            String s2 = list.get(i1 + 1)
            sOne = s2.substring(5, s2.indexOf(" netmask"))
            break
        }
    }
    var strLine = listDefaultConf.stream().filter(str -> str.contains("server_name")).findFirst().get();
    int i = listDefaultConf.indexOf(strLine);
    listDefaultConf.remove(strLine);
    strLine = strLine.replace(strLine.substring(strLine.indexOf("server_name") + 12, strLine.length()), sOne) + ";";
    listDefaultConf.add(i, strLine)
}
def s4;
if (osName != "Windows") {
    BufferedReader br3
            = new BufferedReader
            (new InputStreamReader(processController.getInputStream()))
    def listParameters = br3.
            println("dock")
    br3.close();
    boolean b = false;
    s4 = new StringBuilder("localhost");
    for (String s : listParameters) {
        if (!b && s.contains("kr_controller_app_network")) {
            b = true;
        }
        if (b && s.contains("IPAddress")) {
            s4 = new StringBuilder(s.split(": ")[1])
            break
        }
    }
    if (s4.toString() != "localhost") {
        s4 =
                new StringBuilder(s4.toString().substring(1, s4.length() - 2));
    }
    println(s4)
}
def str = listDefaultConf
        .stream()
        .filter(str -> str.contains("proxy_pass"))
        .findFirst().get()
s5 = str.replaceAll("#АдресКонтейнера", s4.toString()).replaceAll("#ПортПриложения", "8080")
int i6 = listDefaultConf.indexOf(str);
    listDefaultConf.remove(str);
    listDefaultConf.add(i6, s5);
PrintWriter printWriter = new PrintWriter(file)
listDefaultConf.forEach(printWriter::println)
printWriter.flush()
printWriter.close()