package me.ryleykimmel.brandywine.network.msg.codec;

import me.ryleykimmel.brandywine.network.game.frame.DataType;
import me.ryleykimmel.brandywine.network.game.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.msg.MessageEncoder;
import me.ryleykimmel.brandywine.network.msg.impl.LoginResponseMessage;

/**
 * Encodes the {@link LoginResponseMessage}.
 */
public final class LoginResponseMessageEncoder implements MessageEncoder<LoginResponseMessage> {

  @Override
  public void encode(LoginResponseMessage message, FrameBuilder builder) {
    builder.put(DataType.BYTE, message.getStatus());
    if (message.getStatus() == LoginResponseMessage.STATUS_OK) {
      builder.put(DataType.BYTE, message.getPrivilege());
      builder.put(DataType.BYTE, message.isFlagged() ? 1 : 0);
    }
  }

}
