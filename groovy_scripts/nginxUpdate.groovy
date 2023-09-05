import com.example.kr_controller.UtilClass

import java.util.stream.Collectors

def processController = Runtime.runtime.exec("docker inspect kr_controller_master");
def processIfConfig = Runtime.runtime.exec("ipconfig")
def file = new File(System.getProperty("user.dir") + "/nginx/conf.d/default.conf")

BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)))

def list = UtilClass.creatingListOnInputStream(processIfConfig.getInputStream())
if (list.get(0) != "not found") {

} else {
    processIfConfig = Runtime.runtime.exec("ifconfig")
    list = UtilClass.creatingListOnInputStream(processIfConfig.getInputStream())
    String sOne = "127.0.0.1";
    for (String s1 : list) {
        if (s1.contains("eth0")) {
            int i1 = list.indexOf(s1);
            String s2 = list.get(i1 + 1);
            sOne = s2.substring(5, s2.indexOf(" netmask"))
            break
        }
    }
    println(sOne)
    def listDefaultConf = br2.lines().collect(Collectors.toList())
    def strLine = br2.lines().filter(str -> str.contains("server_name")).findFirst().get();
    def line = new StringBuilder(strLine);
    line = line.replaceAll(line.substring(line.indexOf("server_name") + 12, line.length()), sOne);
    int strLineIndex = listDefaultConf.indexOf(strLine);
    listDefaultConf.remove(strLineIndex)
    listDefaultConf.add(line.toString())
    br2.close();
}
    BufferedReader br3 = new BufferedReader(new InputStreamReader(processController.getInputStream()));

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
