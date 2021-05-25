package SecondService;

import FirstService.Timetable;
import ThirdService.GlobalStatistics;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.Gson;
import util.Ship;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WriterToFile
{
  static public void writeTimetable(String pathToJson, Timetable timetable)
  {
    try
    {
      JsonWriter writer = new JsonWriter(new FileWriter(pathToJson));
      Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss").create();
      Type SHIP_TYPE = timetable.shipsArray.getClass();
      gson.toJson(timetable.shipsArray, SHIP_TYPE, writer);
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  static public void writePort(String pathToJson, GlobalStatistics globalStat)
  {
    try
    {
      JsonWriter writer = new JsonWriter(new FileWriter(pathToJson));
      writer.beginObject();
      writer.name("Ships statistic");
      writer.beginArray();
      for (int i = 0; i < globalStat.num ;i++)
      {
        writer.beginObject()
                .name("Name").value(globalStat.statistic_list.get(i).name)
                .name("Date of arriving").value(String.valueOf(globalStat.statistic_list.get(i).date_of_arriving))
                .name("Time of waiting").value(String.valueOf(globalStat.statistic_list.get(i).time_of_waiting))
                .name("Date of unloading").value(String.valueOf(globalStat.statistic_list.get(i).date_of_unloading))
                .name("Time of unloading").value(String.valueOf(globalStat.statistic_list.get(i).time_of_service));
        writer.endObject();
      }
      writer.endArray();
      writer.name("GlobalStatistic:").beginObject();
      writer.name("Num").value(globalStat.num)
              .name("Average queue length").value(globalStat.average_queue_length)
              .name("Average time of waiting").value(String.valueOf(globalStat.average_time_of_waiting))
              .name("Max unload delay").value(String.valueOf(globalStat.max_time_of_delay))
              .name("Average delay time").value(String.valueOf(globalStat.average_time_of_delay))
              .name("Fine").value(globalStat.fine)
              .name("Container Cranes").value(globalStat.contCranes)
              .name("Liquid Cranes").value(globalStat.liquidCranes)
              .name("Loose Cranes").value(globalStat.looseCranes);
      writer.endObject().endObject();
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static Timetable getTimetableFromFile(String pathToJson) {//инициализация расписания из файла
    ArrayList<Ship> ships = new ArrayList<>();
    try {
      JsonReader reader = new JsonReader(new FileReader(pathToJson));
      Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss").create();
      Type SHIP_TYPE = new TypeToken<ArrayList<Ship>>() {
      }.getType();
      ships = gson.fromJson(reader, SHIP_TYPE);
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new Timetable(ships);
  }

  public static Timetable getTimetableFromString(String str){
    Gson gson = new Gson();
    JsonArray object = JsonParser.parseString(str).getAsJsonArray();
    Type SHIP_TYPE = new TypeToken<ArrayList<Ship>>() {}.getType();
    ArrayList<Ship> parsedJson = gson.fromJson(object, SHIP_TYPE);
    return new Timetable(parsedJson);
  }
}
