import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileIODemo {

    public static void main(String[] args) {
        FileIODemo demo = new FileIODemo();
        String fileName = "c:/DevApps/ideaIC2020/LICENSE.txt";
        demo.readFile(fileName);
        String kw = "copyright";
        System.out.println(demo.countWordInTxtFile(fileName, kw) + " \"" + kw + "\" found.");

        demo.printLinesWithKeyword(fileName, "copyright");
    }

    /**
     * BufferedReader chaining FileReader to read a text file line by line
     * @param fileName
     */
    public void readFile(String fileName) {
        System.out.println("Reading File " + fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("readFile() completed!");
    }

    /**
     * BufferedReader method using Stream API to count a keyword in a text file
     * @param fileName
     * @param keyword
     * @return
     */
    public long countWordInTxtFile(String fileName, String keyword) {
        long wordCount = 0L;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            wordCount = br.lines().flatMap(ln -> Stream.of(ln.split(" ")))
                    .filter(word -> word.contains(keyword))
                    .peek(word -> System.out.println(word))
                    .count();
        }
        catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return wordCount;
    }

    /**
     * New IO classes with Stream API to count a keyword in a Text file
     * @param fileName
     * @param keyword
     */
    public void printLinesWithKeyword(String fileName, String keyword) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.stream().filter(ln -> ln.toLowerCase().contains(keyword.toLowerCase()))
                    .forEach( ln -> System.out.println(ln));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
