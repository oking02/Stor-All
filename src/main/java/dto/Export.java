package main.java.dto;

/**
 * Created by oking on 15/10/14.
 */
public class Export extends TransferObject {
    public String locationToExportTo;
    public String objectType;
    public Integer idOfObjectToExport;

    public Export(String locationToExportTo, String objectType, Integer idOfObjectToExport){
        this.locationToExportTo = locationToExportTo;
        this.objectType = objectType;
        this.idOfObjectToExport = idOfObjectToExport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Export)) return false;

        Export export = (Export) o;

        if (locationToExportTo != null ? !locationToExportTo.equals(export.locationToExportTo) : export.locationToExportTo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return locationToExportTo != null ? locationToExportTo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Export{" +
                "locationToExportTo='" + locationToExportTo + '\'' +
                '}';
    }
}
