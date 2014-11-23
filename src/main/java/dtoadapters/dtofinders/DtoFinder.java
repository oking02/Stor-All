package main.java.dtoadapters.dtofinders;

import main.java.dto.TransferObject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oking on 22/11/14.
 */
public abstract class DtoFinder {

    public abstract List<TransferObject> findAll() throws SQLException;

    public abstract List<TransferObject> findOne(int id) throws SQLException;
}
