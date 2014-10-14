package main.java.dto;

/**
 * Created by oking on 22/09/14.
 */
public class Analysis extends TransferObject {
    public int id;
    public int expID;
    public String info;
    public String dataLocation;

    public Analysis(Integer id, Integer expID, String info, String dataLocation){
        this.id = id;
        this.expID = expID;
        this.info = info;
        this.dataLocation = dataLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Analysis)) return false;

        Analysis analysis = (Analysis) o;

        if (expID != analysis.expID) return false;
        if (id != analysis.id) return false;
        if (info != null ? !info.equals(analysis.info) : analysis.info != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + expID;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "id=" + id +
                ", expID=" + expID +
                ", info='" + info + '\'' +
                '}';
    }
}
