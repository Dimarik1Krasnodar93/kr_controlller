import java.nio.charset.Charset
import java.util.stream.Collectors

String commandToJoinDb = "docker container exec -it db psql -U postgres -w -d  postgres"
Charset charset = System.getProperty("os.name").contains("Windows") ? Charset.forName("866")
        : Charset.defaultCharset()
def process = Runtime.getRuntime().exec(commandToJoinDb)
BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
BufferedReader normalBuffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
List<String> normalList = normalBuffer.lines().collect(Collectors.toList())
normalList.forEach(str -> println(str))
errorBuffer.lines().forEach(str -> println(str))
if (normalList.size() != 0 && errorBuffer.lines().count() == 0 && normalList.last().contains("psql")) {
    def strPath = (System.getProperty("user.dir"))
    def envPath = strPath + "/.env"

    BufferedReader reader
            = new BufferedReader(new FileReader(new File(envPath)))

    def map = reader.lines()
            .map(str -> str.split("="))
            .collect(Collectors.toMap(e
                    -> e[0].split("_")[1]
                    .toLowerCase(Locale.ROOT), e -> e[1]))
    reader.close()
    println(map)
    String command = "CREATE DATABASE " + map.get("db") + ";"
    process = Runtime.runtime.exec(command)
    br = new BufferedReader(new InputStreamReader(process.getInputStream()));

    if (!br.lines().findFirst().get().toLowerCase(Locale.ROOT).contains("error")) {
        println("======СОЗДАНА БАЗА ДАННЫХ " + map.get("db") + "======")
    } else {
        println("======БАЗА ДАННЫХ " + map.get("db") + " УЖЕ СУЩЕСТВУЕТ.======")
    }

    command = "CREATE USER " + map.get("user") + " WITH PASSWORD \'" + map.get("password") + "\' SUPERUSER;"
    process = Runtime.runtime.exec(command);
    br = new BufferedReader(new InputStreamReader(process.getInputStream()));

    if (!br.lines().findFirst().get().toLowerCase(Locale.ROOT).contains("error")) {
        println("======СОЗДАН ПОЛЬЗОВАТЕЛЬ " + map.get("user") + "======")
    } else {
        boolean start = true;
        while (start) {
            println("======ПОЛЬЗОВАТЕЛЬ " + map.get("user") + " УЖЕ СУЩЕСТВУЕТ.======")
            Scanner scanner = new Scanner(System.in);
            println("Изменить пароль? Y/N")
            String s = scanner.nextLine();
            if (s.toLowerCase(Locale.ROOT) == "y") {
                command = "ALTER USER " + map.get("user") + "WITH PASSWORD \'" + map.get("password") + "\';"
                Runtime.runtime.exec(command);
            } else if (s.toLowerCase(Locale.ROOT) == "n") {
                "======ОК======"
                start = false;
            }
        }
    }
} else {
    println("======Ошибка, контейнера не существует======")
}