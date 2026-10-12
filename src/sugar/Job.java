package sugar;

import entrepreneurship.Material;

public enum Job {
    CANDY_SELLER("CANDY_SELLER", 100),
    CANDY_RECIPE_CREATOR("CANDY_RECIPE_CREATOR", 68),
    SWEET_EVENT_ORGANIZER("SWEET_EVENT_ORGANIZER", 74),
    SWEET_GUARDIAN("SWEET_GUARDIAN", 0),
    SWEET_AMBASSADOR("SWEET_AMBASSADOR", 43),
    SWEET_INGREDIENT_HUNTER("SWEET_INGREDIENT_HUNTER", 60),
    CANDY_MAGIC_TEACHER("CANDY_MAGIC_TEACHER", 84),
    UNEMPLOYED("UNEMPLOYED", 33);

    public final String label;
    public final int infectionChance;

    private Job(String label, int infectionChance) {
        this.label = label;
        this.infectionChance = infectionChance;
    }

    public static Job parseJob(String label) {
        for (Job job: values()) {
            if (job.label.equals(label)) {
                return job;
            }
        }
        return null;
    }
}
