package com.example.nordicmotorhomes.utilities;

import java.util.ArrayList;

public interface IJSON {
    JSON add(String key, String value);
    JSON add(String key, int value);
    JSON addArray(String key, String... values);
    //JSON add(String key, ArrayList<String> values);
    JSON add(String key, ArrayList<IJSON> jsons);
    JSON merge(JSON json);
    JSON merge(JSON... jsons);
    void clear();
    String getJSON();
    int length();
}
