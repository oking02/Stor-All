package main.java.dto;

/**
 * Created by oking on 22/09/14.
 */
public class Project extends TransferObject {
    public int id;
    public String owner;

    public Project(Integer id, String owner){
        this.id = id;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (owner != null ? !owner.equals(project.owner) : project.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                '}';
    }
}
