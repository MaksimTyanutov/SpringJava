package SecondService;

import FirstService.Timetable;
import ThirdService.GlobalStatistics;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.security.InvalidParameterException;

@RestController
public class RestControllerSecond {

    @GetMapping(value = "/jsonTimetable")
    public static String jsonTimetable(@RequestParam int n) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String str = restTemplate.getForObject("http://localhost:8010/generateTimetable" + "?n=" + n, String.class);
            Timetable timetable = WriterToFile.getTimetableFromString(str);
            WriterToFile.writeTimetable(System.getProperty("user.dir") + "/timetable.json", timetable);
        }catch (Exception e){
            return e.getMessage();
        }
        return System.getProperty("user.dir") + "/timetable.json";
    }

    @GetMapping(value = "/timetableByName")
    public static Timetable timetableByName(@RequestParam String name) throws InvalidParameterException {
        if (!new File(System.getProperty("user.dir") + "/" + name).isFile()){
            throw new InvalidParameterException("Файл с таким именем не существует");
        }
        return WriterToFile.getTimetableFromFile(System.getProperty("user.dir") + "/" + name);
    }

    @PostMapping(value = "/writeStatistics")
    public static void writeStatistics(@RequestBody GlobalStatistics globalStatistics){
        try {
            WriterToFile.writePort(System.getProperty("user.dir") + "/statistics.json", globalStatistics);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}