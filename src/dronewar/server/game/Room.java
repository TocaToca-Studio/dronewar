package dronewar.server.game;

import choke3d.math.Vec3f;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tocatoca
 */
public class Room {   
    public ConcurrentHashMap<String,Integer> connections=new ConcurrentHashMap<>();
    public ConcurrentHashMap<Integer,Player> players=new ConcurrentHashMap<>();
    
    public ConcurrentHashMap<Integer,Drone> drones=new ConcurrentHashMap<>(); 
    public ArrayList<Bullet> bullets=new ArrayList<>();
    public Safezone safezone=new Safezone();
    
    public boolean is_registered(String connection) {
        return connections.containsKey(connection);
    }
    public int register_player(String connection,String name) {
        if(connections.containsKey(connection)) {
            int id=connections.get(connection);
            players.get(id).name=name;
            return id;
        } 
        int id=connections.size()+1;
        Player new_player=new Player(); 
        new_player.id=id;
        new_player.name=name;
        connections.put(connection,id);
        drones.put(id,new Drone());  
        return id;
    }
    void destroy_drone(int player) {
        drones.remove(player);
    }
    public void init() {
        
    }
    public void update(float delta) {
        HashMap<Bullet,Drone> hits=new HashMap<>();
        safezone.radius-=delta;
        for(Drone drone : drones.values()) {
            drone.update(delta);
            
            if(!drone.collideWith(safezone)) {
                drone.energy-=delta*safezone.damage;
            }
            if(drone.energy<=0) {
                destroy_drone(drone.player);
            }
        }
        for(Bullet bullet : bullets) {
            bullet.update(delta);
            
            for(Drone drone : drones.values()) {
                if(drone.collideWith(bullet)) hits.put(bullet,drone);
            }
        }
         
        hits.forEach((bullet, drone) -> {
            drone.energy-=bullet.energy;
            if(drone.energy<=0) { 
                drones.get(bullet.player).energy+=drone.energy;
                players.get(bullet.player).score++;
            }
            bullets.remove(bullet);
        });
    }
    public void destroy() {
        
    }
    
}
