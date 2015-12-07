package pagebuilder.builder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Matthieu on 01/12/2015.
 */
public class PageBuilder {
    private Path root;
    private Path startFile;
    private Path outputFile;

    public PageBuilder(Path startFile, Path outputFile) {
        this.root = startFile.getParent();
        this.startFile = startFile;
        this.outputFile = outputFile;
    }

    public void build() throws IOException {
        if(!outputFile.toFile().exists()) {
            Files.createFile(this.outputFile);
        }
        FileWriter fw = new FileWriter(this.outputFile.toFile());
        BufferedWriter bw = new BufferedWriter(fw);
        this.writeFileContent( this.startFile.toString(), bw);
        bw.flush();
        bw.close();
    }

    public void writeFileContent(String filename, Writer writer) throws IOException {
        System.out.println("Writing the following file : " + filename);
        Path filePath = this.root.resolve(filename);
        if(Files.exists(filePath)) {
            FileReader fr = new FileReader(filePath.toFile());
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while((line = br.readLine()) != null) {
                this.processLine(writer,line);
            }
        } else {
            System.out.println(filename + " is not a correct filename");
            throw new IOException();
        }
    }

    public String getFileToInclude(String line) {
        String tempStr = line.trim();
        if(tempStr.length() > 2) {
            if((tempStr.charAt(0) == '[') && (tempStr.charAt(1) == '[') && (tempStr.charAt(tempStr.length() - 1) == ']') && (tempStr.charAt(tempStr.length()-2) == ']')){
                String out ="";
                for(int i=2;i<(tempStr.length() - 2);i++) {
                    out += tempStr.charAt(i);
                }
                return out;
            }
        }
        return null;
    }

    public void processLine(Writer writer, String line) throws IOException {
        String pathToFile = this.getFileToInclude(line);
        if(pathToFile != null) {
            this.writeFileContent(pathToFile,writer);
        } else {
            writer.write(line);
        }
    }
}

