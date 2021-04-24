/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgapp.utiilities;

import java.util.ArrayList;

/**
 *
 * @author InTEracTIon User
 */
public interface PluginMethods {
    /**
     * Es imprescindible que el desarrollador inicialice todos los campos del objeto "SensorNeed"
     * Para crear un nuevo atributo que necesite enviar por streaming, revisar biblioteca "objects"
     * Si se desea enviar un Atributo proce
     */
    
    
    /**
     * @param Obs Son los Observadores
     * A continuación se establecen los observadores que puede tener esta plugin, inicialmente
     * el código mínimo debe ser el siguiente: this.SNeed.Observadores = (ArrayList)Obs;
     */
    void setSensorNeedObservers(ArrayList<?> Obs);
    
    
    /**
     * Se debe poder entregar el Objeto Sensorneed, que se encuentra en la biblioteca "objects"
     * el código de retorno debe ser el siguiente: return this.SNeed;
     * @return 
     */
    public SensorNeed getSensorNeed();
    
    /**
     * Se utiliza para inicializar las variables que se necesiten para la extracción de datos del sensor
     * Se repite: es imprescindible que el desarrollador inicialice todos los campos del objeto "SensorNeed"
     */
    void initializeValues();
    
    
    /**
     * Para la ejecución del código del proceso para la obtención de los datos del sensor, se tienen que ejecutar
     * los metódos o llamados en este lugar
     */
    void run(int id_player);
    
    /**
     * 
     * 
     */
    void StopThis();
}
