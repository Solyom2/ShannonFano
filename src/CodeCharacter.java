public class CodeCharacter implements Comparable< CodeCharacter > {

    private char character;
    private double occurances;
    private double frequency;
    private double xvalue;
    private String codeword;

    public CodeCharacter(char character) {
        this.character = character;
        this.occurances = 1;
        this.frequency = 0;
        this.codeword = "";
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public double getOccurances() {
        return occurances;
    }

    public void setOccurances(double occurances) {
        this.occurances = occurances;
    }

    public void increaseOccurances() {
        this.occurances = this.occurances + 1;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void calculateFrequency(int codeLength) {
        this.frequency = this.occurances/codeLength;
    }

    public double getXvalue() {
        return xvalue;
    }

    public void setXvalue(double xvalue) {
        this.xvalue = xvalue;
    }

    public String getCodeword() {
        return codeword;
    }

    public void setCodeword(String codeword) {
        this.codeword = codeword;
    }

    @Override
    public int compareTo(CodeCharacter cc) {
        Double d1 = new Double(this.getFrequency());
        Double d2 = new Double(cc.getFrequency());
        return d1.compareTo(d2);
    }

    @Override
    public String toString() {
        return "CodeCharacter{" +
                "character=" + character +
                ", occurances=" + occurances +
                ", frequency=" + frequency +
                ", xvalue=" + xvalue +
                ", codeword='" + codeword + '\'' +
                '}';
    }
}
