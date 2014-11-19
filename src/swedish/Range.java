package swedish;

public class Range {

    private int low;
    private int high;

    public Range(int low, int high){
        this.low = low;
        this.high = high;
    }

    public boolean contains(int number){
    	if(high == -1){
    		return number >=low;
    	}
        return (number >= low && number <= high);
    }
}