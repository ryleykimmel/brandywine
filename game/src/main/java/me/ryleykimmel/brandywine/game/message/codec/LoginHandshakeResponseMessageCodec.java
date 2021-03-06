package me.ryleykimmel.brandywine.game.message.codec;

import me.ryleykimmel.brandywine.game.message.LoginHandshakeResponseMessage;
import me.ryleykimmel.brandywine.network.frame.DataType;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.message.MessageCodec;

/**
 * MessageCodec for the {@link LoginHandshakeResponseMessage}.
 */
public final class LoginHandshakeResponseMessageCodec extends
    MessageCodec<LoginHandshakeResponseMessage> {

  @Override
  public void encode(LoginHandshakeResponseMessage message, FrameBuilder builder) {
    builder.putBytes(message.getDummy());
    builder.put(DataType.BYTE, message.getStatus());
    builder.put(DataType.LONG, message.getSessionKey());
  }

}
