package org.bukkit.entity;

import org.bukkit.Location;

/**
 * 一个水晶，能治疗附近的末影龙
 */
public interface EnderCrystal extends Entity {

    /**
     * Return whether or not this end crystal is showing the
     * bedrock slate underneath it.
     *
     * @return true if the bottom is being shown
     */
    boolean isShowingBottom();

    /**
     * Sets whether or not this end crystal is showing the
     * bedrock slate underneath it.
     *
     * @param showing whether the bedrock slate should be shown
     */
    void setShowingBottom(boolean showing);

    /**
     * Gets the location that this end crystal is pointing its beam to.
     *
     * @return the location that the beam is pointed to, or null if the beam is not shown
     */
    Location getBeamTarget();

    /**
     * Sets the location that this end crystal is pointing to. Passing a null
     * value will remove the current beam.
     *
     * @param location the location to point the beam to
     * @throws IllegalArgumentException for differing worlds
     */
    void setBeamTarget(Location location);
}
