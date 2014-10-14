package main.java.fileutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

/**
 * Created by oking on 14/10/14.
 */
public class FileThreadController {

    private Semaphore semaphore;


    public FileThreadController() throws FileNotFoundException {

        semaphore = new Semaphore(4, false);
    }

    /**
     *
     * @param src Path of the source directory.
     * @param dest Path of the desired destination directory.
     * @param files Array of the individual file paths
     * @throws InterruptedException Throw by the Java Thread implementation.
     */
    public void copier(String src, String dest, String[] files) throws InterruptedException {

        File destination = new File(dest);
        long startTime = System.currentTimeMillis();

        for (String s : files) {
            File file = new File(src + "/" + s);
            movement(file, destination);
        }

        while (semaphore.availablePermits() != 4){

            //Wait for transfer to finish!

        }

        long stopTime = System.currentTimeMillis();
        long runTime = stopTime - startTime;
        System.out.println("Run time: " + runTime);

    }

    private void movement(File file, File des) throws InterruptedException {
        semaphore.acquire();
        new FileThread(file, des, semaphore).start();
    }
}
