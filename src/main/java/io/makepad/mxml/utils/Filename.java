package io.makepad.mxml.utils;

public class Filename {
  public static String getExtension(String filePath) {
    String[] starray = filePath.split("\\.");
    return starray[starray.length - 1];
  }
}
