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
    private Map<String, Integer> intVariableList = new HashMap<String,Integer>();
    private Map<String, Integer> floatVariableList = new HashMap<String,Integer>();
    private Map<String, String> stringVariableList = new HashMap<String,String>();
    int errorCode = 100;
    public KernuxSL(File readeableFile) {
        this.readeableFile = readeableFile;
    }

    private static boolean isKernFile(File kernFile) {
        return getExtension(kernFile) == "kernux";
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
        int value = 0;
        boolean canConvertInt = false;
        boolean isVar = false;
        for (int i = 0; i < splittedString.length; i++) {
            if (splittedString[i] == type) {
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

    private void parseFunc(String[] SplittedString) { // Идея неплохая, но функция предпологоает многострочный характер(необязательно, но у меня же интерпретатор, так что многострочный характер(нашёл ещё одну недоработку//TODO: Приудмать как нормально организовать логику работы интерпретатора
        boolean isRight = false;

    }


    private void parseStringFirstIt(String kernString) { // Метод для парсинга строки, полученной из файла(поиск переменных)
        boolean isRight = false;
        String fileName = readeableFile.getName();
        File cache = new File(getName(readeableFile)+".json");
        String[] splitedCode = kernString.split(" ");
        int resultCode = 0;
        for(int i = 0; i < splitedCode.length; i++) {
            switch (splitedCode[i]) {
                case "int":
                    isRight = true;
                    parseStringVar(splitedCode,"int");
                    break;
                case "float":
                    isRight = true;
                    parseStringVar(splitedCode,"float");
                    break;
                case "{":
                    isRight = true;
                    parseFunc(splitedCode);
            }
        }
        if(!isRight) {
            resultCode = 0;
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
