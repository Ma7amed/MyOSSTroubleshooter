import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by m80028770 on 11/6/2017.
 */
public class TestDrive {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentDate = localDateTime.format(formatter);
        System.out.println("D:/Work/OSS/Temp_Delete/" + currentDate);
    }

    // 0 > 999, 1000 > 1999, 2000 > 2999, 3000 > max
    // 0 > max
    // if <= 1000 ... go normal way
    // if > 1000 ... loop every 1000
    //
}
