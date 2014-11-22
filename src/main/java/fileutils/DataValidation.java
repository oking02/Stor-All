package main.java.fileutils;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

/**
 * Created by oking on 14/10/14.
 */
public class DataValidation {

    public static void validateInputDataFolder(String folderLocation) throws FileNotFoundException, FileSystemException {

        File dataFolder = new File(folderLocation);
        validAndIsDirectory(dataFolder);
        isDirectoryEmpty(dataFolder);

    }

    private static void validAndIsDirectory(File dataFolder) throws FileNotFoundException {

        boolean exists = dataFolder.exists();
        boolean isDirectory = dataFolder.isDirectory();

        if ( !exists || !isDirectory){
            throw new FileNotFoundException("Directory Doesn't Exist!");
        }

    }

    private static void isDirectoryEmpty(File dataFolder) throws FileSystemException {

        String[] dirs = dataFolder.list(DirectoryFileFilter.INSTANCE);
        String[] files = dataFolder.list(FileFileFilter.FILE);

        if (dirs.length == 0 && files.length == 0){
            throw new FileSystemException("No Files Found!");
        }
    }



}
