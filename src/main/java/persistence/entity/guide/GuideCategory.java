package persistence.entity.guide;

public enum GuideCategory {
    TUTORIAL("Tutorial"),
    RECOMMANDATION("Recommandation");

    private final String name;

    GuideCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}



