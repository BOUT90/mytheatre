/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modeles;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author lagierg
 */
public class SpectacleDatePhoto extends SpectacleDate{

    protected List<String> photos;
    
    public SpectacleDatePhoto(int numeroSpectacle, String nomSpectacle, int duree, String resumeSpectacle, List<LocalDateTime> d, List<String> photo) {
        super(numeroSpectacle, nomSpectacle, duree, resumeSpectacle, d);
        this.photos = photo;
    }

    public List<String> getPhotos() {
        return photos;
    }
    
}
