package main.java.fileutils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Created by oking on 14/10/14.
 */
public class FileThread extends Thread {

    File file;
    File dest;
    Semaphore semaphore;


    /**
     *
     * @param file File Object of file to be copied
     * @param dest File Object for the file's destination.
     * @param semaphore Semaphore restricting threads. Released when transfer is finished.
     */
    public FileThread(File file, File dest, Semaphore semaphore){

        this.file = file;
        this.dest = dest;
        this.semaphore = semaphore;

    }

    /**
     * Run method overrided from Thread.
     * Uses FileUtil to copy file.
     * then release's held semaphore.
     */
    @Override
    public void run() {

        try {
            FileUtils.copyDirectoryToDirectory(file, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        semaphore.release();
    }
}
