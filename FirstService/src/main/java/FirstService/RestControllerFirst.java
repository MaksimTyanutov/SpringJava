package FirstService;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerFirst {
    @GetMapping(value = "/generateTimetable")
    public static String timetable(@RequestParam int n) {
        Gson gson = new Gson();
        return gson.toJson(new Timetable(n).shipsArray);
    }
}