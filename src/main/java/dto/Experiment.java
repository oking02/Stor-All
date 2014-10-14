package main.java.dto;

import java.util.ArrayList;

/**
 * Created by oking on 22/09/14.
 */
public class Experiment extends TransferObject {
    public int id;
    public int projectID;
    public int readID;
    public ArrayList<Analysis> analysis;

    public Experiment(Integer id, Integer projectID, Integer readID){
        this.id = id;
        this.projectID = projectID;
        this.readID = readID;
        analysis = new ArrayList<Analysis>();
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Experiment)) return false;

        Experiment that = (Experiment) o;

        if (id != that.id) return false;
        if (projectID != that.projectID) return false;
        if (readID != that.readID) return false;
        if (analysis != null ? !analysis.equals(that.analysis) : that.analysis != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + projectID;
        result = 31 * result + readID;
        return result;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "id=" + id +
                ", projectID=" + projectID +
                ", readID=" + readID +
                ", analysis=" + analysis +
                '}';
    }
}
