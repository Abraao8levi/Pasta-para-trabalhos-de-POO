import java.util.Scanner;

class Pet {
    private int energyMax, hungryMax, cleanMax;
    private int energy, hungry, clean;
    private int diamonds;
    private int age;
    private boolean alive;

    public Pet(int energyMax, int hungryMax, int cleanMax) {
        this.energyMax = energyMax;
        this.hungryMax = hungryMax;
        this.cleanMax = cleanMax;
        this.energy = energyMax;
        this.hungry = hungryMax;
        this.clean = cleanMax;
        this.diamonds = 0;
        this.age = 0;
        this.alive = true;
    }

    void setEnergy(int value) {
        if (value <= 0) {
            this.energy = 0;
            System.out.println("fail: pet morreu de fraqueza");
            this.alive = false;
            return;
        }
        if (value > this.energyMax) {
            this.energy = this.energyMax;
            return;
        }
        this.energy = value;
    }

    public void setHungry(int value) {
        if (value <= 0) {
            this.hungry = 0;
            System.out.println("fail: pet morreu de fome");
            this.alive = false;
            return;
        }
        if (value > this.hungryMax) {
            this.hungry = this.hungryMax;
            return;
        }
        this.hungry = value;
    }

    void setClean(int value) {
        if (value < 0) {
            this.clean = 0;
            System.out.println("fail: pet morreu de sujeira");
            this.alive = false;
            return;
        }
        if (value > this.cleanMax) {
            this.clean = this.cleanMax;
            return;
        }
        this.clean = value;
    }

    private boolean testAlive() {
        if (!alive) {
            System.out.println("fail: pet esta morto");
            return false;
        }
        return true;
    }

    public String toString() {
        String ss = "E:" + energy + "/" + energyMax + ", "
                + "S:" + hungry + "/" + hungryMax + ", "
                + "L:" + clean + "/" + cleanMax + ", "
                + "D:" + diamonds + ", " + "I:" + age;
        return ss;
    }

    public void play() {
        if (!testAlive()) return;
        setEnergy(getEnergy() - 2);
        setHungry(getHungry() - 1);
        setClean(getClean() - 3);
        diamonds += 1;
        age += 1;
    }

    public void shower() {
        if (!testAlive()) return;
        setEnergy(getEnergy() - 3);
        setHungry(getHungry() - 1);
        setClean(getCleanMax());
        age += 2;
    }

    public void eat() {
        if (!testAlive()) return;
        setEnergy(getEnergy() - 1);
        setHungry(getHungry() + 4);
        setClean(getClean() - 2);
        age += 1;
    }

    public void sleep() {
        if (!testAlive()) return;
        setEnergy(getEnergyMax());
        setHungry(getHungry() - 1);
        age += 5;
    }

    int getClean() {
        return clean;
    }

    int getHungry() {
        return hungry;
    }

    int getEnergy() {
        return energy;
    }

    int getEnergyMax() {
        return energyMax;
    }

    int getCleanMax() {
        return cleanMax;
    }

    int getHungryMax() {
        return hungryMax;
    }

    public void cleanPet() {
        if (!testAlive()) return;
        if (getClean() <= 0) {
            System.out.println("fail: pet morreu de sujeira");
            alive = false;
            return;
        }
        setEnergy(getEnergy() - 1);
        setHungry(getHungry() - 1);
        setClean(getCleanMax());
        age += 1;
    }
}
