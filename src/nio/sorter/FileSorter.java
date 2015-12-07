package nio.sorter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Matthieu on 01/12/2015.
 */
public class FileSorter {
    private Path root;
    private Path archive;
    private Path byExtension;
    private Path animals;


    public FileSorter(String root) {
        this.root = Paths.get(root);
        try {
            this.animals = this.prepareDirectory("animal", this.root);
            this.archive = this.prepareDirectory("archive", this.root);
            this.byExtension = this.prepareDirectory("byExt", this.root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int sortFiles() throws IOException {
        DirectoryStream<Path> files = Files.newDirectoryStream(this.animals);
        int filesTreated = 0;
        for (Path curFile: files) {
            this.prepareDirectory(this.getExtension(curFile), this.byExtension); //Créer directory si inexistant
            Path temp = this.byExtension.resolve(this.getExtension(curFile)); //Créer le path de destination
            this.copyFile(curFile,temp); //Copie des fichier dans le dossier courant (byExt/<extension>)
            this.moveFileToArchive(curFile);
            filesTreated++;
        }
        return filesTreated;
    }

    public Path prepareDirectory(String newDir, Path base) throws IOException {
        Path nBase = base.resolve(newDir);
        if(Files.exists(nBase)) {
            return nBase;
        } else {
            Files.createDirectory(nBase);
            return nBase;
        }
    }

    public String getExtension(Path entry) {
        String str = entry.toString();
        int index = str.lastIndexOf((int)'.') + 1;
        String out = "";
        for(int i=index ; i<str.length(); i++) {
            out += str.charAt(i);
        }
        return out;
    }

    public void copyFile(Path entry, Path directory) throws IOException{
        if(!Files.exists(directory.resolve(entry.getFileName()))) {
            Files.copy(entry,directory.resolve(entry.getFileName()));
        }

    }

    public void moveFileToArchive(Path entry) throws IOException {
        Path temp = this.archive.resolve(entry.getFileName());
        if(!Files.exists(temp)) {
            Files.move(entry, temp);
        }
    }

    public void setArchive(Path archive) {
        this.archive = archive;
    }

}