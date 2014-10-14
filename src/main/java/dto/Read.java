package main.java.dto;

/**
 * Created by oking on 22/09/14.
 */
public class Read extends TransferObject {
    public int id;
    public String locationOfReadData;

    public Read(Integer id, String locationOfReadData){
        this.locationOfReadData = locationOfReadData;
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Read)) return false;

        Read read = (Read) o;

        if (id != read.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Read{" +
                "id=" + id +
                '}';
    }
}
