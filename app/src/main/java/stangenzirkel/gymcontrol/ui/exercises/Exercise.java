package stangenzirkel.gymcontrol.ui.exercises;

public class Exercise {
    public int id;
    public String name;
    public int target;
    public String icon;

    public Exercise(int id, String name, int target, String icon) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", target=" + target +
                ", icon=" + icon +
                '}';
    }
}
