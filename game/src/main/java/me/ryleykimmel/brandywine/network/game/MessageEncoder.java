package me.ryleykimmel.brandywine.network.game;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.ryleykimmel.brandywine.network.msg.Message;

/**
 * Encodes Messages into Frames.
 */
public final class MessageEncoder extends MessageToMessageEncoder<Message> {

  /**
   * A {@link PooledByteBufAllocator} responsible for allocating {@link ByteBuf buffers}.
   */
  private final ByteBufAllocator allocator = new PooledByteBufAllocator();

  /**
   * The GameSession we're encoding for.
   */
  private final GameSession session;

  /**
   * Constructs a new {@link MessageEncoder} with the specified GameSession.
   *
   * @param session The GameSession we're encoding for.
   */
  public MessageEncoder(GameSession session) {
    this.session = session;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) {
    out.add(session.getContext().getFrameMetadataSet().encode(message, allocator));
  }

}
