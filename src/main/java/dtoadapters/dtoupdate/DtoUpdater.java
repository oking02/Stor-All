package main.java.dtoadapters.dtoupdate;

import main.java.dto.TransferObject;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public abstract class DtoUpdater {

    public abstract void update(TransferObject transferObject) throws SQLException, InterruptedException, FileSystemException, FileNotFoundException;
}
