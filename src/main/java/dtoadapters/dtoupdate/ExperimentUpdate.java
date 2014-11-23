package main.java.dtoadapters.dtoupdate;

import main.java.dto.TransferObject;
import main.java.mysql.builder.AnalysisBuilder;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;

/**
 * Created by oking on 22/11/14.
 */
public class ExperimentUpdate extends DtoUpdater {


    public void update(TransferObject transferObject) throws SQLException, InterruptedException, FileSystemException, FileNotFoundException {
        AnalysisBuilder analysisBuilder = new AnalysisBuilder((main.java.dto.Analysis) transferObject);
        analysisBuilder.build();
    }
}
