import java.io.*;
import java.util.HashMap;
import java.util.Map;

//TODO: Придумать название
public class KernuxSL {
    /**
     * Главный файл скриптового языка(KernLang?)
     */
    private File readeableFile = null;
    private Map<String, Integer> intVariableList = new HashMap<String,Integer>();
    private Map<String, Integer> floatVariableList = new HashMap<String,Integer>();
    private Map<String, String> stringVariableList = new HashMap<String,String>();

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

    private void writeStringType(String stringVal){
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

    private void parseStringVar(String[] splittedString, String type) {
        int splitCounter = 0;
        int value = 0;
        boolean canConvertInt = false;
        boolean isString = false;
        for (int i = 0; i < splittedString.length; i++) {
            if (splittedString[i] == type) {
                splitCounter = i;
                return;
            }
                try {
                    value = Integer.parseInt(splittedString[splitCounter]);
                    writeNumbType(type, value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    writeNumbType(type, Integer.parseInt(splittedString[splitCounter++]));
                }
        }
    }


    private void parseStringFirstIt(String kernString) {
        String fileName = readeableFile.getName();
        File cache = new File(getName(readeableFile)+".json");
        String[] splitedCode = kernString.split(" ");
        for(int i = 0; i < splitedCode.length; i++) {
            switch (splitedCode[i]) {
                case "int":
                    parseStringVar(splitedCode,"int");
                    break;
                case "float":
                    parseStringVar(splitedCode,"float");
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
