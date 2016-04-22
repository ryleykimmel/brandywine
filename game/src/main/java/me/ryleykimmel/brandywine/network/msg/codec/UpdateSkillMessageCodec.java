package me.ryleykimmel.brandywine.network.msg.codec;

import me.ryleykimmel.brandywine.game.model.skill.Skill;
import me.ryleykimmel.brandywine.network.frame.DataOrder;
import me.ryleykimmel.brandywine.network.frame.DataType;
import me.ryleykimmel.brandywine.network.frame.FrameBuilder;
import me.ryleykimmel.brandywine.network.frame.FrameReader;
import me.ryleykimmel.brandywine.network.msg.MessageCodec;
import me.ryleykimmel.brandywine.network.msg.impl.UpdateSkillMessage;

/**
 * MessageCodec for the {@link UpdateSkillMessage}.
 */
public final class UpdateSkillMessageCodec implements MessageCodec<UpdateSkillMessage> {

  @Override
  public void encode(UpdateSkillMessage message, FrameBuilder builder) {
    Skill skill = message.getSkill();
    builder.put(DataType.BYTE, skill.getId());
    builder.put(DataType.INT, DataOrder.MIDDLE, (int) skill.getExperience());
    builder.put(DataType.BYTE, skill.getCurrentLevel());
  }

  @Override
  public UpdateSkillMessage decode(FrameReader frame) {
    return null;
  }

}