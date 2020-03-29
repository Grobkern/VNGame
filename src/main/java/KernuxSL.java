import java.io.*;
import java.util.HashMap;
import java.util.Map;

//TODO: Придумать название
public class KernuxSL {
    /**
     * Главный файл скриптового языка(KernLang?)(Да да да, это интерпретатор на джаве(его кривая реализация))
     */
    //TODO: Сделать внутреигровую консоль, с вызовами функций из вложенных файлов
    private File readeableFile = null;
    private Map<String, Integer> intVariableList = new HashMap<>();
    private Map<String, Integer> floatVariableList = new HashMap<>();
    private Map<String, String> stringVariableList = new HashMap<>();
    int errorCode = 100;

    public KernuxSL(File readeableFile) {
        this.readeableFile = readeableFile;
    }

    private static boolean isKernFile(File kernFile) {
        return getExtension(kernFile).equals("kernux");
    }

    public static String getExtension(File fileName) {
        String fileExt;
        fileExt = fileName.getName().split(".")[1];
        return fileExt;
    }

    public static String getName(File fileName) {
        String name;
        name = fileName.getName().split(".")[0];
        return name;
    }

    private void writeStringType(String stringVal) {
        //TODO: Придумать как бы покрасивее реализовать
    }

    private void writeNumbType(String type, int value) {
        switch(type) {
            case "int":
                intVariableList.put(type, value);
                break;
            case "float":
                floatVariableList.put(type, value);
                break;
        }
    }

    private void parseStringVar(String[] splittedString, String type) { // Метод для парсинга строки содержащей переменную со строковым типом данных(тавтология?)
        int splitCounter = 0;
        int value;
        boolean isVar = false;
        for (int i = 0; i < splittedString.length; i++) {
            if (splittedString[i].equals(type)) {
                splitCounter = i;
            }
            try {
                value = Integer.parseInt(splittedString[splitCounter]);
                writeNumbType(type, value);
                isVar = true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                writeNumbType(type, Integer.parseInt(splittedString[splitCounter++]));
                isVar = true;
            }
        }
        if (!isVar) {
            errorCode = 0;
        }
    }

    private void parseFunc(String[] SplittedString) { // Идея неплохая, но функция предпологоает многострочный характер(необязательно, но у меня же интерпретатор, так что многострочный характер(нашёл ещё одну недоработку
        // TODO: Приудмать как нормально организовать логику работы интерпретатора

    }


    private void parseStringFirstIt(String kernString) { // Метод для парсинга строки, полученной из файла(поиск переменных)
        String fileName = readeableFile.getName();
        File cache = new File(fileName+".json"); //TODO: Релизовать кеширование(Мне кажется немного рановато для этого)
        String[] splitedCode = kernString.split(" ");
        for (String s : splitedCode) {
            switch (s) {
                case "int":
                    parseStringVar(splitedCode, "int");
                    break;
                case "float":
                    parseStringVar(splitedCode, "float");
                    break;
                case "{":
                    parseFunc(splitedCode);
            }
        }
    }

    private void parseMyFile() throws Exception {
        BufferedReader fileReader;
        int iterationSector = 0;
        if(readeableFile != null) {
            if(isKernFile(readeableFile)) {
                try {
                     fileReader = new BufferedReader(new FileReader(readeableFile));
                     while(fileReader.readLine() != null) {
                         System.out.println(iterationSector);
                         iterationSector++;
                         parseStringFirstIt(fileReader.readLine());
                     }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                    throw new FileNotFoundException("Error, bad file extension(Not .kern)");
            }
        } else {
                    throw new Exception("Internal compiler exception");
        }
    }
}
