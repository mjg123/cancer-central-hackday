package lol.gilliard.cancercentralhackday.DataObjects;

public class Category {

    public final int id;
    public final String name;
    public final String logoImageUrl;

    public Category(int id, String name, String logoImageUrl) {
        this.id = id;
        this.name = name;
        this.logoImageUrl = logoImageUrl;
    }
}
