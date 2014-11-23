package main.java.dtoadapters.dtodeleter;

import main.java.dto.TransferObject;

import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public abstract class DtoDeleter {

    public abstract void delete(TransferObject transferObject) throws SQLException;
}
