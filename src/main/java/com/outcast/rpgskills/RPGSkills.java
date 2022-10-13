package com.outcast.rpgskills;

import com.outcast.rpgskills.base.DragonsBreath;
import com.outcast.rpgskills.util.skills.BlankSkill;
import com.outcast.rpgcore.RPGCore;
import com.outcast.rpgskill.event.EffectRegistrationEvent;
import com.outcast.rpgskill.event.SkillRegistrationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGSkills extends JavaPlugin implements Listener {

    private static RPGSkills instance;

    public static RPGSkills getInstance() {
        return instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegisterSkills(SkillRegistrationEvent event) {
        RPGCore.info("Registering skills.");
        event.registerSkills(
                // Base
                new DragonsBreath(),

                // Upgradeable

                // For tree root
                new BlankSkill("root-skill", "RootSkill")
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegisterEffects(EffectRegistrationEvent event) {
        event.registerEffects(
//                new BlankEffect(ArcaneWard.WARD_EFFECT, "Arcane Ward User", true)
        );
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Register Listeners
//        Bukkit.getPluginManager().callEvent(this, new Effects());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
