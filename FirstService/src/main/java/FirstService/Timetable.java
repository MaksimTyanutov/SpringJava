package FirstService;

import util.Ship;
import util.cargoType;

import java.util.ArrayList;
import java.util.Calendar;

public class Timetable {
  public int num;
  public Ship[] shipsArray;

  public static final double containerSpeed = 15;//в минуту
  public static final double liquidSpeed = 25;
  public static final double looseSpeed = 15;

  public Timetable() {
    this.num = 10;
    this.shipsArray = new Ship[num];
    this.generateTime();
  }

  public Timetable(int num) {
    this.num = num;
    this.shipsArray = new Ship[num];
    this.generateTime();
  }

  public Timetable(ArrayList<Ship> ships) {
    this.num = ships.size();
    this.shipsArray = new Ship[num];
    ships.toArray(shipsArray);
  }

  public void generateTime() {
    for (int i = 0; i < num; i++) {
      shipsArray[i] = new Ship();
      shipsArray[i].getTimeInCalendar().set(Calendar.YEAR, 121);
      shipsArray[i].getTimeInCalendar().set(Calendar.MONTH, Calendar.MARCH);
      shipsArray[i].getTimeInCalendar().set(Calendar.DAY_OF_MONTH, getRandomNum(0,30));
      shipsArray[i].getTimeInCalendar().set(Calendar.HOUR, getRandomNum(0,23));
      shipsArray[i].getTimeInCalendar().set(Calendar.MINUTE,getRandomNum(0,59));
      shipsArray[i].getTimeInCalendar().set(Calendar.SECOND, getRandomNum(0,59));
      shipsArray[i].setName("Ship №" + i);
      shipsArray[i].setCargoNum(getRandomNum(5000,100000));
      switch (getRandomNum(0,2)) {
        case 0 -> {
          shipsArray[i].setType(cargoType.loose);
          shipsArray[i].setPlanned_unload_time((long) ((shipsArray[i].getCargoNum()) * 500L / looseSpeed)
                  + 1000 * 60 * 60 * 12);//добавление 12 часов ко времени разгрузки
        }
        case 1 -> {
          shipsArray[i].setType(cargoType.liquid);
          shipsArray[i].setPlanned_unload_time((long) ((shipsArray[i].getCargoNum()) * 500L / liquidSpeed)
                  + 1000 * 60 * 60 * 12);
        }
        default -> {
          shipsArray[i].setType(cargoType.container);
          shipsArray[i].setPlanned_unload_time((long) ((shipsArray[i].getCargoNum()) * 500L / containerSpeed)
                  + 1000 * 60 * 60 * 12);
        }
      }

      shipsArray[i].setPlanned_time(shipsArray[i].getTime() + (long)getRandomNum(-7,7) * 24 * 60 * 60 * 1000);

    }
    this.sort();
  }

  public void sort() {
    for (int j = num - 1; j >= 1; j--) {
      for (int i = 0; i < j; i++) {
        if (shipsArray[i].getTime() > shipsArray[i + 1].getTime()) {
          Ship tmp = shipsArray[i];
          shipsArray[i] = shipsArray[i + 1];
          shipsArray[i + 1] = tmp;
        }
      }
    }
  }

  private int getRandomNum(int min, int max) {
    return (int) (Math.random() * (max - min + 1) + min);
  }
}
