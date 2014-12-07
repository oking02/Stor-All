package main.java.dto;

/**
 * Created by oking on 04/12/14.
 */
public class AnalysisTool extends TransferObject {

    public String name;
    public String outputLocation;
    public int useCount;

    public AnalysisTool(String name, String outputLocation){
        this.name = name;
        this.outputLocation = outputLocation;
        this.useCount = -1;
    }

    @Override
    public String toString() {
        return "AnalysisTool{" +
                "name='" + name + '\'' +
                ", outputLocation='" + outputLocation + '\'' +
                ", useCount=" + useCount +
                '}';
    }

    public AnalysisTool(String name, String outputLocation, int useCount){
        this.name = name;
        this.outputLocation = outputLocation;
        this.useCount = useCount;
    }

    public void setUseCount(int count){
        this.useCount = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalysisTool)) return false;

        AnalysisTool that = (AnalysisTool) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (outputLocation != null ? !outputLocation.equals(that.outputLocation) : that.outputLocation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (outputLocation != null ? outputLocation.hashCode() : 0);
        return result;
    }


}
