

class Phone{
    private String number;
    private int code;


    public Phone(int code, String number){
        this.number = number;
        this.code = code;
    }

    public Phone(String number){
        this.number = number;
        this.code = 1;
    }

    @Override
    public String toString(){
        return "+" + this.code + this.number;
    }
}
