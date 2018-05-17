package diploma.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMarkGenerator {

    private static void writeToCSV(List<Mark> users, String separator) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream("C:/Users/vkre/Desktop/marks.csv"), "UTF-8"));
            for (Mark mark : users) {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(mark.getMarkId());
                oneLine.append(separator);
                oneLine.append(mark.getUserId());
                oneLine.append(separator);
                oneLine.append(mark.getMovieId());
                oneLine.append(separator);
                oneLine.append(mark.getMark());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Mark generateRandomMark() {
        Random rnd = new Random();
        Mark mark = new Mark();
        int randomMark = rnd.nextInt(5) + 1;
        int randomUserId = rnd.nextInt(100) + 1;
        int randomMovieId = rnd.nextInt(3500) + 1;
        mark.setMark(randomMark);
        mark.setUserId(randomUserId);
        mark.setMovieId(randomMovieId);
        return mark;
    }

    public static void main(String[] args) {
        List<Mark> marks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            marks.add(generateRandomMark());
        }
        writeToCSV(marks, ",");
    }

}

class Mark {

    private int markId;
    private int userId;
    private int movieId;
    private int mark;

    private static int counter = 1;

    public Mark() {
        this.markId = counter;
        counter++;
    }

    public int getMarkId() {
        return markId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Mark.counter = counter;
    }
}