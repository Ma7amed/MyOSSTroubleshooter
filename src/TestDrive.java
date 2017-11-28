import java.util.ArrayList;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class TestDrive {

    public static void main(String[] args) {

        ArrayList<String> test = new ArrayList<>();
        for(int i=1;i<=3500;i++) {
            test.add(Integer.toString(i));
        }
        sample.Utlities.arrayToSQL_Advance(test);



    }

    // 0 > 999, 1000 > 1999, 2000 > 2999, 3000 > max
    // 0 > max
    // if <= 1000 ... go normal way
    // if > 1000 ... loop every 1000
    //
}
