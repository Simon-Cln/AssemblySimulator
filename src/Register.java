public class Register {

    private int value;

    public Register(){ this.value = 0;}

    public void setRegister(int givenValue) {
        this.value = givenValue;
    }

    public int getDecimalValue(){
        return(this.value);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("");
        str.append(this.value);
        return str.toString();
    }

}