package model;

public class Player implements Comparable<Player> {
    private String name;
    private final Score score;

    public Player() {
        this.score = new Score();
    }

    public Player(String name) {
        this();
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score.getScore();
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    @Override
    public String toString() {
        return String.format("%s: %d", this.getName(), this.getScore());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Player player)) return false;

        return this.name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Player o) {
        if (o.getScore() == this.getScore()) {
            return this.getName().compareTo(o.getName());
        }
        return o.getScore() - this.getScore();
    }
}
