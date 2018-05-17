package com.example.nordicmotorhomes.utilities;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

public class DistanceCounter {
    private static final String API_KEY = "AIzaSyAT9P126V5CrXzpQwjPKZNTQ34VfE1hFLo";
    private static DistanceCounter distanceCounter = new DistanceCounter();

    private DistanceCounter(){}

    public static DistanceCounter getInstance(){
        return distanceCounter;
    }

    public static double getDistance(String destination){
        double distance = 0;
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        try{
            DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);
            DistanceMatrix matrix = request.origins("Lygten 16, Copenhagen, Denmark")
                    .destinations(destination)
                    .mode(TravelMode.DRIVING)
                    .await();
            if(matrix.rows[0].elements[0].distance != null){
                String[] split = matrix.rows[0].elements[0].distance.toString().split(" ");
                distance = Double.parseDouble(split[0]);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return distance;
    }
}
