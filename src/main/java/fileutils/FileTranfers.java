package main.java.fileutils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by oking on 14/10/14.
 */
public class FileTranfers {
    private FileThreadController fileThreadController;
    private String sourceLocation;
    private String destinationLocation;

    public FileTranfers(String sourceLocation, String destinationLocation) throws FileNotFoundException {
        fileThreadController = new FileThreadController();
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
    }

    public void transfer() throws InterruptedException, IOException {
        File source = new File(sourceLocation);
        String[] sourceSubDirectories = source.list(DirectoryFileFilter.INSTANCE);
        String[] sourceRootFiles = source.list(FileFileFilter.FILE);

        fileThreadController.copier(sourceLocation, destinationLocation, sourceSubDirectories);

        for ( String file : sourceRootFiles){
            FileUtils.copyFileToDirectory(new File(sourceLocation + "/" + file), new File(destinationLocation));
        }

    }



}
