package me.ryleykimmel.brandywine.game.message.codec;

import java.util.ArrayDeque;
import java.util.Deque;
import me.ryleykimmel.brandywine.game.message.MovementMessage;
import me.ryleykimmel.brandywine.game.model.Position;
import me.ryleykimmel.brandywine.network.frame.DataOrder;
import me.ryleykimmel.brandywine.network.frame.DataTransformation;
import me.ryleykimmel.brandywine.network.frame.DataType;
import me.ryleykimmel.brandywine.network.frame.FrameReader;
import me.ryleykimmel.brandywine.network.message.MessageCodec;

/**
 * MessageCodec for the {@link MovementMessage}.
 */
public final class MovementMessageCodec extends MessageCodec<MovementMessage> {

  @Override
  public MovementMessage decode(FrameReader reader) {
    int length = reader.getLength();
    if (reader.getOpcode() == 248) {
      length -= 14; // strip off anti-cheat data
    }

    int steps = (length - 5) / 2;
    int[][] path = new int[steps][2];

    int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);

    for (int i = 0; i < steps; i++) {
      path[i][0] = (int) reader.getSigned(DataType.BYTE);
      path[i][1] = (int) reader.getSigned(DataType.BYTE);
    }

    int y = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);

    boolean run = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE) == 1;

    Deque<Position> positions = new ArrayDeque<>();

    positions.offerFirst(new Position(x, y));
    for (int i = 0; i < steps; i++) {
      positions.offer(new Position(path[i][0] + x, path[i][1] + y));
    }

    return new MovementMessage(positions, run);
  }

}
