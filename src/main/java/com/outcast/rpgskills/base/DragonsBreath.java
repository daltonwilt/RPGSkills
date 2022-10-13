package com.outcast.rpgskills.base;

import com.outcast.rpgclass.api.skill.Skill;
import com.outcast.rpgclass.api.skill.SkillSpec;
import com.outcast.rpgcore.physics.Projectile;
import com.outcast.rpgcore.util.ParticleUtil;
import com.outcast.rpgcore.util.math.geometry.ShapeUtil;
import com.outcast.rpgskill.api.exception.CastException;
import com.outcast.rpgskill.api.skill.CastResult;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

import static com.outcast.rpgskills.util.Properties.DAMAGE;

public class DragonsBreath extends Skill {

    private static final String DEFAULT_DAMAGE_EXPRESSION = "CLAMP(SOURCE_WIS * 1.5, 0.5, 10.0)";

    public DragonsBreath() {
        super(
            SkillSpec.create()
                .id("dragonsbreath")
                .name("DragonsBreath")
                .descriptionTemplate(Component.text(
                        ChatColor.DARK_GRAY +
                        "Breath fire in the direction you are facing for " +
                        ChatColor.DARK_AQUA +
                        DAMAGE +
                        ChatColor.DARK_GRAY +
                        " magical damage."
                ))
                .resourceCost("0")
                .cooldown("0")
        );

//        setDescriptionArguments(
//                (Map.Entry<String, ?>[]) new Map.Entry<>(DAMAGE, DescriptionArguments.ofProperty(this, DAMAGE, DEFAULT_DAMAGE_EXPRESSION))
//        );
    }

    @Override
    public CastResult cast(LivingEntity living, long timestamp, String... args) throws CastException {
        if(living instanceof Player) {
            Player player = (Player) living;
            double distance = 10;
            Location start = player.getEyeLocation().add(player.getLocation().getDirection()).subtract(0.0, 0.25, 0.0);
            Vector direction = start.getDirection();

            double x = direction.getX() * distance;
            double y = direction.getY() * distance;
            double z = direction.getZ() * distance;

            Location end = start.clone().add(x,y,z);
            List<Location> path = ShapeUtil.line(start, end, (distance * 2));
            projectile(path, player);

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.5F, 1.0F);
        }

        return CastResult.success();
    }

    public final Projectile projectile(List<Location> path, Player player) {
        return new Projectile(path) {
            @Override
            protected void init() {
                setOwner(player);
                setLifetime(2000);
                setMaxEntities(3);
                setInterpolationScale(3);
                setSize(new Vector(1.0, 2.0, 1.0));
                super.init();
            }

            @Override
            public void onTick() {
                ParticleUtil.spawnBasicParticles(location, Particle.LAVA, 2, 16.0, 0.1, 0.1, 0.1);
                super.onTick();
            }

            @Override
            public void interpolatedTick() {
                ParticleUtil.spawnBasicParticles(location, Particle.FALLING_LAVA, 5, 16.0, 0.3, 0.3, 0.3);
                ParticleUtil.spawnBasicParticles(location, Particle.FLAME, 15, 16.0, 0.3, 0.3, 0.3);
                ParticleUtil.spawnColoredParticles(location, Particle.REDSTONE, 15, 16.0, 0.3, 0.3, 0.3, 0, 0, 0);
                super.interpolatedTick();
            }

            @Override
            public void onHitEntity(LivingEntity target) {
//                double amount = 20.0;
                double damage = asDouble(player, target, getProperty(DAMAGE, String.class, DEFAULT_DAMAGE_EXPRESSION));
                target.damage(damage, owner);
//                damageEntity(entity, owner, amount, EntityDamageEvent.DamageCause.MAGIC, false);
                hitEntities.add(target);

                target.getWorld().playSound(target.getLocation(), Sound.ENTITY_PLAYER_HURT, 0.5F, 0.1F);
                super.onHitEntity(owner);
            }
        };
    }

}
