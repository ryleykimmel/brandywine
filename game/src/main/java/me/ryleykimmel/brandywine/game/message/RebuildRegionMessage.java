package me.ryleykimmel.brandywine.game.message;

import me.ryleykimmel.brandywine.game.model.Position;
import me.ryleykimmel.brandywine.network.message.Message;

/**
 * A {@link Message} which rebuilds the region for the specified Position.
 */
public final class RebuildRegionMessage extends Message {

  /**
   * The Position of the region to rebuild.
   */
  private final Position position;

  /**
   * Constructs a new {@link RebuildRegionMessage} with the specified Position.
   *
   * @param position The position of the region to rebuild.
   */
  public RebuildRegionMessage(Position position) {
    this.position = position;
  }

  /**
   * Gets the Position of the region to rebuild.
   *
   * @return The Position of the region to rebuild.
   */
  public Position getPosition() {
    return position;
  }

}
