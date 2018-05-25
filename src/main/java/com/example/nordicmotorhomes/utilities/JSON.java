package com.example.nordicmotorhomes.utilities;

import java.util.ArrayList;

public class JSON implements IJSON{
    private StringBuilder json;
    private int length;

    public JSON() {
        json = new StringBuilder();
        json.append("{");
    }

    @Override
    public IJSON add(String key, String value){
        StringBuilder str = new StringBuilder();

        if(value.length() > 2)
            if(value.charAt(0) == '"' && value.charAt(1) == '{') {
                value = new StringBuilder(value).deleteCharAt(0).
                        deleteCharAt(value.length()-1).toString();
            }

        if(length != 0)
            str.append(", ");
        length++;
        if(value.charAt(0) != '{') {
            str.append("\"").append(key).append("\" : ");
            str.append("\"").append(value).append("\"");
        } else {
            str.append("\"").append(key).append("\" : ");
            str.append(value);
        }
        json.append(str.toString());
        return this;
    }

    @Override
    public IJSON add(String key, int value){
        StringBuilder str = new StringBuilder();
        if(length != 0)
            str.append(", ");
        length++;
        str.append("\"").append(key).append("\" : ");
        str.append("\"").append(value).append("\"");
        json.append(str.toString());
        return this;
    }

    @Override
    public IJSON addArray(String key, String... values){
        StringBuilder str = new StringBuilder();
        if(length != 0)
            str.append(", ");
        length++;
        str.append("\"").append(key).append("\" : ");
        str.append("[");
        for(int i = 0; i < values.length; i++) {
            if(i != 0)
                str.append(", ");
            if(values[i].charAt(0) == '"' && values[i].charAt(1) == '{'){
                values[i] = new StringBuilder(values[i]).deleteCharAt(0).deleteCharAt(values[i].length()-1).toString();
            }
            if(values[i].charAt(0) != '{')
                str.append("\"").append(values[i]).append("\"");
            else
                str.append(values[i]);
        }
        str.append("]");
        json.append(str.toString());
        return this;
    }

    @Override
    public IJSON add(String key, ArrayList<IJSON> jsons){
        StringBuilder str = new StringBuilder();
        if(length != 0)
            str.append(", ");
        length++;

        str.append("\"").append(key).append("\" : ");
        str.append("[");

        boolean first = true;
        for(IJSON json : jsons){
            if(!first)
                str.append(", ");
            first = false;

            str.append(json.getJSON());
        }

        str.append("]");
        this.json.append(str.toString());
        return this;
    }

    @Override
    public int length(){
        return this.length;
    }

    @Override
    public String getJSON(){
        String returned = "";
        if(json.toString().charAt(json.toString().length()-1) != '}')
            returned = json.append("}").toString();
        json.deleteCharAt(json.toString().length() - 1);
        return returned;
    }

    @Override
    public void clear(){
        json = new StringBuilder();
        length = 0;
        json.append("{");
    }

    @Override
    public IJSON merge(IJSON json) {
        StringBuilder str = new StringBuilder(json.getJSON());
        str.deleteCharAt(0).deleteCharAt(str.toString().length()-1);

        if(length != 0)
            this.json.append(", ").append(str.toString());
        else
            this.json.append(str.toString());

        return this;
    }

    @Override
    public IJSON merge(IJSON... jsons) {
        StringBuilder str;

        for(IJSON json : jsons){
            if(length != 0) {
                str = new StringBuilder(json.getJSON());
                str.deleteCharAt(0).deleteCharAt(str.toString().length() - 1);
                this.json.append(", ").append(str.toString());
            }
            else {
                str = new StringBuilder(json.getJSON());
                str.deleteCharAt(0).deleteCharAt(str.toString().length() - 1);
                this.json.append(str.toString());
            }
        }
        return this;
    }
}
