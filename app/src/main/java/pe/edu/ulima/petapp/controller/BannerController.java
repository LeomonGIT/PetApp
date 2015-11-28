package pe.edu.ulima.petapp.controller;


import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BannerController {

    private static BannerController instance;
    private List<String> banner;

    public static BannerController getInstance(){
        if(instance==null)
            instance=new BannerController();
        return instance;
    }

    public void cargarBanner(){
        banner = new ArrayList<>();
        banner.add("Agrovetmarket");
        banner.add("Healthy Pets Vet");
        banner.add("\"Groom Room\"");
        banner.add("Dicopet");
    }

    public void bannerShow(TextView _banner){
        Random randito = new Random();
        int valorDado = randito.nextInt(banner.size());

        _banner.setText(banner.get(valorDado).toString());


    }

}
