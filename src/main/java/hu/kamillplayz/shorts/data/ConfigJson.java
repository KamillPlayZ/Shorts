package hu.kamillplayz.shorts.data;

public class ConfigJson {

    private boolean diamondStrip = true;
    private boolean striderBook = true;
    private boolean cauldronWater = true;
    private boolean redstoneDye = true;
    private boolean treeGrow = true;
    private boolean diamondEat = true;
    private boolean bedrockGrow = true;
    private boolean mobEgg = true;
    private boolean rename = true;
    private boolean gymAnimals = true;

    public boolean isGymAnimals() {
        return gymAnimals;
    }

    public boolean isRename() {
        return rename;
    }

    public boolean isMobEgg() {
        return mobEgg;
    }

    public boolean isBedrockGrow() {
        return bedrockGrow;
    }

    public boolean isDiamondStrip() {
        return diamondStrip;
    }

    public boolean isStriderBook() {
        return striderBook;
    }

    public boolean isCauldronWater() {
        return cauldronWater;
    }

    public boolean isRedstoneDye() {
        return redstoneDye;
    }

    public boolean isTreeGrow() {
        return treeGrow;
    }

    public boolean isDiamondEat() {
        return diamondEat;
    }
}
