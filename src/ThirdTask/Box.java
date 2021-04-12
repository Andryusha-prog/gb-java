package ThirdTask;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public float getWeightBox() {
        if (fruits.isEmpty()) {
            return 0f;
        } else {
            return fruits.get(0).getWeight() * fruits.size();
        }
    }

    public boolean compare(Box<? extends Fruit> that) {
        return getWeightBox() == that.getWeightBox();
    }

    public void moveFruits(Box<T> that) {
        fruits.addAll(that.getFruits());
        that.getFruits().clear();
    }

    public void addFruits(T fruit) {
        fruits.add(fruit);
    }
}
