import com.example.kr_controller.UtilClass

def strPath = (System.getProperty("user.dir"))

def srtPathDbProperties = strPath + "/src/main/resources/hibernate.cfg.xml"

def envPath = strPath + "/.env"

def map = UtilClass.creatingEnvMap(new File(envPath))

def list = UtilClass.creatingListOnFile(new File(srtPathDbProperties))
println(list)
list = UtilClass.injectEnvParameterInFileAsList(list, map,
        (str, key) -> str.contains(key),
        str -> {
    int i1 = str.indexOf('>');
    int i2 = str.lastIndexOf('<');
    return str.substring(i1 + 1, i2);
})

UtilClass.writeInTheFile(new File(srtPathDbProperties), list);