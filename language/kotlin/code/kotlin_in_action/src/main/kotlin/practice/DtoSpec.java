package practice;

public class DtoSpec {
    private String finalVal;
    private int finalInt;

    private String varStringGetterSetter;
    private int varIntGetterSetter;

    private String varStringGetter;
    private int vatIntGetter;

    private DtoSpec() {
    }

    public DtoSpec(String finalVal, int finalInt) {
        this.finalVal = finalVal;
        this.finalInt = finalInt;
    }

    public String getFinalVal() {
        return finalVal;
    }

    public int getFinalInt() {
        return finalInt;
    }

    public String getVarStringGetterSetter() {
        return varStringGetterSetter;
    }

    public int getVarIntGetterSetter() {
        return varIntGetterSetter;
    }

    public String getVarStringGetter() {
        return varStringGetter;
    }

    public int getVatIntGetter() {
        return vatIntGetter;
    }

    public void setVarStringGetterSetter(String varStringGetterSetter) {
        this.varStringGetterSetter = varStringGetterSetter;
    }

    public void setVarIntGetterSetter(int varIntGetterSetter) {
        this.varIntGetterSetter = varIntGetterSetter;
    }
}