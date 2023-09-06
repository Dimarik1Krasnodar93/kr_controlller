import com.example.kr_controller.UtilClass

import java.util.stream.Collectors

def processController = Runtime.runtime.exec("docker inspect kr_controller_master")

def processIfConfig

def file = new File(System.getProperty("user.dir") + "/nginx/conf.d/default.conf")

def listDefaultConf = UtilClass.creatingListOnFile(file)

String osName = System.getProperty("os.name")

if (osName.toLowerCase(Locale.ROOT).contains("Windows".toLowerCase(Locale.ROOT))) {
    processIfConfig = Runtime.runtime.exec("ipconfig")
    def list = UtilClass.creatingListOnInputStream(processIfConfig.getInputStream())
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
    println(sOne)
    listDefaultConf = UtilClass.injectIpInDefaultConf(listDefaultConf, sOne)
} else {
    processIfConfig = Runtime.runtime.exec("ifconfig")
    def list = UtilClass.creatingListOnInputStream(processIfConfig.getInputStream())
    String sOne = "127.0.0.1"
    for (String s1 : list) {
        if (s1.contains("eth0")) {
            int i1 = list.indexOf(s1)
            String s2 = list.get(i1 + 1)
            sOne = s2.substring(5, s2.indexOf(" netmask"))
            break
        }
    }
    println(sOne)
    listDefaultConf = UtilClass.injectIpInDefaultConf(listDefaultConf, sOne)
}
    BufferedReader br3 = new BufferedReader(new InputStreamReader(processController.getInputStream()))
    def listParameters = br3.lines().collect(Collectors.toList());
    boolean b = false;

    StringBuilder s4 = new StringBuilder("127.0.0.1");
    for (String s : listParameters) {
        if (!b && s.contains("kr_controller_app_network")) {
            b = true;
        }
        if (b && s.contains("IPAddress")) {
            s4 = new StringBuilder(s.split(": ")[1])
            break
        }
    }
    s4 = s4.substring(1, s4.length() - 2);
    println(s4);
    def str = listDefaultConf
            .stream()
            .filter(str -> str.contains("proxy_pass"))
            .findFirst().get()
    s5 = str
            .replaceAll("#АдресКонтейнера", s4.toString()).replaceAll("#ПортПриложения", "8080")
    int i6 = listDefaultConf.indexOf(str);
    listDefaultConf.remove(str);
    listDefaultConf.add(i6, s5);

    UtilClass.writeInTheFile(file, listDefaultConf)
