package ThirdService;

import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Port port = new Port(1, 1, 1);
        port.getTimetableFromFile(restTemplate.getForObject("http://localhost:8020/jsonTimetable?n=100", String.class));
        port.Optimize();
        try {
            restTemplate.postForObject("http://localhost:8020/writeStatistics", port.globalStat, Void.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
