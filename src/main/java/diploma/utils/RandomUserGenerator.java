package diploma.utils;

import java.io.*;
import java.util.*;

public class RandomUserGenerator {

    private static Map<Integer, String> userNamesMap = new HashMap<>();
    private static Map<Integer, String> userTariffsMap = new HashMap<>();

    static {

        userNamesMap.put(0, "Anya");
        userNamesMap.put(1, "Olya");
        userNamesMap.put(2, "Zhenya");
        userNamesMap.put(3, "Tolik");
        userNamesMap.put(4, "Katya");
        userNamesMap.put(5, "Sasha");
        userNamesMap.put(6, "Andrey");
        userNamesMap.put(7, "Kuzya");
        userNamesMap.put(8, "Sveta");
        userNamesMap.put(9, "Liza");
        userNamesMap.put(10, "Vova");
        userNamesMap.put(11, "Lena");
        userNamesMap.put(12, "Yulia");
        userNamesMap.put(13, "Nastya");
        userNamesMap.put(14, "Roma");
        userNamesMap.put(15, "Artem");
        userNamesMap.put(16, "Denis");
        userNamesMap.put(17, "Mark");
        userNamesMap.put(18, "Natasha");

        userTariffsMap.put(0, "SVOBODNYJ");
        userTariffsMap.put(1, "YASKRAVY");
        userTariffsMap.put(2, "STAR");
        userTariffsMap.put(3, "UNLIMITED");
        userTariffsMap.put(4, "BONUS+");
        userTariffsMap.put(5, "BONUS");
        userTariffsMap.put(6, "STUDENT");
        userTariffsMap.put(7, "BUSINESS");

    }

    private static User makeRandomUser() {

        Random rnd = new Random();

        int randNameIndex = rnd.nextInt(18);
        int randAge = rnd.nextInt(90) + 10;
        int randTariffIdex = rnd.nextInt(7);

        User user = new User();
        user.setName(userNamesMap.get(randNameIndex));
        user.setAge(randAge);
        user.setTariff(userTariffsMap.get(randTariffIdex));

        return user;

    }

    private static void writeToCSV(List<User> users, String separator)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream("C:/Users/vkre/Desktop/users.csv"), "UTF-8"));
            for (User user : users)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(user.getId());
                oneLine.append(separator);
                oneLine.append(user.getName());
                oneLine.append(separator);
                oneLine.append(user.getAge());
                oneLine.append(separator);
                oneLine.append(user.getTariff());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>(1000);
        for(int i = 0; i < 1000; i++){
            users.add(makeRandomUser());
        }
        writeToCSV(users, ",");

    }


}

class User {

    private int id;
    private String name;
    private int age;
    private String tariff;

    private static int counter = 1;

    public User() {
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }


}