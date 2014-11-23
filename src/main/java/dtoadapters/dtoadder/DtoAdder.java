package main.java.dtoadapters.dtoadder;

import main.java.dto.TransferObject;

import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public abstract class DtoAdder {

    public abstract boolean addDto(TransferObject transferObject) throws SQLException;
}
