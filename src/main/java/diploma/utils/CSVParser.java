package diploma.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVParser {

    public static void main(String[] args) {

        String csvFile = "C:/Users/vkre/Desktop/movies.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            PrintWriter pw = new PrintWriter(new File("C:/Users/vkre/Desktop/test.csv"));
            String regex = "\\|.+";
            String regex2 = "::";
            String regex3 = "\\s\\(.{4}\\)";
            String regex4 = ",\\s";
            String currentLine;
            while ((currentLine = br.readLine()) != null){
                currentLine = findAndReplaceAll(regex, "", currentLine);
                currentLine = findAndReplaceAll(regex2, ",", currentLine);
                currentLine = findAndReplaceAll(regex3, "", currentLine);
                currentLine = findAndReplaceAll(regex4, "", currentLine);
                pw.write(currentLine+"\n");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String findAndReplaceAll(
            String pattern,
            String replaceWith,
            String inputString)
    {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(inputString);
        return matcher.replaceAll(replaceWith);
    }

}
