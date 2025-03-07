package dronewar.server.game;

import choke3d.math.Mat4f;
import choke3d.math.MathUtils;
import choke3d.math.Quat;
import choke3d.math.Vec3f;
import dronewar.server.protocol.ControlData;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tocatoca
 */
public class Room {   
    public final ConcurrentHashMap<String,Integer> connections=new ConcurrentHashMap<>();
    public final ConcurrentHashMap<Integer,Player> players=new ConcurrentHashMap<>();
    public final ConcurrentHashMap<Integer,ControlData> player_controls=new ConcurrentHashMap<>();
    
    public final ConcurrentHashMap<Integer,Drone> drones=new ConcurrentHashMap<>(); 
    public final ArrayList<Bullet> bullets=new ArrayList<>();
    public Safezone safezone=new Safezone();
    
    public boolean is_registered(String connection) {
        return connections.containsKey(connection);
    }
    public Player register_player(String connection,String name) {
        if(connections.containsKey(connection)) {
            int id=connections.get(connection);
            players.get(id).name=name;
            return players.get(id);
        } 
        int id=connections.size()+1;
        Player new_player=new Player(); 
        new_player.id=id;
        new_player.name=name;
        connections.put(connection,id);
        Drone drone=new Drone();
        drone.player=id;
        drones.put(id,drone);  
        players.put(new_player.id, new_player);
        return new_player;
    }
    public int get_player_id(String connection) throws Exception {
        if(connections.containsKey(connection)) { 
            return connections.get(connection);
        }  
        throw new Exception("Player not found"); 
    }
    void destroy_drone(int player) {
        drones.remove(player);
    }
    public void init() {
        Random random = new Random(); 
        // adiciona bots 
        for(int c=0;c<100;c++) {
            Player bot=register_player("bot-"+c,"bot-"+c);
            Drone drone=drones.get(bot.id);
            drone.position.x=random.nextInt(-100, 100);
            drone.position.z=random.nextInt(-100, 100); 
            drone.position.y=random.nextInt(20, 60); 
            drone.angle=(float) (2.0f*Math.PI);
            //drone.angles.y=(float) (2.0f*Math.PI);
            
        }
        
    }
    public void update(double delta) {
        HashMap<Bullet,Drone> hits=new HashMap<>();
        safezone.radius-=delta;
        for(int player_id : player_controls.keySet()) {
            Drone drone=drones.get(player_id);
            if(drone!=null) {
                ControlData control=player_controls.get(player_id);
                Quat rotation=new Quat();
                //drone.angle+=(delta*-control.movement.x);
                drone.angular_velocity=MathUtils.LERP(drone.angular_velocity,control.movement.x*Drone.MAX_ANG_VELOCITY, (float) (delta*2));
                
                drone.velocity.z=MathUtils.LERP(drone.velocity.z,control.movement.z*Drone.MAX_VELOCITY, (float) (delta*0.5f));
                drone.velocity.y=MathUtils.LERP(drone.velocity.y,control.movement.y*Drone.MAX_VELOCITY, (float) (delta*0.5f));
               
                //drone.velocity.
                drone.angle+=(-delta*drone.angular_velocity); 
              
                if(control.fire && drone.canFire()) {
                    System.out.println("ATIRANDO");
                    Bullet bullet= new Bullet();
                    bullet.position=drone.position.copy();
                    bullet.player=player_id;
                    bullet.velocity=
                            Mat4f.IDENTITY().translated(Vec3f.FORWARD().mul(30))
                                    .rotated(drone.get_rotation())
                                    .normalized_translation();
                    
                    bullets.add(bullet);
                    drone.fire_timeout+=0.3f;
                }
            }            
        }
         
        for(Drone drone : drones.values()) {
            
           /* for(Drone other : drones.values()) {
                if(other!=drone) drone.velocity-=drone.velocity;
            }*/
            drone.update(delta);
            
            if(!drone.collideWith(safezone)) {
                drone.energy-=delta*safezone.damage;
            }
            if(drone.energy<=0) {
                destroy_drone(drone.player);
            }
        }
        ArrayList dead_bullets=new ArrayList<>();
        for(Bullet bullet : bullets) {
            bullet.update(delta);
            
            for(Drone drone : drones.values()) {
                if(bullet.player!=drone.player)
                    if(drone.collideWith(bullet)) hits.put(bullet,drone);
            }
            if(bullet.energy<0) {
                dead_bullets.add(bullet);
            }
        }
         
        
        hits.forEach((bullet, drone) -> {
            drone.energy-=bullet.energy;
            if(drone.energy<=0) { 
                drones.get(bullet.player).energy+=drone.energy;
                players.get(bullet.player).score++;
            }
        });
        bullets.removeAll(dead_bullets); 
        bullets.removeAll(hits.keySet());
    }
    public void destroy() {
        
    }
    
}
