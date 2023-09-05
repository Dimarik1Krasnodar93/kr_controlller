import com.example.kr_controller.UtilClass

def strPath = (System.getProperty("user.dir"))
def strPathDbProperties = strPath + "/src/main/resources/db.properties"
def envPath = strPath + "/.env"

def map = UtilClass.creatingEnvMap(new File(envPath));

def list = UtilClass.creatingListOnFile(new File(strPathDbProperties))

list = UtilClass.injectEnvParameterInFileAsList(list, map, String::contains, str -> {
    int i1 = str.indexOf('=');
    return str.substring(i1 + 1, str.length());
});

UtilClass.writeInTheFile(new File(strPathDbProperties), list)
