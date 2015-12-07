package pagebuilder.app;

import pagebuilder.builder.PageBuilder;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Matthieu on 01/12/2015.
 */
public class Application {
    public static final String PROPERTY_PATH_PAGE = "configPageBuilder.properies";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("If first launch, you must select the input and output directories (select 2) !");
            System.out.print("Choose mode (generate page : 1 ; change input/output files : 2) : ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    normalMode();
                    return;
                case 2:
                    setProperties();
                    break;
            }
        }
    }



    public static void normalMode() {
        // PageBuilder pb = new PageBuilder(Paths.get("c:/isen/java2/practical2/pagebuilder/index.html"),Paths.get("c:/isen/java2/practical2/pagebuilder/output.html"));
        PageBuilder pb = new PageBuilder(Paths.get(getInputFileString()),Paths.get(getOutputFileString()));
        try {
            pb.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInputFileString() {
        Properties prop = new Properties();
        String inputStr = null;
        try (InputStream input = new FileInputStream(PROPERTY_PATH_PAGE)){
            prop.load(input);
            inputStr = prop.getProperty("input");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStr;
    }
    public static String getOutputFileString(){
        Properties prop = new Properties();
        String outputStr = null;
        try (InputStream input = new FileInputStream(PROPERTY_PATH_PAGE)){
            prop.load(input);
            outputStr = prop.getProperty("output");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStr;
    }
    public static void setProperties() {
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter input file path (absolute path with extension name file) : ");
        String input = sc.nextLine(); //To add: gestion d'un mauvais type de chemins !
        System.out.print("Enter output file path (absolute path with extension name file) : ");
        String outputStr = sc.nextLine();
        try (OutputStream output = new FileOutputStream(PROPERTY_PATH_PAGE)) {
            prop.setProperty("input", input);
            prop.setProperty("output", outputStr);
            prop.store(output,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

