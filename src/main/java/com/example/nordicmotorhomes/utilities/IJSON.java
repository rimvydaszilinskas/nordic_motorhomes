package com.example.nordicmotorhomes.utilities;

import java.util.ArrayList;

public interface IJSON {
    IJSON add(String key, String value);
    IJSON add(String key, int value);
    IJSON addArray(String key, String... values);
    //JSON add(String key, ArrayList<String> values);
    IJSON add(String key, ArrayList<IJSON> jsons);
    IJSON merge(IJSON json);
    IJSON merge(IJSON... jsons);
    void clear();
    String getJSON();
    int length();
}
