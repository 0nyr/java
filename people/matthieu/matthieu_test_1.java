import java.lang.Math.*;

public class matthieu_test_1 {

	public static void main(String[] args){
	
	// variables
	double p=15.0;
	int n=0;
	double u=1.0;

	// loop
	while (Math.abs(u)>=Math.pow(10, -p)){
		u = u-Math.log(u+1.0);
		n++;
	}
	System.out.println("value of n = "+n);

	}
}
