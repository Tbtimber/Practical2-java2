package nio.app;

import nio.sorter.FileSorter;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static final String PROPERTY_PATH = "config.properties";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("If first launch, you must select a root directory (select 2) !");
            System.out.print("Choose mode (Keep current root path : 1 ; Change root path : 2) : ");
            int val = sc.nextInt();
            switch (val) {
                case 1:
                    normalMode();
                    return;
                case 2:
                    writeProperties();
                    break;
            }
        }

    }

    public static void normalMode() {
        FileSorter sorter = new FileSorter(getRootPath());
        int number = 0;
        try {
            number = sorter.sortFiles();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Number of file treated : " + number);
        }
    }

    /**
     * Properties setup
     */
    public static void writeProperties() {
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new path (absolute path) : ");
        String rootPath = sc.nextLine(); //To add: gestion d'un mauvais type de chemins !
        try (OutputStream output = new FileOutputStream(PROPERTY_PATH)) {
            prop.setProperty("root", rootPath);
            prop.store(output,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Get properties (i.e. rootPath)
     */
    public static String getRootPath() {
        Properties prop = new Properties();
        String pathToRoot = null;
        try (InputStream input = new FileInputStream(PROPERTY_PATH)){
            prop.load(input);
            pathToRoot = prop.getProperty("root");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathToRoot;
    }
}

