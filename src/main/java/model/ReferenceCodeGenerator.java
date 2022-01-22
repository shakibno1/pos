package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

public class ReferenceCodeGenerator {
    
//  private static String uniqueReferenceCode="";
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   
//  public static String getUniqueReferenceCode() {
//       
//      Timestamp ts = new Timestamp(System.nanoTime());
//      String uniqueRef = sdf.format(ts);
//      uniqueReferenceCode = uniqueRef;
//      return uniqueReferenceCode;
//  }
   
  private static final AtomicLong LAST_TIME_MS = new AtomicLong();
  public static String getUniqueReferenceCode() {
      long now = System.nanoTime();
      while(true) {
          long lastTime = LAST_TIME_MS.get();
          if (lastTime >= now)
              now = lastTime+1;
          if (LAST_TIME_MS.compareAndSet(lastTime, now)) {
              Timestamp tt = new Timestamp(now);
              String uniqueRef = sdf.format(tt);
              return uniqueRef;
          }
              
      }
  }
   
//  private static boolean duplicates(final ArrayList<String> zipcodelist)
//  {
//    Set<String> lump = new HashSet<String>();
//    for (String i : zipcodelist)
//    {
//      if (lump.contains(i)) return true;
//      lump.add(i);
//    }
//    return false;
//  }
   
//  public static void main(String [] args){
//      ArrayList<String> arr = new ArrayList<String>();
//      for (int i=0;i<10000;i++){
//          String s = ReferenceCodeGenerator.uniqueCurrentTimeMS();
//      System.out.println(s);
//       
//      arr.add(s);
//       
//      }
//      System.out.println(duplicates(arr));
//  }
} 

