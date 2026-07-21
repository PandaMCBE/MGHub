package fr.pandamcbe.mghub.fireworks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FireworksManager {
        private Map<String, Set<FireworkEffect>> rocket;

        public FireworksManager() {
            this.rocket = new HashMap<>();
        }

        public void craftEffects(){
            FireworkEffect.Builder builder = FireworkEffect.builder();

            FireworkEffect fw1 = builder.trail(true).withColor(Color.GREEN).withFade(Color.RED).with(FireworkEffect.Type.BALL).build();
            rocket.put("fw1", Collections.singleton(fw1));
        }

        public void spawnFirework(Location location, String name) {
            Firework firework = (Firework)  location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            if(rocket.containsKey(name)) {
                fireworkMeta.addEffects(rocket.get(name));
            }


            fireworkMeta.setPower(3);
            firework.setFireworkMeta(fireworkMeta);
        }
}
