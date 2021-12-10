package stangenzirkel.gymcontrol.ui.exercises;

public class Exercise {
    public int id;
    public String name;
    public int goal;
    public String icon;

    public Exercise(int id, String name, int goal, String icon) {
        this.id = id;
        this.name = name;
        this.goal = goal;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", target=" + goal +
                ", icon=" + icon +
                '}';
    }
}
