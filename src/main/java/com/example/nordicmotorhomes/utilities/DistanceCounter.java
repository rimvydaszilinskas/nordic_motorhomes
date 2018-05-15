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

    public static int getDistance(String origin, String destination){
        int distance = 0;
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        try{
            DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);
            DistanceMatrix matrix = request.origins(origin)
                    .destinations(destination)
                    .mode(TravelMode.DRIVING)
                    .await();

            String[] split = matrix.rows[0].elements[0].distance.toString().split(" ");
            distance = Integer.parseInt(split[0]);

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return distance;
    }
}
