import java.nio.charset.Charset
import java.util.stream.Collectors

Charset charset = System.getProperty("os.name").contains("Windows") ? Charset.forName("866")
        : Charset.defaultCharset()

def process = Runtime.getRuntime().exec("docker inspect db");

def buffer = new BufferedReader(new InputStreamReader(process.getInputStream()))

boolean b = buffer.lines()
        .filter(str -> str.contains("Status"))
        .map(str -> str.split(": \"")[1])
        .findFirst()
        .get().contains("running");

if (b) {
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
    String firstPartOnResponse = "docker container exec db ";
    StringBuilder command = new StringBuilder(firstPartOnResponse).append("createdb ")
            .append(map.get("db"))
            .append(" -U postgres -w -d postgres");
    String commando = "docker container exec db psql -c \"create database project_db\" -U postgres -w -d postgres -e"
    println("---COMMAND: " + commando)
    def proc = Runtime.getRuntime().exec(commando)
    def br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    def brError = new BufferedReader(new InputStreamReader(proc.getErrorStream()))
    br.lines().forEach(str -> println(str))
    if (br.lines().findFirst().isPresent() &&
            !br.lines().findFirst().get().toLowerCase(Locale.ROOT).contains("error")) {
        println("======СОЗДАНА БАЗА ДАННЫХ " + map.get("db") + " ======")
    } else if (brError.lines().count() != 0) {
        println("======БАЗА ДАННЫХ " + map.get("db") + " УЖЕ СУЩЕСТВУЕТ.======")
        brError.lines().forEach(str -> println(str))
    }
    br.close()
    print("UserPoint")
    command = new StringBuilder(firstPartOnResponse).append(" createuser -l  -s -U postgres ")
            .append(map.get("user"))
    process.closeStreams()
    process = Runtime.runtime.exec(command.toString())
    br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    brError = new BufferedReader(new InputStreamReader(process.getErrorStream()))
    println(command.toString());
    if (br.lines().findFirst().isPresent() && brError.lines().count() == 0
            && !br.lines().findFirst().get().toLowerCase(Locale.ROOT).contains("error")) {
        println("======СОЗДАН ПОЛЬЗОВАТЕЛЬ " + map.get("user") + "======")
        Runtime.runtime.exec(firstPartOnResponse + "psql  -c \"ALTER USER "
                + map.get("user") + " WITH PASSWORD "
                + "\'" +  map.get("password") +"\'\"  -U postgres -d postgres")
    } else {
        boolean start = true;
        brError.lines().forEach(str -> println(str))
        while (start) {
            println("======ПОЛЬЗОВАТЕЛЬ " + map.get("user") + " УЖЕ СУЩЕСТВУЕТ.======")
            Scanner scanner = new Scanner(System.in);
            println("Изменить пароль? Y/N")
            String s = scanner.nextLine();
            if (s.toLowerCase(Locale.ROOT) == "y") {
                command = new StringBuilder("docker container exec db psql ")
                        .append("-U postgres -w -d project_db  -c \"").append("ALTER USER ")
                        .append(map.get("user")).append("WITH PASSWORD \'")
                        .append(map.get("password")).append("\';\"")
                Runtime.runtime.exec(command.toString());
            } else if (s.toLowerCase(Locale.ROOT) == "n") {
                "======ОК======"
                start = false;
            }
        }
    }
} else {
    new BufferedReader(new InputStreamReader(process.getErrorStream())).lines().forEach(str -> println(str))
    println("======Ошибка, контейнера не существует======")
}