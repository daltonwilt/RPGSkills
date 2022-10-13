package com.outcast.rpgskills.util.skills;

import com.outcast.rpgclass.api.skill.Skill;
import com.outcast.rpgclass.api.skill.SkillSpec;
import com.outcast.rpgskill.api.exception.CastException;
import com.outcast.rpgskill.api.skill.CastResult;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;

public class BlankSkill extends Skill {

    public BlankSkill(String id, String name) {
        super(
                SkillSpec.create()
                        .id(id)
                        .name(name)
                        .descriptionTemplate(Component.empty())
                        .cooldown("0")
                        .resourceCost("0")
        );
    }

    @Override
    public CastResult cast(LivingEntity user, long timestamp, String... args) throws CastException {
        return CastResult.success();
    }

}
