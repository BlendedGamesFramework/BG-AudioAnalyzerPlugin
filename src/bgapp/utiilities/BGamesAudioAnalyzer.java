package bgapp.utiilities;



import bgapp.utiilities.AttributePlayer;
import bgapp.utiilities.SensorNeed;
import bgapp.utiilities.Observer;
import bgapp.utiilities.PluginMethods;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.Polling;
import io.socket.engineio.client.transports.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
/**
 *
 * @author InTEracTIon User
 */
public class BGamesAudioAnalyzer implements PluginMethods{

    //RECORDAR USO DE ATRIBUTOS!!! 
    private boolean _connectionStatus =false;
    private String _namePlayer;
    private int data, attributes_id_attributes;
    private String nameat, namecategory,  data_type, input_source, date_time;
    private SensorNeed _sensorNeed;
    private Socket socket;
    private Socket socket2;
    private int id_player;

    private String Host;
    final String NameApp = "SensorSpectroAudio"; //Name sensor

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BGamesAudioAnalyzer sv = new BGamesAudioAnalyzer();
        sv.initializeValues();
        sv.run(2);
    }
    
    @Override
    public void initializeValues() {
        
        String VER = "1.0";//Version of plugin
        String Cat = "Físico"; // Category
        String Des = "Plugin dedicated to the extraction of information from the sensor NeuroSky Mindwave";// Description
        ArrayList<AttributePlayer> LISTATT = new ArrayList();//List of Attributes
        String Host2 = "8001";//Host of WebSocket
        ArrayList<Observer> Obs = new ArrayList();
        this._sensorNeed = new SensorNeed(NameApp, VER,Cat,Des,LISTATT,1,Host2,Obs);       
        this._connectionStatus = true;
        this.id_player = id_player;
        localSocketInitialization();
        SoundSpectrumSocketInitialization();
        
        
      
        
        
    }
    
    public void localSocketInitialization(){
          
        
        try {
            /*WebClient
            
            -------------------------------------------------------------------------------------------
            */
            socket = IO.socket("http://localhost:"+this._sensorNeed.getHost());
        } catch (URISyntaxException ex) {
            Logger.getLogger(BGamesAudioAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                System.out.println("Sensor connected to the but local"); //To change body of generated methods, choose Tools | Templates.
            }
            
        }).on("join_sensor", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Sensor connected to the local websocket server"); //To change body of generated methods, choose Tools | Templates.
            }
            
        }).on("AllSensors", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    //System.out.println("Args[0] enter: "+args[0]);
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Sensors: "+obj.getString("sensoresActivos"));
                } catch (JSONException ex) {
                    
                }
            }
            
        }).on("AllSensors", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    //System.out.println("Args[0] enter: "+args[0]);
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Sensors: "+obj.getString("sensoresActivos"));
                } catch (JSONException ex) {
                    
                }
            }
            
        }).on("join_offline_sensors", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                JSONObject obj2 = new JSONObject();
                try {
                    String room = obj.getString("name");
                    obj2.put("room", room);
                    obj2.put("name", NameApp);
                    socket.emit("join_offline_sensors_confirmation", obj2);

                } catch (JSONException ex) {
                    Logger.getLogger(BGamesAudioAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }).on("Smessage", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Objet nome: "+obj.getString("name"));
                    System.out.println("Objet message: "+obj.getString("message"));
                } catch (JSONException ex) {
                    System.out.println("Failed to get object or name");
                }
            }
            
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {}
            
        });
        socket.connect(); 
    }
    public void SoundSpectrumSocketInitialization(){
          
        
        try {
            /*WebClient
            
            -------------------------------------------------------------------------------------------
            */
            socket2 = IO.socket("http://164.90.156.141:3011");

        } catch (URISyntaxException ex) {
            Logger.getLogger(BGamesAudioAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "paso por aqui");

        }
        socket2.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                System.out.println("Sensor connected to the websocket in the microservice"); //To change body of generated methods, choose Tools | Templates.
           

            }
            
        }).on("join_sensor", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            }
            
        }).on("AllSensors", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    //System.out.println("Args[0] enter: "+args[0]);
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Sensors: "+obj.getString("sensoresActivos"));
                } catch (JSONException ex) {
                    
                }
            }
            
        }).on("message", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Objet message: "+obj.getString("message"));
                    JSONObject objSent = new JSONObject();
                    objSent.put("message", obj.getString("message"));
                    socket.emit("Omessage",objSent );
                } catch (JSONException ex) {
                    System.out.println("Failed to get object or name");
                }
            }
            
        }).on("welcome", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Objet message: "+obj.getString("message"));
                } catch (JSONException ex) {
                    System.out.println("Failed to get object or name");
                }
            }
            
        })
        .on("success", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Objet message: "+obj.getString("message"));
                } catch (JSONException ex) {
                    System.out.println("Failed to get object or name");
                }
            }
            
        }).on("Smessage", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    System.out.println("Objet nome: "+obj.getString("name"));
                    System.out.println("Objet message: "+obj.getString("message"));
                } catch (JSONException ex) {
                    System.out.println("Failed to get object or name");
                }
            }
            
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {}
            
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
               System.out.println("Tuve un error al conectarme");
               System.out.println(args);
               System.out.println(args[0]);
               socket2.connect(); 

            }
        });
        socket2.connect(); 
    }

    public void notificar(SensorNeed sensorNeed,AttributePlayer attributePlayer) {
        sensorNeed.notify(attributePlayer); //To change body of generated methods, choose Tools | Templates.
    }
    public void notifica(SensorNeed sensorNeed) {
        sensorNeed.notifyOK(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run(int id_player) {
        System.out.println("EL HOST CAMBIO:" + _sensorNeed.getHost());
        //System.out.println("Data Found: " + _connection.getData());
        //notifica(this._sensorNeed);
        this.id_player = id_player;
        socket2.emit("joinRoom", id_player);
        String resultJson;           
        try {
            resultJson = new JSONObject().put("name", NameApp).put("room", NameApp).toString();       
            socket.emit("join_sensor", resultJson);            
        } catch (JSONException ex) {
            Logger.getLogger(BGamesAudioAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        System.out.println(socket2.connected()); // true


        //System.out.println(" \n");
        socket.emit("AllSensors");
        /*String json = "{\"id_player\": 1,\"nameat\":\"Resistencia\",\"namecategory\":\"Físico\",\"data\":1,\"data_type\":\"in.off\",\"input_source\":\"xlr8_podometer\",\"date_time\": \"2011-12-18 13:17:17\"}";
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        */
        //System.out.println(properties);
    }
    
    @Override
    public void StopThis(){
        this._connectionStatus = false;
        
        
    }
    
    @Override
    public SensorNeed getSensorNeed() {
        return this._sensorNeed;
    }

    @Override
    public void setSensorNeedObservers(ArrayList<?> Obs) {
        this._sensorNeed.Observers = (ArrayList)Obs;
    }
    
    /**
     * @return the Host
     */
    public String getHost() {
        return Host;
    }

    /**
     * @param Host the Host to set
     */
    public void setHost(String Host) {
        this.Host = Host;
    }
}
