package main.java.fileutils;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;

/**
 * Created by oking on 14/10/14.
 */
public class DataValidation {

    public static void validateInputDataFolder(String folderLocation) throws Exception {

        File dataFolder = new File(folderLocation);
        validAndIsDirectory(dataFolder);
        isDirectoryEmpty(dataFolder);

    }

    private static void validAndIsDirectory(File dataFolder) throws  Exception {

        boolean exists = dataFolder.exists();
        boolean isDirectory = dataFolder.isDirectory();

        if ( !exists || !isDirectory){
            throw new Exception("Directory Doesn't Exist!");
        }

    }

    private static void isDirectoryEmpty(File dataFolder) throws Exception {

        String[] dirs = dataFolder.list(DirectoryFileFilter.INSTANCE);
        String[] files = dataFolder.list(FileFileFilter.FILE);

        if (dirs.length == 0 && files.length == 0){
            throw new Exception("No Files Found!");
        }
    }



}
