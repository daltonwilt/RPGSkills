package com.outcast.rpgskills.util.effects;

import com.outcast.rpgskill.api.effect.AbstractEffect;
import com.outcast.rpgskill.api.effect.ApplyableCarrier;

public class BlankEffect extends AbstractEffect {

    private boolean canRemove;
    public BlankEffect(String id, String name, boolean isPositive) {
        super(id, name, isPositive);
        canRemove = false;
    }

    @Override
    public boolean canApply(long timestamp, ApplyableCarrier<?> character) {
        return false;
    }

    @Override
    public boolean apply(long timestamp, ApplyableCarrier<?> character) {
        return true;
    }

    @Override
    public boolean canRemove(long timestamp, ApplyableCarrier<?> character) {
        return canRemove;
    }

    @Override
    public boolean remove(long timestamp, ApplyableCarrier<?> character) {
        return true;
    }

    @Override
    public void setRemoved() {
        canRemove = true;
    }

}
